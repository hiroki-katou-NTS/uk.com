package nts.uk.ctx.at.request.ac.schedule.appreflectprocess;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ApplicationGobackScheInforDto;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule.ApplicationReflectProcessSche;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule.ReflectScheDto;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.CommonReflectSchePubParam;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.AppReflectProcessSchePub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ApplyTimeAtrPub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ChangeAtrAppGobackPub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ApplicationReflectParamScheDto;

@Stateless
public class ApplicationReflectProcessScheImpl implements ApplicationReflectProcessSche{
	@Inject
	private AppReflectProcessSchePub appReflectSchePub;

	@Override
	public boolean goBackDirectlyReflect(ReflectScheDto reflectSche) {
		ApplicationGobackScheInforDto appInfo = new ApplicationGobackScheInforDto(
				reflectSche.getGoBackDirectly().getWorkChangeAtr().isPresent() ? EnumAdaptor.valueOf(reflectSche.getGoBackDirectly().getWorkChangeAtr().get().value, ChangeAtrAppGobackPub.class) : null,
				reflectSche.getGoBackDirectly().getWorkTypeCD().isPresent() ? reflectSche.getGoBackDirectly().getWorkTypeCD().get().v() : null,
				reflectSche.getGoBackDirectly().getSiftCD().isPresent() ? reflectSche.getGoBackDirectly().getSiftCD().get().v() : null,
				reflectSche.getGoBackDirectly().getWorkTimeStart1().isPresent() ? reflectSche.getGoBackDirectly().getWorkTimeStart1().get().v() : null,
				reflectSche.getGoBackDirectly().getWorkTimeEnd1().isPresent() ? reflectSche.getGoBackDirectly().getWorkTimeEnd1().get().v() : null,
				reflectSche.getGoBackDirectly().getWorkTimeStart2().isPresent() ? reflectSche.getGoBackDirectly().getWorkTimeStart2().get().v() : null,
				reflectSche.getGoBackDirectly().getWorkTimeEnd2().isPresent() ? reflectSche.getGoBackDirectly().getWorkTimeEnd2().get().v() : null);
		ApplicationReflectParamScheDto dto = new ApplicationReflectParamScheDto(reflectSche.getEmployeeId(), 
				reflectSche.getDatePara(),
				reflectSche.getReflectSetting().isChokochoki(),
				appInfo, 
				EnumAdaptor.valueOf(reflectSche.getTimeAtr().value,
						ApplyTimeAtrPub.class)); 
		return appReflectSchePub.goBackDirectlyReflectSch(dto);
	}

	@Override
	public boolean forleaveReflect(ReflectScheDto reflectSche) {
		CommonReflectSchePubParam leavePra = new CommonReflectSchePubParam(reflectSche.getEmployeeId(),
				reflectSche.getDatePara(),
				reflectSche.getForLeave().getWorkTypeCode() != null ? reflectSche.getForLeave().getWorkTypeCode().v() : null, //勤務種類=INPUT．勤務種類コード chi update workType
				null,
				reflectSche.getAppInfor().getStartDate().isPresent() ? reflectSche.getAppInfor().getStartDate().get() : null,
				reflectSche.getAppInfor().getEndDate().isPresent() ? reflectSche.getAppInfor().getEndDate().get() : null,
				reflectSche.getForLeave().getStartTime1() != null ? reflectSche.getForLeave().getStartTime1().v() : null,
				reflectSche.getForLeave().getEndTime1() != null ? reflectSche.getForLeave().getEndTime1().v() : null);
		return appReflectSchePub.appForLeaveSche(leavePra);
	}

	@Override
	public boolean workChangeReflect(ReflectScheDto reflectSche) {
		CommonReflectSchePubParam workChangePara = new CommonReflectSchePubParam(reflectSche.getEmployeeId(), 
				reflectSche.getDatePara(), 
				reflectSche.getWorkChange().getWorkTypeCd(), 
				reflectSche.getWorkChange().getWorkTimeCd(),
				reflectSche.getAppInfor().getStartDate() == null ? null : reflectSche.getAppInfor().getStartDate().get(),
				reflectSche.getAppInfor().getEndDate() == null ? null : reflectSche.getAppInfor().getEndDate().get(),
				null,
				null);
		
		return appReflectSchePub.appWorkChangeReflect(workChangePara);
	}

	@Override
	public boolean holidayWorkReflect(ReflectScheDto relectSche) {
		CommonReflectSchePubParam holidayWork = new CommonReflectSchePubParam(relectSche.getEmployeeId(), 
				relectSche.getDatePara(), 
				relectSche.getHolidayWork().getWorkTimeCode().v(),
				relectSche.getHolidayWork().getWorkTypeCode().v(),
				null, 
				null,
				null,
				null);		
		return appReflectSchePub.holidayWorkReflectSche(holidayWork);
	}

	@Override
	public boolean ebsenceLeaveReflect(ReflectScheDto relectSche) {
		CommonReflectSchePubParam absenceLeave = new CommonReflectSchePubParam(relectSche.getEmployeeId(),
				relectSche.getDatePara(),
				relectSche.getAbsenceLeave().getWorkTypeCD() == null ? null : relectSche.getAbsenceLeave().getWorkTypeCD().v(),
				relectSche.getAbsenceLeave().getWorkTimeCD(), 
				relectSche.getAppInfor().getStartDate().isPresent() ? relectSche.getAppInfor().getStartDate().get() : null,
				relectSche.getAppInfor().getEndDate().isPresent() ? relectSche.getAppInfor().getEndDate().get() : null,
				relectSche.getAbsenceLeave().getWorkTime1().getStartTime() != null ? relectSche.getAbsenceLeave().getWorkTime1().getStartTime().v() : null,
				relectSche.getAbsenceLeave().getWorkTime1().getEndTime() != null ? relectSche.getAbsenceLeave().getWorkTime1().getEndTime().v() : null);
		return appReflectSchePub.absenceLeaveReflectSche(absenceLeave);
	}

	@Override
	public boolean recruitmentReflect(ReflectScheDto relectSche) {
		CommonReflectSchePubParam recruitment = new CommonReflectSchePubParam(relectSche.getEmployeeId(), 
				relectSche.getDatePara(), 
				relectSche.getRecruitment().getWorkTypeCD() != null ? relectSche.getRecruitment().getWorkTypeCD().v() : null, 
				relectSche.getRecruitment().getWorkTimeCD() != null ? relectSche.getRecruitment().getWorkTimeCD().v() : null, 
				relectSche.getAppInfor().getStartDate().isPresent() ? relectSche.getAppInfor().getStartDate().get() : null, 
				relectSche.getAppInfor().getEndDate().isPresent() ? relectSche.getAppInfor().getEndDate().get() : null, 
				relectSche.getRecruitment().getWorkTime1().getStartTime() != null ? relectSche.getRecruitment().getWorkTime1().getStartTime().v() : null, 
				relectSche.getRecruitment().getWorkTime1().getEndTime() != null ? relectSche.getRecruitment().getWorkTime1().getEndTime().v() : null);
		return appReflectSchePub.recruitmentReflectSche(recruitment);
	}

	@Override
	public boolean isSche(String employeeId, GeneralDate baseDate) {
		
		return appReflectSchePub.isSche(employeeId, baseDate);
	}
	
	

}
