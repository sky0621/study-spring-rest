package com.github.sky0621.study.springrest.api.domain.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.github.sky0621.study.springrest.api.domain.criteria.ItemCriteria;
import com.github.sky0621.study.springrest.api.domain.entity.Item;

@Service
public class ItemService {

	private final Map<String, Item> itemRepository = new ConcurrentHashMap<>();

	@PostConstruct
	public void loadDummyData() {
		Item item = new Item();
		item.setBookId("00000-000-000-000-000-0000000");
		item.setName("書籍の名前");
		item.setPublishedDate(LocalDate.of(2012, 6, 6));
		itemRepository.put(item.getBookId(), item);
	}

	public Item find(String itemId) {
		Item item = itemRepository.get(itemId);
		return item;
	}

	public Item create(Item item) {
		item.setBookId(UUID.randomUUID().toString());
		itemRepository.put(item.getBookId(), item);
		return item;
	}

	public Item update(Item item) {
		return itemRepository.put(item.getBookId(), item);
	}

	public Item delete(String itemId) {
		Item item = itemRepository.remove(itemId);
		return item;
	}

	// 条件に名前があれば、それが含まれているものをチョイス
	// 条件に出版日があれば、それが含まれているものをチョイス
	public List<Item> findAllByCriteria(ItemCriteria criteria) {
		return itemRepository.values().stream()
				.filter(item -> 
					(criteria.getName() == null || item.getName().contains(criteria.getName()))
						&& 
					(criteria.getPublishedDate() == null || item.getPublishedDate().equals(criteria.getPublishedDate())))
				.sorted((o1, o2) -> o1.getPublishedDate().compareTo(o2.getPublishedDate()))
				.collect(Collectors.toList());
	}
}
