package nts.uk.screen.at.app.command.kmk.kmk004.n;

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
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.DeleteMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.DeleteMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentCodeDto;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentList;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.N：雇用別法定労働時間の登録（変形労働）.メニュー別OCD.雇用別月単位労働時間（変形労働）を削除する
 * 
 * @author tutt
 *
 */
@Stateless
public class DeleteTransMonthlyWorkTimeSetEmpCommandHandler
		extends CommandHandlerWithResult<DeleteTransMonthlyWorkTimeSetEmpCommand, List<EmploymentCodeDto>> {

	@Inject
	private DeleteMonthlyWorkTimeSetEmpCommandHandler deleteHandler;

	@Inject
	private CompanyRepository companyRepo;

	@Inject
	private EmploymentList employmentList;

	@Override
	public List<EmploymentCodeDto> handle(CommandHandlerContext<DeleteTransMonthlyWorkTimeSetEmpCommand> context) {
		DeleteTransMonthlyWorkTimeSetEmpCommand cmd = context.getCommand();

		// 1: 年度の期間を取得(require, 会社ID, 年度)
		// Input: 年度 ← 選択中の年度
		GetThePeriodOfTheYearImpl require = new GetThePeriodOfTheYearImpl();
		YearMonthPeriod yearMonths = GetThePeriodOfTheYear.getPeriodOfTheYear(require, AppContexts.user().companyId(),
				cmd.getYear());

		// 2: <call>
		// 雇用別月単位労働時間を削除する
		// Input:
		// 雇用コード = 選択中の雇用コード
		// 勤務区分 = 1：変形労働
		// 年月期間 = 取得した年月期間
		this.deleteHandler.handle(
				new DeleteMonthlyWorkTimeSetEmpCommand(cmd.getEmploymentCode(), LaborWorkTypeAttr.DEFOR_LABOR.value,
						new YearMonthPeriodCommand(yearMonths.start().v(), yearMonths.end().v())));

		// 3: <call>
		// 雇用リストを表示する
		// Input: 勤務区分＝1：変形労働
		return this.employmentList.get(LaborWorkTypeAttr.DEFOR_LABOR);
	}

	private class GetThePeriodOfTheYearImpl implements GetThePeriodOfTheYear.Require {

		@Override
		public Optional<Company> getComanyInfoByCid(String cid) {
			return companyRepo.getComanyInfoByCid(cid);
		}

	}

}
