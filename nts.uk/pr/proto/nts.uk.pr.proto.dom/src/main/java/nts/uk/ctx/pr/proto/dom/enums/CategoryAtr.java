package nts.uk.ctx.pr.proto.dom.enums;

import java.util.HashMap;

/**カテゴリ区分 */
public enum CategoryAtr {
	// 0:支給
	PAYMENT(0),
	// 1:控除
	DEDUCTION(1),
	// 2:勤怠
	DILIGENCE(2),
	// 3:記事
	ARTICLES(3),
	// 9:その他
	OTHER(4);

	public final int value;

	private static HashMap<Integer, CategoryAtr> map = new HashMap<>();
	
	static {
		for(CategoryAtr item: CategoryAtr.values()){
			map.put(item.value, item);
		}
	}
	/**
	 * Constructor.
	 * 
	 * @param カテゴリ区分の値
	 */
	CategoryAtr(int value) {
		this.value = value;
	}
	
	public static CategoryAtr valuesOf(int value){
		return map.get(value);
	}
}
