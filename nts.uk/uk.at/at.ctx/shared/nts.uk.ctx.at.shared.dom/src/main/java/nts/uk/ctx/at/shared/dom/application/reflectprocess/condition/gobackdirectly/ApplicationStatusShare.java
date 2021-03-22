package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly;
//従業員が申請時に選択する条件
public enum ApplicationStatusShare {
	
	DO_NOT_REFLECT(0,"反映しない"),
	
	DO_REFLECT(1,"反映する"),
	
	DO_NOT_REFLECT_1(2,"申請時に決める(初期値：反映しない)"),
	
	DO_REFLECT_1(3,"申請時に決める(初期値：反映する)");
	
	public final int value;
	
	public final String nameId;
	
	private ApplicationStatusShare(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
