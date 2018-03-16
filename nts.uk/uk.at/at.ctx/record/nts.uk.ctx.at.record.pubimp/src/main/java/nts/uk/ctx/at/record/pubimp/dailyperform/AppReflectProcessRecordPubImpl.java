package nts.uk.ctx.at.record.pubimp.dailyperform;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ApplicationReflectOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonCheckParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.DegreeReflectionAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ExecutionType;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReasonNotReflectRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReflectedStateRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ScheAndRecordSameChangeFlg;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback.ChangeAppGobackAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback.GobackAppParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback.GobackReflectParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback.PreGoBackReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback.PriorStampAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback.ScheTimeReflectAtr;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppCommonPara;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppReflectProcessRecordPub;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppReflectPubOutput;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ReasonNotReflectPubRecord;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ReflectedStatePubRecord;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.GobackReflectPubParameter;

@Stateless
public class AppReflectProcessRecordPubImpl implements AppReflectProcessRecordPub{
	@Inject
	private CommonProcessCheckService commonProcess;
	@Inject
	private PreGoBackReflectService preGobackReflect;
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
		GobackAppParameter appPara = new GobackAppParameter(EnumAdaptor.valueOf(para.getGobackData().getChangeAppGobackAtr().value, ChangeAppGobackAtr.class),
				para.getGobackData().getWorkTimeCode(),
				para.getGobackData().getWorkTypeCode(),
				para.getGobackData().getStartTime1(),
				para.getGobackData().getEndTime1(),
				para.getGobackData().getStartTime2(),
				para.getGobackData().getEndTime2(),
				EnumAdaptor.valueOf(para.getGobackData().getReflectState().value, ReflectedStateRecord.class),
				EnumAdaptor.valueOf(para.getGobackData().getReasoNotReflect().value, ReasonNotReflectRecord.class));
		GobackReflectParameter gobackPara = new GobackReflectParameter(para.getEmployeeId(),
				para.getDateData(),
				para.isOutResReflectAtr(),
				EnumAdaptor.valueOf(para.getPriorStampAtr().value, PriorStampAtr.class),
				EnumAdaptor.valueOf(para.getScheAndRecordSameChangeFlg().value, ScheAndRecordSameChangeFlg.class),
				EnumAdaptor.valueOf(para.getScheTimeReflectAtr().value, ScheTimeReflectAtr.class),
				para.isScheReflectAtr(),
				appPara);
		ApplicationReflectOutput reflectInfor = preGobackReflect.gobackReflect(gobackPara);
		AppReflectPubOutput reflectOutput = new AppReflectPubOutput(EnumAdaptor.valueOf(reflectInfor.getReflectedState().value, ReflectedStatePubRecord.class), 
				EnumAdaptor.valueOf(reflectInfor.getReasonNotReflect().value, ReasonNotReflectPubRecord.class));
		return reflectOutput;
	}


}
