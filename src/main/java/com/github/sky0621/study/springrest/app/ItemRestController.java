package com.github.sky0621.study.springrest.app;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.sky0621.study.springrest.domain.criteria.ItemCriteria;
import com.github.sky0621.study.springrest.domain.entity.Item;
import com.github.sky0621.study.springrest.domain.exception.ItemResourceNotFoundException;
import com.github.sky0621.study.springrest.domain.query.ItemResourceQuery;
import com.github.sky0621.study.springrest.domain.resource.ItemResource;
import com.github.sky0621.study.springrest.domain.service.ItemService;

@RestController
@RequestMapping("items")
public class ItemRestController {

	@Autowired
	ItemService itemService;

	/*
	 * �P���\�[�X�擾
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
	 * �P���\�[�X����
	 */
	// @Validated �Ń��\�[�X�I�u�W�F�N�g�ɑ΂��ē��̓`�F�b�N���s���Ƃ��邪�A�ǂ�����āH���\�[�X�N���X���Ƀo���f�[�V�������\�b�h��p�ӁH
	// @RequestBody ��POST���̃��N�G�X�g�{�f�B�Ɏw�肳��Ă���JSON�f�[�^���擾
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createItem(@Validated @RequestBody ItemResource resource,
			UriComponentsBuilder uriBuilder) {
		Item item = new Item();
		item.setName(resource.getName());
		item.setPublishedDate(resource.getPublishedDate());
		Item createdItem = itemService.create(item);

		URI resourceUri = MvcUriComponentsBuilder.relativeTo(uriBuilder)
				.withMethodCall(on(ItemRestController.class).getItem(createdItem.getBookId())).build().encode().toUri();

		// 201 Created��ݒ�iLocation�w�b�_�[�ɐV�K�쐬�������\�[�X��URI��ݒ�j
		// build()��p����ƃ��X�|���X�{�f�B�s�v�Ƃ���Void�ƂȂ�
		return ResponseEntity.created(resourceUri).build();
	}

	/*
	 * �P���\�[�X�X�V
	 */
	// 204 No Content��Ԃ��i�X�V��̃f�[�^�͓n���Ă��Ȃ��j�B
	// �X�V��̃f�[�^���K�v�ł���΁Avoid�łȂ�ItemResource��Ԃ���200 OK�ɂ���΂����B
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
	 * �P���\�[�X�폜
	 */
	@RequestMapping(path = "{itemId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String itemId) {
		itemService.delete(itemId);
	}

	/*
	 * �����ɉ��������\�[�X�擾
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<ItemResource> searchItems(@Validated ItemResourceQuery query) {
		ItemCriteria criteria = new ItemCriteria();
		criteria.setName(query.getName());
		criteria.setPublishedDate(query.getPublishedDate());
		List<Item> items = itemService.findAllByCriteria(criteria);
		return items.stream().map(item -> {
			ItemResource resource = new ItemResource();
			resource.setBookId(item.getBookId());
			resource.setName(item.getName());
			resource.setPublishedDate(item.getPublishedDate());
			return resource;
		}).collect(Collectors.toList());
	}

}
