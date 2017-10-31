package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output;
/**
 * @author hieult
 */
public enum User {
	//申請本人&承認者
	APPLICANT_APPROVER(0),
	//承認者
	APPROVER(1),
	//申請本人
	APPLICANT(2),
	//その他
	OTHER(99);

	public int value;

	User(int type) {
		this.value = type;
	}
}
