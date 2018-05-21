package nts.uk.ctx.at.request.ac.record.dailyperform.appreflect;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppCommonPara;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppReflectProcessRecordPub;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.CommonReflectPubParameter;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.DegreeReflectionPubAtr;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ExecutionPubType;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.HolidayWorkReflectPubPara;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.HolidayWorktimeAppPubPara;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ReasonNotReflectDailyPubRecord;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ReasonNotReflectPubRecord;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ReflectedStatePubRecord;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ScheAndRecordSameChangePubFlg;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.ChangeAppGobackPubAtr;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.GobackAppPubParameter;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.GobackReflectPubParameter;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.PriorStampPubAtr;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.ScheTimeReflectPubAtr;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.overtime.OverTimeRecordPubAtr;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.overtime.OvertimeAppPubParameter;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.overtime.PreOvertimePubParameter;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.CommonReflectPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppReflectInfor;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppReflectProcessRecord;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.GobackReflectPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.HolidayWorkReflectPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.OvertimeReflectPara;

@Stateless
public class AppReflectProcessRecordImpl implements AppReflectProcessRecord {
	@Inject
	private AppReflectProcessRecordPub recordPub;
	
	@Override
	public boolean appReflectProcessRecord(AppReflectInfor info) {
		AppCommonPara para = new AppCommonPara(EnumAdaptor.valueOf(info.getDegressAtr().value, DegreeReflectionPubAtr.class),
				EnumAdaptor.valueOf(info.getExecutiontype().value, ExecutionPubType.class),
				EnumAdaptor.valueOf(info.getStateReflection().value, ReflectedStatePubRecord.class),
				info.getStateReflectionReal() == null ? null : EnumAdaptor.valueOf(info.getStateReflectionReal().value, ReflectedStatePubRecord.class)); 
		return recordPub.appReflectProcess(para);
	}

	@Override
	public boolean gobackReflectRecord(GobackReflectPara para, boolean isPre) {
		GobackAppPubParameter gobackPra = new GobackAppPubParameter(EnumAdaptor.valueOf(para.getGobackData().getChangeAppGobackAtr().value,
				ChangeAppGobackPubAtr.class), para.getGobackData().getWorkTimeCode(),
				para.getGobackData().getWorkTypeCode(), 
				para.getGobackData().getStartTime1(),
				para.getGobackData().getEndTime1(), 
				para.getGobackData().getStartTime2(),
				para.getGobackData().getEndTime2(),
				EnumAdaptor.valueOf(para.getGobackData().getReflectState().value, ReflectedStatePubRecord.class), 
				para.getGobackData().getReasoNotReflect() == null ? null : EnumAdaptor.valueOf(para.getGobackData().getReasoNotReflect().value, ReasonNotReflectPubRecord.class));
		GobackReflectPubParameter pubPara = new GobackReflectPubParameter(para.getEmployeeId(), 
				para.getDateData(),
				para.isOutResReflectAtr(),
				EnumAdaptor.valueOf(para.getPriorStampAtr().value, PriorStampPubAtr.class),
				EnumAdaptor.valueOf(para.getScheAndRecordSameChangeFlg().value, ScheAndRecordSameChangePubFlg.class),
				EnumAdaptor.valueOf(para.getScheTimeReflectAtr().value, ScheTimeReflectPubAtr.class),
				para.isScheReflectAtr(),
				gobackPra);
		if(isPre) {
			 return recordPub.preGobackReflect(pubPara);
		} else {
			return recordPub.afterGobackReflect(pubPara);
		}
	}

