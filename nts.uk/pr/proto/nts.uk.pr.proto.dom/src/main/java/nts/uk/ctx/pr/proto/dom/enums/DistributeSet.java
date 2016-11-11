package nts.uk.ctx.pr.proto.dom.enums;

import java.util.HashMap;

public enum DistributeSet {
	//0:按分しない
	NOT_PROPORTIONAL(0),
	//1:按分する
	PROPORTIONAL(1),
	//2:月1回支給
	MONTHLY_PAYMENT(2);
	
	public final int value;
	
	/**
	 * Constructor.
	 * 
	 * @param カテゴリ区分の値
	 */
	DistributeSet(int value) {
		this.value = value;
	}
	
	private static HashMap<Integer, DistributeSet> map = new HashMap<>();
	
	static{
		for(DistributeSet item: DistributeSet.values()){
			map.put(item.value, item);
		}
	}
	/**
	 * convert to enum DistributeSet by value
	 * @param value
	 * @return
	 */
	public static DistributeSet valueOf(int value){
		return map.get(value);
	}
	
}
