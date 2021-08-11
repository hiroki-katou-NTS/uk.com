package nts.uk.screen.com.app.cmf.cmf001.b.get;

import java.util.List;

import lombok.Getter;

@Getter
public class FindExternalImportSettingLayoutQuery {
	
	/** 受入設定コード */
	private String settingCode;
	
	/** 項目NO一覧 */
	private List<Integer> itemNoList;
	
}