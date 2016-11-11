package nts.uk.ctx.pr.proto.dom.enums;

import java.util.HashMap;

/** 賃金対象区分 */
public enum WageClassificationAtr {
	//0:対象外
	UN_SUBJECT(0),
	//1:対象
	SUBJECT(1);
	
	public final int value;

	/**
	 * Constructor.
	 * 
	 * @param 賃金対象区分の値
	 */
	WageClassificationAtr(int value) {
		this.value = value;
	}
	
	private static HashMap<Integer, WageClassificationAtr> map = new HashMap<>();
	
	static{
		for(WageClassificationAtr item: WageClassificationAtr.values()){
			map.put(item.value, item);
		}
	}
	/**
	 * 
	 * @param value
	 * @return 賃金対象区分
	 */
	public WageClassificationAtr valueOf(int value)
	{
		return map.get(value);
	}

}
