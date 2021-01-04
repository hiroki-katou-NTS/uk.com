package nts.uk.screen.at.app.command.kmk.kmk004.o;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.dom.company.GetThePeriodOfTheYear;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.YearMonthPeriodCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha.DeleteMonthlyWorkTimeSetShaCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha.DeleteMonthlyWorkTimeSetShaCommandHandler;
import nts.uk.screen.at.app.query.kmk004.common.EmployeeIdDto;
import nts.uk.screen.at.app.query.kmk004.common.EmployeeList;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tutt
 *
 */
@Stateless
public class DeleteTransMonthlyWorkTimeSetShaCommandHandler
		extends CommandHandlerWithResult<DeleteTransMonthlyWorkTimeSetShaCommand, List<EmployeeIdDto>> {

	@Inject
	private DeleteMonthlyWorkTimeSetShaCommandHandler deleteHandler;

	@Inject
	private CompanyRepository companyRepo;

	@Inject
	private EmployeeList employeeList;

	@Override
	public List<EmployeeIdDto> handle(CommandHandlerContext<DeleteTransMonthlyWorkTimeSetShaCommand> context) {
		DeleteTransMonthlyWorkTimeSetShaCommand cmd = context.getCommand();

		// 1: 年度の期間を取得(require, 会社ID, 年度)
		// Input: 年度 ← 選択中の年度
		GetThePeriodOfTheYearImpl require = new GetThePeriodOfTheYearImpl();
		YearMonthPeriod yearMonths = GetThePeriodOfTheYear.getPeriodOfTheYear(require, AppContexts.user().companyId(),
				cmd.getYear());

		// 2: <call>
		// 社員別月単位労働時間を削除する
		// Input:
		// 社員ID = 選択中の社員ID
		// 勤務区分 = 1：変形労働
		// 年月期間 = 取得した年月期間
		this.deleteHandler
				.handle(new DeleteMonthlyWorkTimeSetShaCommand(cmd.getEmployeeId(), LaborWorkTypeAttr.DEFOR_LABOR.value,
						new YearMonthPeriodCommand(yearMonths.start().v(), yearMonths.end().v())));

		// 3: <call>
		// 社員リストを表示する
		// Input:
		// 社員データ← 社員リストに表示中の社員データ
		// 勤務区分＝1：変形労働
		return this.employeeList.get(LaborWorkTypeAttr.DEFOR_LABOR);
	}

	private class GetThePeriodOfTheYearImpl implements GetThePeriodOfTheYear.Require {

		@Override
		public Optional<Company> getComanyInfoByCid(String cid) {
			return companyRepo.getComanyInfoByCid(cid);
		}

	}

}
