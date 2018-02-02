package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset;

public enum AllowAtr {
	/**許可しない*/
	NOTALLOW(0),
	/**1:許可する*/
	ALLOW(1);

	public final int value;

	AllowAtr(int value) {
		this.value = value;
	}

}
