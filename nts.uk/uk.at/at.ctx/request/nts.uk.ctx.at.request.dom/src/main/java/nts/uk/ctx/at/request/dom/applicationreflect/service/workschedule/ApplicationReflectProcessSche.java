package nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule;

import nts.arc.time.GeneralDate;

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
	public boolean forleaveReflect(ReflectScheDto reflectSche);
	/**
	 * 勤務変更申請
	 * @param reflectSche
	 * @return
	 */
	public boolean workChangeReflect(ReflectScheDto reflectSche);
	/**
	 * 休日出勤申請
	 * @param relectSche
	 * @return
	 */
	public boolean holidayWorkReflect(ReflectScheDto relectSche);
	/**
	 * 振休申請の反映
	 * @param relectSche
	 * @return
	 */
	public boolean ebsenceLeaveReflect(ReflectScheDto relectSche);
	/**
	 * 振出申請の反映
	 * @param relectSche
	 * @return
	 */
	public boolean recruitmentReflect(ReflectScheDto relectSche);
	
	public boolean isSche(String employeeId, GeneralDate baseDate);

}
