package nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule;

public interface ApplicationReflectProcessSche {
	/**
	 * 直行直帰申請
	 * @param reflectSche
	 * @return
	 */
	public boolean goBackDirectlyReflect(ReflectScheDto reflectSche);
	/**
	 * 休暇申請
	 * @param reflectSche
	 */
	public void forleaveReflect(ReflectScheDto reflectSche);
	/**
	 * 勤務変更申請
	 * @param reflectSche
	 * @return
	 */
	public boolean workChangeReflect(ReflectScheDto reflectSche);

}