	@Override
	public boolean overtimeReflectRecord(OvertimeReflectPara para, boolean isPre) {
		OvertimeAppPubParameter overtimePara = new OvertimeAppPubParameter(EnumAdaptor.valueOf(para.getOvertimePara().getReflectedState().value, ReflectedStatePubRecord.class),
				EnumAdaptor.valueOf(para.getOvertimePara().getReasonNotReflect() == null ? 0 : para.getOvertimePara().getReasonNotReflect().value, ReasonNotReflectPubRecord.class),
				para.getOvertimePara().getWorkTypeCode(),
				para.getOvertimePara().getWorkTimeCode(),
				para.getOvertimePara().getStartTime1(),
				para.getOvertimePara().getEndTime1(),
				para.getOvertimePara().getStartTime2(),
				para.getOvertimePara().getEndTime2(),
				para.getOvertimePara().getMapOvertimeFrame(),
				para.getOvertimePara().getOverTimeShiftNight(),
				para.getOvertimePara().getFlexExessTime(),
				EnumAdaptor.valueOf(para.getOvertimePara().getOverTimeAtr().value, OverTimeRecordPubAtr.class));
		PreOvertimePubParameter preOvertimePara = new PreOvertimePubParameter(para.getEmployeeId(), 
				para.getDateInfo(), 
				para.isActualReflectFlg(), 
				para.isScheReflectFlg(), 
				para.isTimeReflectFlg(), 
				para.isAutoClearStampFlg(), 
				EnumAdaptor.valueOf(para.getScheAndRecordSameChangeFlg().value, ScheAndRecordSameChangePubFlg.class), 
				para.isScheTimeOutFlg(), 
				overtimePara);
		if(isPre) {
			return recordPub.preOvertimeReflect(preOvertimePara);	
		} else {
			return recordPub.afterOvertimeReflect(preOvertimePara);
		}
	}

	@Override
	public boolean absenceReflectRecor(CommonReflectPara para, boolean isPre) {
		return recordPub.absenceReflect(this.toPubPara(para), isPre);		
	}

	@Override
	public boolean holidayWorkReflectRecord(HolidayWorkReflectPara para, boolean isPre) {
		HolidayWorktimeAppPubPara appPara = new HolidayWorktimeAppPubPara(para.getHolidayWorkPara().getWorkTypeCode(), 
				para.getHolidayWorkPara().getWorkTimeCode(), 
				para.getHolidayWorkPara().getMapWorkTimeFrame(), 
				para.getHolidayWorkPara().getNightTime(), 
				EnumAdaptor.valueOf(para.getHolidayWorkPara().getReflectedState().value, ReflectedStatePubRecord.class), 
				para.getHolidayWorkPara().getReasonNotReflect() == null ? null : EnumAdaptor.valueOf(para.getHolidayWorkPara().getReasonNotReflect().value, ReasonNotReflectDailyPubRecord.class),
				para.getHolidayWorkPara().getStartTime(),
				para.getHolidayWorkPara().getEndTime()); 
		HolidayWorkReflectPubPara pubPara = new HolidayWorkReflectPubPara(para.getEmployeeId(), 
				para.getBaseDate(),
				para.isHolidayWorkReflectFlg(),
			 	EnumAdaptor.valueOf(para.getScheAndRecordSameChangeFlg().value, ScheAndRecordSameChangePubFlg.class), 
				para.isScheReflectFlg(), 
				appPara);
		
		return recordPub.holidayWorkReflect(pubPara, isPre);		
	}

	@Override
	public boolean workChangeReflectRecord(CommonReflectPara para, boolean isPre) {		
		return recordPub.workChangeReflect(this.toPubPara(para), isPre);		
	}
	
	private CommonReflectPubParameter toPubPara(CommonReflectPara para) {
		CommonReflectPubParameter pubPara = new CommonReflectPubParameter(para.getEmployeeId(),
				para.getBaseDate(), 
				EnumAdaptor.valueOf(para.getScheAndRecordSameChangeFlg().value, ScheAndRecordSameChangePubFlg.class),
				para.isScheTimeReflectAtr(),
				para.getWorkTypeCode(), 
				para.getWorkTimeCode(),
				para.getStartDate(),
				para.getEndDate(),
				para.getStartTime(),
				para.getEndTime());
		return pubPara;
	}

	@Override
	public boolean absenceLeaveReflectRecord(CommonReflectPara para, boolean isPre) {
		return recordPub.absenceLeaveReflect(this.toPubPara(para), isPre);
	}

	@Override
	public boolean recruitmentReflectRecord(CommonReflectPara para, boolean isPre) {
		return recordPub.recruitmentReflect(this.toPubPara(para), isPre);
	}

	@Override
	public boolean isRecordData(String employeeId, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return recordPub.isRecordData(employeeId, baseDate);
	}
	
	

}
