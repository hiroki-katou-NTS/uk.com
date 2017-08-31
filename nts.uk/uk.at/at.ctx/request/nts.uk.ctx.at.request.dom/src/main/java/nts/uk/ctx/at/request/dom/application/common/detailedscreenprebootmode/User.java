package nts.uk.ctx.at.request.dom.application.common.detailedscreenprebootmode;
/**
 * @author hieult
 */
public enum User {
	APPLICANT_APPROVER(0),
	APPROVER(1),
	APPLICANT(2),
	OTHER(99);

	public int value;

	User(int type) {
		this.value = type;
	}
}
