package com.github.sky0621.study.springrest.app;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.sky0621.study.springrest.domain.entity.Item;
import com.github.sky0621.study.springrest.domain.exception.ItemResourceNotFoundException;
import com.github.sky0621.study.springrest.domain.resource.ItemResource;
import com.github.sky0621.study.springrest.domain.service.ItemService;

@RestController
@RequestMapping("items")
public class ItemRestController {

	@Autowired
	ItemService itemService;

	/*
	 * １リソース取得
	 */
	@RequestMapping(path = "{itemId}", method = RequestMethod.GET)
	public ItemResource getItem(@PathVariable String itemId) {
		Item item = itemService.find(itemId);
		if (item == null) {
			throw new ItemResourceNotFoundException(itemId);
		}
		ItemResource resource = new ItemResource();
		resource.setBookId(item.getBookId());
		resource.setName(item.getName());
		resource.setPublishedDate(item.getPublishedDate());
		return resource;
	}

	/*
	 * １リソース生成
	 */
	// @Validated でリソースオブジェクトに対して入力チェックを行うとあるが、どうやって？リソースクラス内にバリデーションメソッドを用意？
	// @RequestBody でPOST時のリクエストボディに指定されているJSONデータを取得
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createItem(@Validated @RequestBody ItemResource resource) {
		Item item = new Item();
		item.setName(resource.getName());
		item.setPublishedDate(resource.getPublishedDate());
		Item createdItem = itemService.create(item);

		String resourceUri = "http://localhost:8080/items/" + createdItem.getBookId();

		// 201 Createdを設定（Locationヘッダーに新規作成したリソースのURIを設定）
		// build()を用いるとレスポンスボディ不要としてVoidとなる
		return ResponseEntity.created(URI.create(resourceUri)).build();
	}

	/*
	 * １リソース更新
	 */
	// 204 No Contentを返す（更新後のデータは渡していない）。
	// 更新後のデータが必要であれば、voidでなくItemResourceを返して200 OKにすればいい。
	@RequestMapping(path = "{itemId}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void put(@PathVariable String itemId, @Validated @RequestBody ItemResource resource) {
		Item item = new Item();
		item.setBookId(itemId);
		item.setName(resource.getName());
		item.setPublishedDate(resource.getPublishedDate());
		itemService.update(item);
	}

	/*
	 * １リソース削除
	 */
	@RequestMapping(path = "{itemId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String itemId) {
		itemService.delete(itemId);
	}

}
