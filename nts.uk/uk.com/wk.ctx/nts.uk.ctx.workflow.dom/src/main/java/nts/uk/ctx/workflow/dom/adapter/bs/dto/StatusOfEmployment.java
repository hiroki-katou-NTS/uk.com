package nts.uk.ctx.workflow.dom.adapter.bs.dto;
/**
 * 在職状態
 * @author Doan Duy Hung
 *
 */
public enum StatusOfEmployment {
	//在職 
	INCUMBENT(1, "在職 "),
	//休職
	LEAVE_OF_ABSENCE(2, "休職"),
	//休業
	HOLIDAY(3, "休業"),
	//入社前
	BEFORE_JOINING(4, "入社前"),
	//出向中
	ON_LOAN(5, "出向中"),
	//退職
	RETIREMENT(6, "退職");
	
	public int value;
	public String name;
	StatusOfEmployment(int value, String name){
		this.value = value;
		this.name = name;
	}
}
