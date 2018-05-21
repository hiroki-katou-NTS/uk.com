package nts.uk.ctx.at.record.pubimp.dailyperform;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonReflectParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ScheAndRecordSameChangeFlg;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.absence.AbsenceReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.absenceleave.AbsenceLeaveReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback.ChangeAppGobackAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback.GobackAppParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback.GobackReflectParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback.PreGoBackReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback.PriorStampAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback.ScheTimeReflectAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime.HolidayWorktimeAppPara;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime.HolidayWorktimePara;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime.PreHolidayWorktimeReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.AfterOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.OverTimeRecordAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.OvertimeAppParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.OvertimeParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.recruitment.RecruitmentRelectRecordService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.workchange.PreWorkchangeReflectService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppCommonPara;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppReflectProcessRecordPub;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.CommonReflectPubParameter;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.HolidayWorkReflectPubPara;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.GobackReflectPubParameter;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.overtime.PreOvertimePubParameter;

@Stateless
public class AppReflectProcessRecordPubImpl implements AppReflectProcessRecordPub{
	@Inject
	private PreGoBackReflectService preGobackReflect;
	@Inject
	private PreOvertimeReflectService preOvertimeReflect;
	@Inject
	private AfterOvertimeReflectService afterOvertimeReflect;
	@Inject
	private AbsenceReflectService absenceReflect;
	@Inject
	private PreHolidayWorktimeReflectService holidayworkService;
	@Inject
	private PreWorkchangeReflectService workChangeService;
	@Inject
	private AbsenceLeaveReflectService absenceLeaveService;
	@Inject
	private RecruitmentRelectRecordService recruitmentService;
	@Inject
	private WorkInformationRepository workRepository;
	@Override
	public boolean appReflectProcess(AppCommonPara para) {
		/*CommonCheckParameter paraTemp = new CommonCheckParameter(EnumAdaptor.valueOf(para.getDegressAtr().value, DegreeReflectionAtr.class),
				EnumAdaptor.valueOf(para.getExecutiontype().value, ExecutionType.class),
				EnumAdaptor.valueOf(para.getStateReflectionReal().value, ReflectedStateRecord.class),
				EnumAdaptor.valueOf(para.getStateReflection().value, ReflectedStateRecord.class));
		return commonProcess.commonProcessCheck(paraTemp);*/
		return true;
	}

	@Override
	public boolean preGobackReflect(GobackReflectPubParameter para) {
		return preGobackReflect.gobackReflect(this.toDomainGobackReflect(para));		
	}

	@Override
	public boolean afterGobackReflect(GobackReflectPubParameter para) {		
		return preGobackReflect.afterGobackReflect(this.toDomainGobackReflect(para));		
	}
	private GobackReflectParameter toDomainGobackReflect(GobackReflectPubParameter para) {
		GobackAppParameter appPara = new GobackAppParameter(EnumAdaptor.valueOf(para.getGobackData().getChangeAppGobackAtr().value, ChangeAppGobackAtr.class),
				para.getGobackData().getWorkTimeCode(),
				para.getGobackData().getWorkTypeCode(),
				para.getGobackData().getStartTime1(),
				para.getGobackData().getEndTime1(),
				para.getGobackData().getStartTime2(),
				para.getGobackData().getEndTime2());
		GobackReflectParameter gobackPara = new GobackReflectParameter(para.getEmployeeId(),
				para.getDateData(),
				para.isOutResReflectAtr(),
				EnumAdaptor.valueOf(para.getPriorStampAtr().value, PriorStampAtr.class),
				EnumAdaptor.valueOf(para.getScheAndRecordSameChangeFlg().value, ScheAndRecordSameChangeFlg.class),
				EnumAdaptor.valueOf(para.getScheTimeReflectAtr().value, ScheTimeReflectAtr.class),
				para.isScheReflectAtr(),
				appPara);
		return gobackPara;
		
	}

	@Override
	public boolean preOvertimeReflect(PreOvertimePubParameter param) {
		return preOvertimeReflect.overtimeReflect(this.toDomainOvertimeReflect(param));		
	}

	@Override
	public boolean afterOvertimeReflect(PreOvertimePubParameter param) {
		return afterOvertimeReflect.reflectAfterOvertime(this.toDomainOvertimeReflect(param));		
	}

