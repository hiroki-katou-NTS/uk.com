package nts.uk.ctx.at.request.dom.setting.request.application.common;

/**申請反映設定*/
public enum ReflectionFlg {
	/* 0:反映しない */
	NOTREFLECT(0),
	/* 1:反映する */
	REFLECT(1);

	public final int value;

	ReflectionFlg(int value) {
		this.value = value;
	}

}
