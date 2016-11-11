package nts.uk.ctx.pr.proto.dom.layout.detail;

import java.util.HashMap;

/**
 * 
 * 合計対象区分
 *
 */
public enum SumScopeAtr {
	// 0:対象外
	EXCLUDED(0),
	// 1:対象内
	INCLUDED(1);
	public final int value;	

	/**
	 * Constructor
	 * 
	 * @param 合計対象区分
	 */
	SumScopeAtr(int value) {
		this.value = value;
	}
	
	private static HashMap<Integer, SumScopeAtr> map = new HashMap<>();
	
	static{
		for(SumScopeAtr item: SumScopeAtr.values()){
			map.put(item.value, item);
		}
	}
	
	/**
	 * 
	 * @param 合計対象区分の値
	 * @return　合計対象区分
	 */
	public static SumScopeAtr valueOf(int value) {
		return map.get(value);
	}
}
