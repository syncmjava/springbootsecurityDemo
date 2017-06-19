package com.auto.myte.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.auto.myte.entity.ReceiptInfo;

public interface ReceiptInfoMapper {
	List<ReceiptInfo> selectAll();
	ReceiptInfo selectByPrimaryKey(@Param("id")String id);
	int insertReceiptInfo(ReceiptInfo receipt);
	int updateReceiptInfoByKey(ReceiptInfo receipt);
}