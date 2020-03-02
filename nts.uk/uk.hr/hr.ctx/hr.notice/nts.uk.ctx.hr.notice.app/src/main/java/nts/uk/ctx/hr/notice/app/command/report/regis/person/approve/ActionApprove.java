package nts.uk.ctx.hr.notice.app.command.report.regis.person.approve;

public enum ActionApprove {
	APPROVE(0),
	DENY(1),
	BACK(2),
	CANCEL(3),
	REGISTER(4);
	public final int value;
	private ActionApprove(int value) {
		this.value = value;
	}
}
