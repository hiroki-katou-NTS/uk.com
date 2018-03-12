package nts.uk.ctx.at.schedule.pub.applicationreflectprocess.applicationsmanager;

public interface ApplicationReflectProcessSchePub {
	/**
	 * 	直行直帰申請: 勤務予定への反映
	 * @param reflectPara
	 * @return
	 */
	public ReflectedStatesScheInfoDto goBackDirectlyReflectSch(GoBackDirectlyReflectParamDto reflectPara);

}
