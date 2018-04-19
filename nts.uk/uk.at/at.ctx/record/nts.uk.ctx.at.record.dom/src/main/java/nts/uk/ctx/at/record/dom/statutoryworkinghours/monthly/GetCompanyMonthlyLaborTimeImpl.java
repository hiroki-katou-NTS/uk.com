package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

@Stateless
public class GetCompanyMonthlyLaborTimeImpl implements GetCompanyMonthlyLaborTime{

	@Inject
	private ComNormalSettingRepository comNormalSettingRepository;
	
	@Inject
	private ComDeforLaborSettingRepository comDeforLaborSettingRepository;
	
	
	/**
	 * 会社別設定の取得
	 * @param workingSystem
	 * @param comRegularLaborTime
	 * @param comTransLaborTime
	 * @return
	 */
	@Override
	public List<MonthlyUnit> getComWorkingTimeSetting(String companyId,YearMonth yearMonth,WorkingSystem workingSystem) {
		if(workingSystem.isRegularWork()) {//通常勤務　の場合
			Optional<ComNormalSetting> ComNormalSetting = comNormalSettingRepository.find(companyId,yearMonth.year());
			if(ComNormalSetting.isPresent()) {
				return ComNormalSetting.get().getStatutorySetting();
			}
		}else if(workingSystem.isVariableWorkingTimeWork()) {//変形労働勤務　の場合
			Optional<ComDeforLaborSetting> ComDeforLaborSetting = comDeforLaborSettingRepository.find(companyId,yearMonth.year());
			if(ComDeforLaborSetting.isPresent()) {
				return ComDeforLaborSetting.get().getStatutorySetting();
			}
		}
		return new ArrayList<>();
	}
	
	
}