	private OvertimeParameter toDomainOvertimeReflect(PreOvertimePubParameter param) {
		OvertimeAppParameter appOver = new OvertimeAppParameter(
				param.getOvertimePara().getWorkTypeCode(), 
				param.getOvertimePara().getWorkTimeCode(),
				param.getOvertimePara().getStartTime1(), 
				param.getOvertimePara().getEndTime1(),
				param.getOvertimePara().getStartTime2(),
				param.getOvertimePara().getEndTime2(),
				param.getOvertimePara().getMapOvertimeFrame(),
				param.getOvertimePara().getOverTimeShiftNight(),
				param.getOvertimePara().getFlexExessTime(),
				EnumAdaptor.valueOf(param.getOvertimePara().getOverTimeAtr().value, OverTimeRecordAtr.class));
		OvertimeParameter overtimePara = new OvertimeParameter(param.getEmployeeId(), 
				param.getDateInfo(), 
				param.isActualReflectFlg(), 
				param.isScheReflectFlg(), 
				param.isTimeReflectFlg(), 
				param.isAutoClearStampFlg(), 
				EnumAdaptor.valueOf(param.getScheAndRecordSameChangeFlg().value, ScheAndRecordSameChangeFlg.class),
				param.isScheTimeOutFlg(),
				appOver);
		return overtimePara;
	}

	@Override
	public boolean absenceReflect(CommonReflectPubParameter param, boolean isPre) {
		return absenceReflect.absenceReflect(this.toRecordPara(param), isPre);		
	}

	@Override
	public boolean holidayWorkReflect(HolidayWorkReflectPubPara param, boolean isPre) {
		HolidayWorktimeAppPara appPara = new HolidayWorktimeAppPara(param.getHolidayWorkPara().getWorkTypeCode(),
				param.getHolidayWorkPara().getWorkTimeCode(),
				param.getHolidayWorkPara().getMapWorkTimeFrame(),
				param.getHolidayWorkPara().getNightTime(),
				param.getHolidayWorkPara().getStartTime(),
				param.getHolidayWorkPara().getEndTime());
		HolidayWorktimePara para = new HolidayWorktimePara(param.getEmployeeId(),
				param.getBaseDate(),
				param.isHolidayWorkReflectFlg(), 
				EnumAdaptor.valueOf(param.getScheAndRecordSameChangeFlg().value, ScheAndRecordSameChangeFlg.class),
				param.isScheReflectFlg(),
				appPara);
		return holidayworkService.preHolidayWorktimeReflect(para, isPre);
	}

	@Override
	public boolean workChangeReflect(CommonReflectPubParameter param, boolean isPre) {
		
		return workChangeService.workchangeReflect(this.toRecordPara(param), isPre);
		
	}
	
	private CommonReflectParameter toRecordPara(CommonReflectPubParameter param) {
		CommonReflectParameter outputData = new CommonReflectParameter(param.getEmployeeId(), param.getBaseDate(), EnumAdaptor.valueOf(param.getScheAndRecordSameChangeFlg().value, ScheAndRecordSameChangeFlg.class),
			 	param.isScheTimeReflectAtr(),
			 	param.getWorkTypeCode(),
			 	param.getWorkTimeCode(),			 	
			 	param.getStartDate(),
			 	param.getEndDate(),
			 	param.getStartTime(),
			 	param.getEndTime());
		return outputData;
	}

	@Override
	public boolean absenceLeaveReflect(CommonReflectPubParameter param, boolean isPre) {
		return absenceLeaveService.reflectAbsenceLeave(this.toRecordPara(param), isPre);
	}

	@Override
	public boolean recruitmentReflect(CommonReflectPubParameter param, boolean isPre) {
		return recruitmentService.recruitmentReflect(this.toRecordPara(param), isPre);
	}

	@Override
	public boolean isRecordData(String employeeId, GeneralDate baseDate) {
		//日別実績の勤務情報
		Optional<WorkInfoOfDailyPerformance> optDailyPerfor = workRepository.find(employeeId, baseDate); 
		if(!optDailyPerfor.isPresent()) {
			return false;
		}
		return true;
	}

}
