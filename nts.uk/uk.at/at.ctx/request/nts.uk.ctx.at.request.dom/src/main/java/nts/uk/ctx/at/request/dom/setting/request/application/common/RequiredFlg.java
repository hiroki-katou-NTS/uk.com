package nts.uk.ctx.at.request.dom.setting.request.application.common;

/** 申請理由が必須 */
public enum RequiredFlg {
	/* 必須ない */
	NOTREQUIRED(0),
	/* 必須 */
	REQUIRED(1);

	public final int value;

	RequiredFlg(int value) {
		this.value = value;
	}
}
