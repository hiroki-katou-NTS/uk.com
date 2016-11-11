package nts.uk.ctx.pr.proto.dom.enums;

import java.util.HashMap;

public enum DistributeWay {
	//0:割合で計算
	CALCULATED_PERCENTAGE(0),
	//1:日数控除
	DEDUCTION_FOR_DAYS(1),
	//2:計算式
	CALCULATION_FORMULA(2);
	public final int value;
	
	/**
	 * Constructor.
	 * 
	 * @param カテゴリ区分の値
	 */
	DistributeWay(int value) {
		this.value = value;
	}
	
	private static HashMap<Integer, DistributeWay> map = new HashMap<>();
	
	static{
		for(DistributeWay item: DistributeWay.values()){
			map.put(item.value, item);
		}
	}
	
	public static DistributeWay valueOf(int value){
		return map.get(value);
	}
	
}
