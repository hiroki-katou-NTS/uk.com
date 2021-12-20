package nts.uk.ctx.at.record.app.find.monthly.vtotalmethod;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.record.app.find.workrule.specific.TimeOffVacationPriorityOrderDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.totalrestrainttime.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.VerticalTotalMethodOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.workrule.specific.SpecificWorkRuleRepository;
import nts.uk.ctx.at.shared.dom.workrule.specific.UpperLimitTotalWorkingHour;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class VerticalTotalMethodOfMonthlyFinder.
 *
 * @author HoangNDH
 */
@Stateless
public class VerticalTotalMethodOfMonthlyFinder {
	
	/** The repository. */
	@Inject
	VerticalTotalMethodOfMonthlyRepository repository;

	/** The repository. */
	@Inject
	SpecificWorkRuleRepository specificWorkRuleRepository;
	
	/**
	 * Find setting.
	 *
	 * @return the vertical total method of monthly
	 */
	public VerticalTotalMethodOfMonthlyDto findSetting () {
		String companyId = AppContexts.user().companyId();

		Optional<AggregateMethodOfMonthly> optSetting = repository.findByCid(companyId);
		
		if (optSetting.isPresent()) {
			AggregateMethodOfMonthly setting = optSetting.get();
			VerticalTotalMethodOfMonthlyDto dto = new VerticalTotalMethodOfMonthlyDto();
			dto.setAttendanceItemCountingMethod(setting.getTransferAttendanceDays().getTADaysCountCondition().value);
			return dto;
		}
		else {
			return null;
		}
	}

	/**
	 * Start Screen KMK013J Ver 27
	 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK013_計算設定(Calculation setting).KMK013_計算設定（New）.J：実績の計算・集計　詳細設定.アルゴリズム.起動処理.起動処理
	 * @return VerticalTotalMethodOfMonDto
	 */
	public VerticalTotalMethodOfMonDto init() {
		String companyId = AppContexts.user().companyId();

		VerticalTotalMethodOfMonDto dto = new VerticalTotalMethodOfMonDto();

		// ドメインモデル「総労働時間の上限値制御」を取得する
		Optional<UpperLimitTotalWorkingHour> optUpperLimit = specificWorkRuleRepository.findUpperLimitWkHourByCid(companyId);
		if (optUpperLimit.isPresent()) {
			UpperLimitTotalWorkingHour upperLimit = optUpperLimit.get();
			dto.setConfig(upperLimit.getLimitSet().value);
		}

		// ドメインモデル「総拘束時間の計算」を取得する
		Optional<CalculateOfTotalConstraintTime> optCalcSetting = specificWorkRuleRepository.findCalcMethodByCid(companyId);
		if (optCalcSetting.isPresent()) {
			CalculateOfTotalConstraintTime calcSetting = optCalcSetting.get();
			dto.setCalculationMethod(calcSetting.getCalcMethod().value);
		}

		// ドメインモデル「時間休暇相殺優先順位」を取得する
		val orders = specificWorkRuleRepository.findTimeOffVacationOrderByCid(companyId)
				.orElseGet(() -> new CompanyHolidayPriorityOrder(companyId));
		dto.setOffVacationPriorityOrder(TimeOffVacationPriorityOrderDto.from(orders.getHolidayPriorityOrders()));

		// ドメインモデル「月別実績の集計方法」を登録する
		Optional<AggregateMethodOfMonthly> optSetting = repository.findByCid(companyId);
		if (optSetting.isPresent()) {
			dto.setCountingDay(optSetting.get().getTransferAttendanceDays().getTADaysCountCondition().value);
			dto.setCountingCon(optSetting.get().getSpecTotalCountMonthly().getSpecCount().value);
			dto.setCountingWorkDay(optSetting.get().getSpecTotalCountMonthly().isContinuousCount() ? 1 : 0);
			dto.setCountingNonWorkDay(optSetting.get().getSpecTotalCountMonthly().isNotWorkCount() ? 1 : 0);
		}

		return dto;
	}
}
