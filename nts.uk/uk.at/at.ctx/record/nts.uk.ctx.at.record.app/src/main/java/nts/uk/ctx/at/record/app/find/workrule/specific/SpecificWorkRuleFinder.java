package nts.uk.ctx.at.record.app.find.workrule.specific;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.totalrestrainttime.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.shared.dom.workrule.specific.SpecificWorkRuleRepository;
import nts.uk.ctx.at.shared.dom.workrule.specific.TimeOffVacationPriorityOrder;
import nts.uk.ctx.at.shared.dom.workrule.specific.UpperLimitTotalWorkingHour;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SpecificWorkRuleFinder.
 */
@Stateless
public class SpecificWorkRuleFinder {
	
	/** The repository. */
	@Inject
	SpecificWorkRuleRepository repository;
	
	/**
	 * Find specific work rule.
	 *
	 * @return the specific work rule dto
	 */
	public SpecificWorkRuleDto findSpecificWorkRule() {
		String companyId = AppContexts.user().companyId();
		
		SpecificWorkRuleDto dto = new SpecificWorkRuleDto();
		
		// ドメインモデル「総労働時間の上限値制御」を取得する 
		Optional<CalculateOfTotalConstraintTime> optCalcSetting = repository.findCalcMethodByCid(companyId);
		if (optCalcSetting.isPresent()) {
			CalculateOfTotalConstraintTime calcSetting = optCalcSetting.get();
			dto.setConstraintCalcMethod(calcSetting.getCalcMethod().value);
		}
		
		// ドメインモデル「総拘束時間の計算」を取得する
		Optional<UpperLimitTotalWorkingHour> optUpperLimit = repository.findUpperLimitWkHourByCid(companyId);
		if (optUpperLimit.isPresent()) {
			UpperLimitTotalWorkingHour upperLimit = optUpperLimit.get();
			dto.setUpperLimitSet(upperLimit.getLimitSet().value);
		}
		
		// ドメインモデル「時間休暇相殺優先順位」を取得する
		Optional<TimeOffVacationPriorityOrder> optVacationOrder = repository.findTimeOffVacationOrderByCid(companyId);
		if (optVacationOrder.isPresent()) {
			TimeOffVacationPriorityOrder vacationOrder = optVacationOrder.get();
			dto.setSubstituteHoliday(vacationOrder.getSubstituteHoliday());
			dto.setSixtyHourVacation(vacationOrder.getSixtyHourVacation());
			dto.setSpecialHoliday(vacationOrder.getSpecialHoliday());
			dto.setAnnualHoliday(vacationOrder.getAnnualHoliday());
		}
		
		return dto;
	}
}
