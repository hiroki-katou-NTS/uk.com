package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ApplicationReflectOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonReflectParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReasonNotReflectRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReflectedStateRecord;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;

@Stateless
public class PreWorkchangeReflectServiceImpl implements PreWorkchangeReflectService{
	@Inject
	private CommonProcessCheckService commonService;
	@Inject
	private ScheWorkUpdateService workTimeUpdate;
	@Override
	public ApplicationReflectOutput workchangeReflect(CommonReflectParameter workchangePara, boolean isPre) {
		try {
			//予定勤種就時の反映
			commonService.reflectScheWorkTimeWorkType(workchangePara, isPre);
			//勤種・就時の反映
			ReflectParameter reflectPara = new ReflectParameter(workchangePara.getEmployeeId(),
					workchangePara.getBaseDate(),
					workchangePara.getWorkTimeCode(),
					workchangePara.getWorkTypeCode());
			workTimeUpdate.updateWorkTimeType(reflectPara, isPre);
			return new ApplicationReflectOutput(ReflectedStateRecord.REFLECTED, ReasonNotReflectRecord.ACTUAL_CONFIRMED);	
		}catch (Exception e) {
			return new ApplicationReflectOutput(EnumAdaptor.valueOf(workchangePara.getReflectState().value, ReflectedStateRecord.class),
					workchangePara.getReasoNotReflect() == null ? null : EnumAdaptor.valueOf(workchangePara.getReasoNotReflect().value, ReasonNotReflectRecord.class));
		}
	}

}
