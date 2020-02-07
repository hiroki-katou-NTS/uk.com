package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

/**条件種類*/
public enum RequirementType {


	YEARS (1,"年数"),

	SELECT_FROM_MASTER (2,"マスタから選択する"),
	
	INPUT (3, "入力する");
	
	public int value;
	
	public String nameId;
	
	RequirementType(int type,String nameId){
		this.value = type;
		this.nameId = nameId;
	}
}
