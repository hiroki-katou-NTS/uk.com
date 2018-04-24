package nts.uk.ctx.at.record.pubimp.dailyperform;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EnumType;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ApplicationReflectOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonCheckParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonReflectParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.DegreeReflectionAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ExecutionType;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReasonNotReflectRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReflectedStateRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ScheAndRecordSameChangeFlg;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.absence.AbsenceReflectService;
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
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.OvertimeAppParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.OvertimeParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.workchange.PreWorkchangeReflectService;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppCommonPara;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppReflectProcessRecordPub;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppReflectPubOutput;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.CommonReflectPubParameter;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.HolidayWorkReflectPubPara;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ReasonNotReflectPubRecord;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ReflectedStatePubRecord;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.GobackReflectPubParameter;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.overtime.PreOvertimePubParameter;

@Stateless
public class AppReflectProcessRecordPubImpl implements AppReflectProcessRecordPub{
	@Inject
	private CommonProcessCheckService commonProcess;
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
	@Override
	public boolean appReflectProcess(AppCommonPara para) {
		CommonCheckParameter paraTemp = new CommonCheckParameter(EnumAdaptor.valueOf(para.getDegressAtr().value, DegreeReflectionAtr.class),
				EnumAdaptor.valueOf(para.getExecutiontype().value, ExecutionType.class),
				EnumAdaptor.valueOf(para.getStateReflectionReal().value, ReflectedStateRecord.class),
				EnumAdaptor.valueOf(para.getStateReflection().value, ReflectedStateRecord.class));
		return commonProcess.commonProcessCheck(paraTemp);
	}

	@Override
	public AppReflectPubOutput preGobackReflect(GobackReflectPubParameter para) {
		ApplicationReflectOutput reflectInfor = preGobackReflect.gobackReflect(this.toDomainGobackReflect(para));
		AppReflectPubOutput reflectOutput = new AppReflectPubOutput(EnumAdaptor.valueOf(reflectInfor.getReflectedState().value, ReflectedStatePubRecord.class), 
				reflectInfor.getReasonNotReflect() == null ? null : EnumAdaptor.valueOf(reflectInfor.getReasonNotReflect().value, ReasonNotReflectPubRecord.class));
		return reflectOutput;
	}

