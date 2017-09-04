package nts.uk.ctx.at.request.dom.setting.request.application.common;

/** 本人による承認 */
public enum AprovalPersonFlg {
	/* 0:本人 */
	SELF(0),
	/* 1:他の人 */
	OTHER(1);

	public final int value;

	AprovalPersonFlg(int value) {
		this.value = value;
	}

}
