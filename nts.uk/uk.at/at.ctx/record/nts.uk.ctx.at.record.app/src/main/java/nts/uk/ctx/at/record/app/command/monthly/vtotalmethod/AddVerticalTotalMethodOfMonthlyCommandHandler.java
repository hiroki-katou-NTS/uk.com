package nts.uk.ctx.at.record.app.command.monthly.vtotalmethod;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.totalrestrainttime.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.totalrestrainttime.CalculationMethodOfConstraintTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.SpecCountNotCalcSubject;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.TADaysCountCondOfMonthlyAggr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.TADaysCountOfMonthlyAggr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.VerticalTotalMethodOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.workrule.specific.LimitControlOfTotalWorkingSet;
import nts.uk.ctx.at.shared.dom.workrule.specific.SpecificWorkRuleRepository;
import nts.uk.ctx.at.shared.dom.workrule.specific.UpperLimitTotalWorkingHour;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddVerticalTotalMethodOfMonthlyCommandHandler extends CommandHandler<AddVerticalTotalMethodOfMonthlyCommand> {

	@Inject
	VerticalTotalMethodOfMonthlyRepository repository;

	@Inject
	SpecificWorkRuleRepository specificWorkRuleRepository;

	@Override
	protected void handle(CommandHandlerContext<AddVerticalTotalMethodOfMonthlyCommand> context) {

		AddVerticalTotalMethodOfMonthlyCommand command = context.getCommand();

		String companyId = AppContexts.user().companyId();

		// ドメインモデル「総労働時間の上限値制御」を取得する
		Optional<CalculateOfTotalConstraintTime> optCalcSetting = specificWorkRuleRepository.findCalcMethodByCid(companyId);
		if (optCalcSetting.isPresent()) {
			CalculateOfTotalConstraintTime data = optCalcSetting.get();
			data.setCalcMethod(EnumAdaptor.valueOf(command.getCalculationMethod(), CalculationMethodOfConstraintTime.class));
			specificWorkRuleRepository.updateCalcMethod(data);
		} else {
			CalculateOfTotalConstraintTime data = CalculateOfTotalConstraintTime.createFromJavaType(companyId, command.getCalculationMethod());
			specificWorkRuleRepository.insertCalcMethod(data);
		}

		// ドメインモデル「総拘束時間の計算」を取得する
		Optional<UpperLimitTotalWorkingHour> optUpperLimit = specificWorkRuleRepository.findUpperLimitWkHourByCid(companyId);
		if (optUpperLimit.isPresent()) {
			UpperLimitTotalWorkingHour data = optUpperLimit.get();
			data.setLimitSet(EnumAdaptor.valueOf(command.getConfig(), LimitControlOfTotalWorkingSet.class));
			specificWorkRuleRepository.updateUpperLimitWkHour(data);
		} else {
			UpperLimitTotalWorkingHour data = UpperLimitTotalWorkingHour.createFromJavaType(companyId, command.getConfig());
			specificWorkRuleRepository.insertUpperLimitWkHour(data);
		}

		// ドメインモデル「時間休暇相殺優先順位」を取得する
		val optVacationOrder = specificWorkRuleRepository.findTimeOffVacationOrderByCid(companyId);
		if (optVacationOrder.isPresent()) {
			specificWorkRuleRepository.updateTimeOffVacationOrder(new CompanyHolidayPriorityOrder(companyId,
																	command.getOffVacationPriorityOrder().order()));
		} else {
			specificWorkRuleRepository.insertTimeOffVacationOrder(new CompanyHolidayPriorityOrder(companyId,
																	command.getOffVacationPriorityOrder().order()));
		}

		// ドメインモデル「月別実績の集計方法」を登録する
		Optional<AggregateMethodOfMonthly> optSetting = repository.findByCid(companyId);
		if (optSetting.isPresent()) {
			AggregateMethodOfMonthly data = optSetting.get();
			data.setTransferAttendanceDays(TADaysCountOfMonthlyAggr.of(EnumAdaptor.valueOf(command.getCountingDay(), TADaysCountCondOfMonthlyAggr.class)));
			data.getSpecTotalCountMonthly().setSpecCount(EnumAdaptor.valueOf(command.getCountingCon(), SpecCountNotCalcSubject.class));
			data.getSpecTotalCountMonthly().setContinuousCount(command.getCountingWorkDay() == 1);
			data.getSpecTotalCountMonthly().setNotWorkCount(command.getCountingNonWorkDay() == 1);
			repository.update(data);

		} else {
			AggregateMethodOfMonthly data = new AggregateMethodOfMonthly(AppContexts.user().companyId());
			data.setTransferAttendanceDays(TADaysCountOfMonthlyAggr.of(EnumAdaptor.valueOf(command.getCountingDay(), TADaysCountCondOfMonthlyAggr.class)));
			data.getSpecTotalCountMonthly().setSpecCount(EnumAdaptor.valueOf(command.getCountingCon(), SpecCountNotCalcSubject.class));
			data.getSpecTotalCountMonthly().setContinuousCount(command.getCountingWorkDay() == 1);
			data.getSpecTotalCountMonthly().setNotWorkCount(command.getCountingNonWorkDay() == 1);
			repository.insert(data);
		}

	}
}
