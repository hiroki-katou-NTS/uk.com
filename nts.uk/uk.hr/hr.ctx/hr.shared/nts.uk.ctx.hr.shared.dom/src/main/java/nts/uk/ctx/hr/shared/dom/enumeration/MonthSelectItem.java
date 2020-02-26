package nts.uk.ctx.hr.shared.dom.enumeration;

/**
 * @author thanhpv
 * 月選択区分
 */
public enum MonthSelectItem {

	JANUARY (1,"1月"),
	
	FEBRUARY (2,"2月"),
	
	MARCH (3,"3月"),
	
	APRIL (4,"4月"),
	
	MAY (5,"5月"),
	
	JUNE (6,"6月"),
	
	JULY (7,"7月"),
	
	AUGUST (8,"8月"),
	
	SEPTEMBER (9,"9月"),
	
	OCTOBER (10,"10月"),
	
	NOVEMBER (11,"11月"),
	
	DECEMBER (12,"12月");
		
	public int value;
	
	public String nameId;

	MonthSelectItem(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
