package nts.uk.ctx.at.request.dom.setting.request.application.common;
/** 反映時刻優先 */
public enum PriorityFLg {
	/* 0:実打刻を優先する */
	ACTUAL_STAMPING(0),
	/* 1:申請の時刻を優先する */
	TIME_APPLICATION(1);

	public final int value;

	PriorityFLg(int value) {
		this.value = value;
	}


}
