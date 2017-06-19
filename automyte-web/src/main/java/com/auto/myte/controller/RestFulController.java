package com.auto.myte.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auto.myte.entity.ReceiptInfo;
import com.auto.myte.service.ReceiptInfoService;

@RestController
public class RestFulController {
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private ReceiptInfoService service;

	@RequestMapping("/tables")
	public Map<String, List> hello() {

		List<ReceiptInfo> receiptInfoList = service.getAllReceipt();
		Map<String, List> map = new HashMap<>();
		map.put("data", receiptInfoList);
		return map;
	}

	@RequestMapping("/details")
	public ReceiptInfo details(@RequestParam(value = "id", required = false) String id) {
		ReceiptInfo receiptInfo = service.getReceiptBaseInfo(id);
		return receiptInfo;
	}

	@RequestMapping("/update")
	public Map<String, String> update(@RequestBody ReceiptInfo form) {
		Map<String, String> map = new HashMap<>();
		try {
			int flg = service.updateReceiptInfoByKey(form);
			if (flg > 0) {
				map.put("status", "0");
				map.put("message", "update is ok");
			} else {
				map.put("status", "1");
				map.put("message", "update is ng");
			}
		} catch (Exception e) {
			map.put("status", "1");
			map.put("message", "update is exception");
			logger.error(e.getMessage());
		}
		return map;
	}
}
