package nts.uk.screen.at.app.command.kmk.kmk004.i;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.DeleteMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.DeleteMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentList;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.I：雇用別法定労働時間の登録（フレックス勤務）.メニュー別OCD.雇用別月単位労働時間（フレックス勤務）を削除する
 */
@Stateless
public class DeleteFlexMonthlyWorkTimeSetEmpCommandHandler
		extends CommandHandlerWithResult<DeleteFlexMonthlyWorkTimeSetEmpCommand, List<String>> {

	@Inject
	private DeleteMonthlyWorkTimeSetEmpCommandHandler deleteHandler;

	@Inject
	private CompanyRepository companyRepo;

	@Inject
	private EmploymentList employmentList;

	@Override
	protected List<String> handle(CommandHandlerContext<DeleteFlexMonthlyWorkTimeSetEmpCommand> context) {

		DeleteFlexMonthlyWorkTimeSetEmpCommand cmd = context.getCommand();
		// 年度の期間を取得(require, 会社ID, 年度)

		GetThePeriodOfTheYearImpl require = new GetThePeriodOfTheYearImpl();
		YearMonthPeriod yearMonths = GetThePeriodOfTheYear.getPeriodOfTheYear(require, AppContexts.user().companyId(),
				cmd.getYear());
		// 雇用別月単位労働時間を削除する
		this.deleteHandler
				.handle(new DeleteMonthlyWorkTimeSetEmpCommand(cmd.getEmploymentCode(), LaborWorkTypeAttr.FLEX.value,
						new YearMonthPeriodCommand(yearMonths.start().v(), yearMonths.end().v())));
		// 雇用リストを表示する
		return this.employmentList.get(LaborWorkTypeAttr.FLEX).stream().map(x-> x.employmentCode).collect(Collectors.toList());

	}

	private class GetThePeriodOfTheYearImpl implements GetThePeriodOfTheYear.Require {

		@Override
		public Optional<Company> getComanyInfoByCid(String cid) {
			return companyRepo.getComanyInfoByCid(cid);
		}

	}

}
