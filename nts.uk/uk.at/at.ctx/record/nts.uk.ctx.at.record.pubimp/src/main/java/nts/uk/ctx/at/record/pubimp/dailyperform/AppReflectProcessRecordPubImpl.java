package nts.uk.ctx.at.record.pubimp.dailyperform;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonCheckParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppReflectProcessRecordPub;

@Stateless
public class AppReflectProcessRecordPubImpl implements AppReflectProcessRecordPub{
	private CommonProcessCheckService commonProcess;

	@Override
	public boolean appReflectProcess(CommonCheckParameter para) {
		return commonProcess.commonProcessCheck(para);
	}

}
