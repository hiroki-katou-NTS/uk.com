package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

/**
 * 確認ルート種類
 * @author hoatt
 *
 */
public enum ConfirmationRootType {
	/** 日次確認*/
	DAILY_CONFIRMATION(0,"日次確認"),
	/** 月次確認*/
	MONTHLY_CONFIRMATION(1,"月次確認");
	
	public Integer value;
	
	public String nameId;

	private ConfirmationRootType(Integer value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
