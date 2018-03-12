package nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche;
/**
 * 振出・休出時反映する区分
 * @author dudt
 *
 */
public enum OutsetBreakReflectScheAtr {
	/**	反映しない */
	NOTREFLECT(0),
	/** 反映する */
	REFLECT(1);
	public int value;
	OutsetBreakReflectScheAtr(int type) {
		this.value = type;
	}
}
