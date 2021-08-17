package nts.uk.screen.com.app.cmf.cmf001.b.get;

import java.util.List;

import lombok.Getter;

@Getter
public class GetLayoutQuery {
	
	/** 受入設定コード */
	private String settingCode;
	
	/** 受入グループID */
	private int importingGroupId;
	
	/** 項目NO一覧 */
	private List<Integer> itemNoList;
	
}