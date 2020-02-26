package nts.uk.ctx.hr.shared.dom.enumeration;

/**
 * @author thanhpv
 * 日付選択区分
 */
public enum DateSelectItem {

	DAY1 (1,"1日"),
	
	DAY2 (2,"2日"),
	
	DAY3 (3,"3日"),
	
	DAY4 (4,"4日"),
	
	DAY5 (5,"5日"),
	
	DAY6 (6,"6日"),
	
	DAY7 (7,"7日"),
	
	DAY8 (8,"8日"),
	
	DAY9 (9,"9日"),
	
	DAY10 (10,"10日"),
	
	DAY11 (11,"11日"),
	
	DAY12 (12,"12日"),
	
	DAY13 (13,"13日"),
	
	DAY14 (14,"14日"),
	
	DAY15 (15,"15日"),
	
	DAY16 (16,"16日"),
	
	DAY17 (17,"17日"),
	
	DAY18 (18,"18日"),
	
	DAY19 (19,"19日"),
	
	DAY20 (20,"20日"),
	
	DAY21 (21,"21日"),
	
	DAY22 (22,"22日"),
	
	DAY23 (23,"23日"),
	
	DAY24 (24,"24日"),
	
	DAY25 (25,"25日"),
	
	DAY26 (26,"26日"),
	
	DAY27 (27,"27日"),
	
	DAY28 (28,"28日"),
	
	DAY29 (29,"29日"),
	
	DAY30 (30,"30日"),
	
	LATS_DAY_MONTH (31,"末日");
		
	public int value;
	
	public String nameId;

	DateSelectItem(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
