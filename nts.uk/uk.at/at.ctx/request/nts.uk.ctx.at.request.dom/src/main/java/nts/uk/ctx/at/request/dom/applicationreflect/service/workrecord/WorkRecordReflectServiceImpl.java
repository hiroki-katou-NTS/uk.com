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
		//事前チェック処理
		boolean checkReflect = reflectRecord.appReflectProcessRecord(appRecordInfor.getRecordInfor().getAppInfor(), true);
		if (!checkReflect) {
			return false;
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
