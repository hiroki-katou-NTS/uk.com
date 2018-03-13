package nts.uk.ctx.at.schedule.pub.appreflectprocess;

public enum OutsetBreakReflectScheAtrPub {
	/**	反映しない */
	NOTREFLECT(0),
	/** 反映する */
	REFLECT(1);
	public int value;
	OutsetBreakReflectScheAtrPub(int type) {
		this.value = type;
	}
}
