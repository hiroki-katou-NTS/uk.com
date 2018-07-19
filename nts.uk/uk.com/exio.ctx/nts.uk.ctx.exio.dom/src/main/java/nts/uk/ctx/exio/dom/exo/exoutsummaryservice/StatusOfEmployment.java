package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

public enum StatusOfEmployment {
	//在職 
	INCUMBENT(1),
	//休職
	LEAVE_OF_ABSENCE(2),
	//休業
	HOLIDAY(3),
	//入社前
	BEFORE_JOINING(4),
	//出向中
	ON_LOAN(5),
	//退職
	RETIREMENT(6);
	
	public int value;
	private StatusOfEmployment(int value){
		this.value = value;
	}
}
