package com.github.sky0621.study.springrest.domain.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.github.sky0621.study.springrest.domain.entity.Item;

@Service
public class ItemService {

	private final Map<String, Item> itemRepository = new ConcurrentHashMap<>();

	@PostConstruct
	public void loadDummyData() {
		Item item = new Item();
		item.setBookId("00000-000-000-000-000-0000000");
		item.setName("èëê–ÇÃñºëO");
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

}