	@Override
	public AppReflectPubOutput afterGobackReflect(GobackReflectPubParameter para) {		
		ApplicationReflectOutput reflectInfor = preGobackReflect.afterGobackReflect(this.toDomainGobackReflect(para));
		AppReflectPubOutput reflectOutput = new AppReflectPubOutput(EnumAdaptor.valueOf(reflectInfor.getReflectedState().value, ReflectedStatePubRecord.class), 
				reflectInfor.getReasonNotReflect() == null ? null : EnumAdaptor.valueOf(reflectInfor.getReasonNotReflect().value, ReasonNotReflectPubRecord.class));
		return reflectOutput;
	}
	private GobackReflectParameter toDomainGobackReflect(GobackReflectPubParameter para) {
		GobackAppParameter appPara = new GobackAppParameter(EnumAdaptor.valueOf(para.getGobackData().getChangeAppGobackAtr().value, ChangeAppGobackAtr.class),
				para.getGobackData().getWorkTimeCode(),
				para.getGobackData().getWorkTypeCode(),
				para.getGobackData().getStartTime1(),
				para.getGobackData().getEndTime1(),
				para.getGobackData().getStartTime2(),
				para.getGobackData().getEndTime2(),
				EnumAdaptor.valueOf(para.getGobackData().getReflectState().value, ReflectedStateRecord.class),
				para.getGobackData().getReasoNotReflect() == null ? null : EnumAdaptor.valueOf(para.getGobackData().getReasoNotReflect().value, ReasonNotReflectRecord.class));
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
	public AppReflectPubOutput preOvertimeReflect(PreOvertimePubParameter param) {
		ApplicationReflectOutput reflect = preOvertimeReflect.overtimeReflect(this.toDomainOvertimeReflect(param));
		AppReflectPubOutput reflectOutput = new AppReflectPubOutput(EnumAdaptor.valueOf(reflect.getReflectedState().value, ReflectedStatePubRecord.class),
				EnumAdaptor.valueOf(reflect.getReasonNotReflect().value, ReasonNotReflectPubRecord.class));
		return reflectOutput;
	}

	@Override
	public AppReflectPubOutput afterOvertimeReflect(PreOvertimePubParameter param) {
		ApplicationReflectOutput reflect = afterOvertimeReflect.reflectAfterOvertime(this.toDomainOvertimeReflect(param));
		AppReflectPubOutput reflectOutput = new AppReflectPubOutput(EnumAdaptor.valueOf(reflect.getReflectedState().value, ReflectedStatePubRecord.class),
				EnumAdaptor.valueOf(reflect.getReasonNotReflect().value, ReasonNotReflectPubRecord.class));
		return reflectOutput;
	}

	private OvertimeParameter toDomainOvertimeReflect(PreOvertimePubParameter param) {
		OvertimeAppParameter appOver = new OvertimeAppParameter(EnumAdaptor.valueOf(param.getOvertimePara().getReflectedState().value, ReflectedStateRecord.class),
				EnumAdaptor.valueOf(param.getOvertimePara().getReasonNotReflect().value, ReasonNotReflectRecord.class),
				param.getOvertimePara().getWorkTypeCode(), 
				param.getOvertimePara().getWorkTimeCode(),
				param.getOvertimePara().getStartTime1(), 
				param.getOvertimePara().getEndTime1(),
				param.getOvertimePara().getStartTime2(),
				param.getOvertimePara().getEndTime2(),
				param.getOvertimePara().getMapOvertimeFrame(),
				param.getOvertimePara().getOverTimeShiftNight(),
				param.getOvertimePara().getFlexExessTime());
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
	public AppReflectPubOutput absenceReflect(CommonReflectPubParameter param, boolean isPre) {
		ApplicationReflectOutput dataReflect = absenceReflect.absenceReflect(this.toRecordPara(param), isPre);
		AppReflectPubOutput dataOutput = new AppReflectPubOutput(EnumAdaptor.valueOf(dataReflect.getReflectedState().value, ReflectedStatePubRecord.class), 
				EnumAdaptor.valueOf(dataReflect.getReasonNotReflect().value, ReasonNotReflectPubRecord.class));
		return dataOutput;
	}

	@Override
	public AppReflectPubOutput holidayWorkReflect(HolidayWorkReflectPubPara param) {
		HolidayWorktimeAppPara appPara = new HolidayWorktimeAppPara(param.getHolidayWorkPara().getWorkTypeCode(),
				param.getHolidayWorkPara().getWorkTimeCode(),
				param.getHolidayWorkPara().getMapWorkTimeFrame(),
				param.getHolidayWorkPara().getNightTime(),
				EnumAdaptor.valueOf(param.getHolidayWorkPara().getReflectedState().value, ReflectedStateRecord.class), 
				param.getHolidayWorkPara().getReasonNotReflect() == null ? null : EnumAdaptor.valueOf(param.getHolidayWorkPara().getReasonNotReflect().value, ReasonNotReflectRecord.class));
		HolidayWorktimePara para = new HolidayWorktimePara(param.getEmployeeId(),
				param.getBaseDate(),
				param.isHolidayWorkReflectFlg(), 
				EnumAdaptor.valueOf(param.getScheAndRecordSameChangeFlg().value, ScheAndRecordSameChangeFlg.class),
				param.isScheReflectFlg(),
				appPara);
		ApplicationReflectOutput outputData = holidayworkService.preHolidayWorktimeReflect(para);
		ReasonNotReflectPubRecord tem = outputData.getReflectedState() == null ? ReasonNotReflectPubRecord.ACTUAL_CONFIRMED : ReasonNotReflectPubRecord.NOT_PROBLEM;
		return new AppReflectPubOutput(EnumAdaptor.valueOf(outputData.getReflectedState().value, ReflectedStatePubRecord.class),
				outputData.getReflectedState() == null ? null : EnumAdaptor.valueOf(outputData.getReasonNotReflect().value, ReasonNotReflectPubRecord.class));
	}

	@Override
	public AppReflectPubOutput workChangeReflect(CommonReflectPubParameter param, boolean isPre) {
		
		ApplicationReflectOutput workChangeOut = workChangeService.workchangeReflect(this.toRecordPara(param), isPre);
		AppReflectPubOutput outPutData = new AppReflectPubOutput(EnumAdaptor.valueOf(workChangeOut.getReflectedState().value, ReflectedStatePubRecord.class),
				EnumAdaptor.valueOf(workChangeOut.getReasonNotReflect().value, ReasonNotReflectPubRecord.class));
		
		return outPutData;
	}
	
	private CommonReflectParameter toRecordPara(CommonReflectPubParameter param) {
		CommonReflectParameter workchangePara = new CommonReflectParameter(param.getEmployeeId(), param.getBaseDate(), EnumAdaptor.valueOf(param.getScheAndRecordSameChangeFlg().value, ScheAndRecordSameChangeFlg.class),
			 	param.isScheTimeReflectAtr(),
			 	param.getWorkTypeCode(),
			 	param.getWorkTimeCode(),
			 	EnumAdaptor.valueOf(param.getReflectState().value, ReflectedStateRecord.class),
			 	param.getReasoNotReflect() == null ? null : EnumAdaptor.valueOf(param.getReasoNotReflect().value, ReasonNotReflectRecord.class),
			 	param.getStartDate(),
			 	param.getEndDate());
		return workchangePara;
	}

}
