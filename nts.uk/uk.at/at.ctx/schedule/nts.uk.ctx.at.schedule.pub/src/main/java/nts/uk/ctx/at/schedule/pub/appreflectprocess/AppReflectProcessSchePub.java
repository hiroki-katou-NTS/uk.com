package nts.uk.ctx.at.schedule.pub.appreflectprocess;

public interface AppReflectProcessSchePub {
	/**
	 * 	直行直帰申請: 勤務予定への反映
	 * @param reflectPara
	 * @return
	 */
	public boolean goBackDirectlyReflectSch(ApplicationReflectParamScheDto reflectPara);
	/**
	 * 休暇申請
	 * @param reflectPara
	 */
	public void appForLeaveSche(AppForLeavePubDto appForleaverPara);

}
