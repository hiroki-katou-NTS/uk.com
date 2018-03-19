package nts.uk.ctx.at.request.ac.record.dailyperform;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppCommonPara;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppReflectProcessRecordPub;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.DegreeReflectionPubAtr;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ExecutionPubType;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ReflectedStatePubRecord;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppReflectInfor;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppReflectProcessRecord;

@Stateless
public class AppReflectProcessRecordImpl implements AppReflectProcessRecord {

	private AppReflectProcessRecordPub recordPub;
	
	@Override
	public boolean appReflectProcessRecord(AppReflectInfor info) {
		AppCommonPara para = new AppCommonPara(EnumAdaptor.valueOf(info.getDegressAtr().value, DegreeReflectionPubAtr.class),
				EnumAdaptor.valueOf(info.getExecutiontype().value, ExecutionPubType.class),
				EnumAdaptor.valueOf(info.getStateReflection().value, ReflectedStatePubRecord.class),
				EnumAdaptor.valueOf(info.getStateReflectionReal().value, ReflectedStatePubRecord.class)); 
		return recordPub.appReflectProcess(para);
	}

}
