package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;

@Stateless
public class WorkRecordReflectServiceImpl implements WorkRecordReflectService{
	@Inject
	private AppReflectProcessRecord reflectRecord;

	@Override
	public boolean workRecordreflect(AppReflectRecordPara appRecordInfor) {
		ReflectRecordInfor recordInfor = appRecordInfor.getRecordInfor();
		/*WorkReflectedStatesInfo statesInfor = new WorkReflectedStatesInfo(recordInfor.getAppInfor().getReflectionInformation().getStateReflectionReal(),
				recordInfor.getAppInfor().getReflectionInformation().getNotReasonReal().isPresent() ? recordInfor.getAppInfor().getReflectionInformation().getNotReasonReal().get() : null);
		AppReflectInfor reflectInfor = new AppReflectInfor(recordInfor.getDegressAtr(),
				recordInfor.getExecutiontype(),
				recordInfor.getAppInfor().getReflectionInformation().getStateReflection(),
				recordInfor.getAppInfor().getReflectionInformation().getStateReflectionReal());*/
		/*boolean checkReflect = reflectRecord.appReflectProcessRecord(reflectInfor);
		if (!checkReflect) {
			return statesInfor;
		}*/
		if(recordInfor.getAppInfor().getStartDate().isPresent() && recordInfor.getAppInfor().getEndDate().isPresent()) {
			for(int i = 0; recordInfor.getAppInfor().getStartDate().get().daysTo(recordInfor.getAppInfor().getEndDate().get()) - i >= 0; i++){
				GeneralDate loopDate = recordInfor.getAppInfor().getStartDate().get().addDays(i);
				if(!reflectRecord.isRecordData(recordInfor.getAppInfor().getEmployeeID(), loopDate)) {
					return false;
				}
			}	
		}else {
			if(!reflectRecord.isRecordData(recordInfor.getAppInfor().getEmployeeID(), recordInfor.getAppInfor().getAppDate())) {
				return false;
			}	
		}
		//事前事後区分を取得
		if(recordInfor.getAppInfor().getPrePostAtr() == PrePostAtr.PREDICT) {
			//申請種類
			if(recordInfor.getAppInfor().getAppType() == ApplicationType.OVER_TIME_APPLICATION) {
				return reflectRecord.overtimeReflectRecord(appRecordInfor.getOvertimeInfor(), true);
			} else if (recordInfor.getAppInfor().getAppType() == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION) {
				GobackReflectPara gobackpara = appRecordInfor.getGobackInfor();
				return reflectRecord.gobackReflectRecord(gobackpara, true);
			} else if (recordInfor.getAppInfor().getAppType() == ApplicationType.ABSENCE_APPLICATION) {
				CommonReflectPara absenceInfor = appRecordInfor.getAbsenceInfor();
				return reflectRecord.absenceReflectRecor(absenceInfor, true);
			} else if (recordInfor.getAppInfor().getAppType() == ApplicationType.BREAK_TIME_APPLICATION) {
				HolidayWorkReflectPara holidayworkData = appRecordInfor.getHolidayworkInfor();
				return reflectRecord.holidayWorkReflectRecord(holidayworkData, true);
			} else if (recordInfor.getAppInfor().getAppType() == ApplicationType.WORK_CHANGE_APPLICATION) {
				CommonReflectPara workChangeData = appRecordInfor.getWorkchangeInfor();
				return reflectRecord.workChangeReflectRecord(workChangeData, true);
			} else if (recordInfor.getAppInfor().getAppType() == ApplicationType.COMPLEMENT_LEAVE_APPLICATION) {
				CommonReflectPara absenceLeaveData = appRecordInfor.getAbsenceLeaveAppInfor();
				CommonReflectPara recruitmentData = appRecordInfor.getRecruitmentInfor();
				if(absenceLeaveData != null) {
					return reflectRecord.absenceLeaveReflectRecord(absenceLeaveData, true);
				}
				if(recruitmentData != null) {
					return reflectRecord.recruitmentReflectRecord(recruitmentData, true);
				}
			}
		} else {
			if(recordInfor.getAppInfor().getAppType() == ApplicationType.OVER_TIME_APPLICATION) {		
				//TODO: chua giao hang lan nay
				//return reflectRecord.overtimeReflectRecord(appRecordInfor.getOvertimeInfor(), false);
			} else if (recordInfor.getAppInfor().getAppType() == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION) {
				GobackReflectPara gobackpara = appRecordInfor.getGobackInfor();
				return reflectRecord.gobackReflectRecord(gobackpara, false);
			} else if (recordInfor.getAppInfor().getAppType() == ApplicationType.ABSENCE_APPLICATION) {
				CommonReflectPara absenceInfor = appRecordInfor.getAbsenceInfor();
				return reflectRecord.absenceReflectRecor(absenceInfor, false);
			} else if (recordInfor.getAppInfor().getAppType() == ApplicationType.WORK_CHANGE_APPLICATION) {
				CommonReflectPara workChangeData = appRecordInfor.getWorkchangeInfor();
				return reflectRecord.workChangeReflectRecord(workChangeData, false);
			} else if (recordInfor.getAppInfor().getAppType() == ApplicationType.COMPLEMENT_LEAVE_APPLICATION) {
				CommonReflectPara absenceLeaveData = appRecordInfor.getAbsenceLeaveAppInfor();
				CommonReflectPara recruitmentData = appRecordInfor.getRecruitmentInfor();
				if(absenceLeaveData != null) {
					return reflectRecord.absenceLeaveReflectRecord(absenceLeaveData, false);
				}
				if(recruitmentData != null) {
					return reflectRecord.recruitmentReflectRecord(recruitmentData, false);
				}
			}
		}
		
		return false;
	}

}
