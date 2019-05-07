package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

/**年数種類*/
public enum YearType {

	STAYINGYEARS (1,"滞留年数"),

	AGE (2,"年齢"),
	
	LENGTH_OF_SERVICE (3, "勤続年数");
	
	public int value;
	
	public String nameId;
	
	YearType(int type,String nameId){
		this.value = type;
		this.nameId = nameId;
	}
}
