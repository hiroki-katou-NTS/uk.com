package nts.uk.ctx.pr.proto.dom.itemmaster;

import java.util.HashMap;

/** 項目属性 */
public enum ItemAtr {
	//0:時間
	HOURS(0),
	//1:回数
	TIMES(1),	
	//2:金額（小数点無し）
	AMOUNT_NO_DECIMAL(2),
	//3:金額（小数点あり）
	AMOUNT_WITH_DECIMAL(3),
	//4:文字
	CHARACTERS(4);
	
	public final int value;

	/**
	 * Constructor.
	 * 
	 * @param 項目属性 
	 */
	ItemAtr(int value) {
		this.value = value;
	}
	
	private static HashMap<Integer, ItemAtr> map = new HashMap<>();
	
	static{
		for(ItemAtr item: ItemAtr.values()){
			map.put(item.value, item);
		}
	}
	
	public static ItemAtr valueOf(int value)	{
		return map.get(value);
	}
}
