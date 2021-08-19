package nts.uk.ctx.exio.dom.input.canonicalize.domaindata;

import java.util.HashMap;
import java.util.Map;

/**
 * 項目NOが割り振られているが、システム内部でのみ登場する項目たち
 * Map<列名,項目NO> 
 */
public class UkImpotingItems {
	public static Map<String, Integer> map = new HashMap<String, Integer>(){{
		put("SID",101);
		put("HIST_ID",102);
	}};
}
