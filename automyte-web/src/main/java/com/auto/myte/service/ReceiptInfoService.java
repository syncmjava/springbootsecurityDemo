package com.auto.myte.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auto.myte.entity.ReceiptInfo;
import com.auto.myte.mapper.ReceiptInfoMapper;

@Component
public class ReceiptInfoService {
	private static Logger logger = LoggerFactory.getLogger(ReceiptInfoService.class);
	@Autowired
	private ReceiptInfoMapper receiptInfoMapper;

	public ReceiptInfo getReceiptBaseInfo(String id) {
		ReceiptInfo info = receiptInfoMapper.selectByPrimaryKey(id);
		return info;
	}

	public List<ReceiptInfo> getAllReceipt() {
		return receiptInfoMapper.selectAll();
	}
	

	public int insertReceiptInfo(ReceiptInfo receipt) {
		return receiptInfoMapper.insertReceiptInfo(receipt);
	}

	public int updateReceiptInfoByKey(ReceiptInfo receipt) {
		return receiptInfoMapper.updateReceiptInfoByKey(receipt);
	}
}
