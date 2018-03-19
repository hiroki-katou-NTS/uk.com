package nts.uk.ctx.at.request.ac.record.dailyperform.appreflect;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppCommonPara;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppReflectProcessRecordPub;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppReflectPubOutput;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.DegreeReflectionPubAtr;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ExecutionPubType;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ReasonNotReflectPubRecord;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ReflectedStatePubRecord;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.ChangeAppGobackPubAtr;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.GobackAppPubParameter;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.GobackReflectPubParameter;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.PriorStampPubAtr;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.ScheAndRecordSameChangePubFlg;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.ScheTimeReflectPubAtr;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.applicationreflect.service.ReflectedStatesInfo;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppReflectInfor;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppReflectProcessRecord;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.GobackReflectPara;

@Stateless
public class AppReflectProcessRecordImpl implements AppReflectProcessRecord {
	@Inject
	private AppReflectProcessRecordPub recordPub;
	
	@Override
	public boolean appReflectProcessRecord(AppReflectInfor info) {
		AppCommonPara para = new AppCommonPara(EnumAdaptor.valueOf(info.getDegressAtr().value, DegreeReflectionPubAtr.class),
				EnumAdaptor.valueOf(info.getExecutiontype().value, ExecutionPubType.class),
				EnumAdaptor.valueOf(info.getStateReflection().value, ReflectedStatePubRecord.class),
				EnumAdaptor.valueOf(info.getStateReflectionReal().value, ReflectedStatePubRecord.class)); 
		return recordPub.appReflectProcess(para);
	}

	@Override
	public ReflectedStatesInfo gobackReflectRecord(GobackReflectPara para, boolean isPre) {
		GobackAppPubParameter gobackPra = new GobackAppPubParameter(EnumAdaptor.valueOf(para.getGobackData().getChangeAppGobackAtr().value,
				ChangeAppGobackPubAtr.class), para.getGobackData().getWorkTimeCode(),
				para.getGobackData().getWorkTypeCode(), 
				para.getGobackData().getStartTime1(),
				para.getGobackData().getEndTime1(), 
				para.getGobackData().getStartTime1(),
				para.getGobackData().getEndTime2(),
				EnumAdaptor.valueOf(para.getGobackData().getReflectState().value, ReflectedStatePubRecord.class), 
				EnumAdaptor.valueOf(para.getGobackData().getReasoNotReflect().value, ReasonNotReflectPubRecord.class));
		GobackReflectPubParameter pubPara = new GobackReflectPubParameter(para.getEmployeeId(), 
				para.getDateData(),
				para.isOutResReflectAtr(),
				EnumAdaptor.valueOf(para.getPriorStampAtr().value, PriorStampPubAtr.class),
				EnumAdaptor.valueOf(para.getScheAndRecordSameChangeFlg().value, ScheAndRecordSameChangePubFlg.class),
				EnumAdaptor.valueOf(para.getScheTimeReflectAtr().value, ScheTimeReflectPubAtr.class),
				para.isScheReflectAtr(),
				gobackPra);
		AppReflectPubOutput preGobackReflect;
		if(isPre) {
			preGobackReflect = recordPub.preGobackReflect(pubPara);
		} else {
			preGobackReflect = recordPub.afterGobackReflect(pubPara);
		}
		
		ReflectedStatesInfo preGobackData = new ReflectedStatesInfo(EnumAdaptor.valueOf(preGobackReflect.getReflectedState().value, ReflectedState_New.class), 
				EnumAdaptor.valueOf(preGobackReflect.getReasonNotReflect().value, ReasonNotReflect_New.class));
		return preGobackData;
	}
	
	

}
