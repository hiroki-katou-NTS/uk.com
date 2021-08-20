package nts.uk.ctx.exio.dom.input.canonicalize.domaindata;

import java.util.HashMap;
import java.util.Map;

/**
 * 項目NOが割り振られているが、システム内部でのみ登場する項目たち
 * これが無いと、既存データのCHANGEテーブル書き込み時に項目Noから列名へ復元できない
 * Map<列名,項目NO> 
 */
public class SystemImportingItems {
	public static Map<String, Integer> map = new HashMap<String, Integer>(){{
		put("SID",101);
		put("HIST_ID",102);
	}};
}
