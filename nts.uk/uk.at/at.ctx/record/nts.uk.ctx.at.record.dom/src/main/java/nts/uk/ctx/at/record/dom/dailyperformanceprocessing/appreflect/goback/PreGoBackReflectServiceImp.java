package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
@Stateless
public class PreGoBackReflectServiceImp implements PreGoBackReflectService {
	@Inject
	private WorkTimeTypeScheReflect timeTypeSche;
	@Inject
	private ScheTimeReflect scheTimeReflect;
	@Inject
	private AfterScheTimeReflect afterScheTime;
	@Inject
	private CommonProcessCheckService commonService;
	@Inject
	private PreOvertimeReflectService preOvertime;
	@Override
	public boolean gobackReflect(GobackReflectParameter para, boolean isPre) {
		try {
			List<IntegrationOfDaily> lstDaily = this.getByGoBack(para, isPre);
			commonService.updateDailyAfterReflect(lstDaily);
			return true;
		} catch(Exception ex) {
			return false;
		}
	}
	@Override
	public List<IntegrationOfDaily> getByGoBack(GobackReflectParameter para, boolean isPre) {
		IntegrationOfDaily dailyInfor = preOvertime.calculateForAppReflect(para.getEmployeeId(), para.getDateData());
		//予定勤種・就時の反映
		boolean chkTimeTypeSche = timeTypeSche.reflectScheWorkTimeType(para, dailyInfor);
		if(isPre) {
			//予定時刻の反映
			scheTimeReflect.reflectScheTime(para, chkTimeTypeSche, dailyInfor);
		} else {
			//予定時刻の反映
			afterScheTime.reflectScheTime(para, chkTimeTypeSche, dailyInfor);
		}
		//勤種・就時の反映
		boolean isRecord = timeTypeSche.reflectRecordWorktimetype(para, dailyInfor);
		//時刻の反映
		scheTimeReflect.reflectTime(para, isRecord, dailyInfor);			
		List<IntegrationOfDaily> lstDaily = commonService.lstIntegrationOfDaily(dailyInfor, para.getEmployeeId(), para.getDateData(), false);
		return lstDaily;
	}
}
