package nts.uk.screen.at.app.command.kmk.kmk004.l;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.dom.company.GetThePeriodOfTheYear;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.DeleteMonthlyWorkTimeSetComCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.DeleteMonthlyWorkTimeSetComCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.YearMonthPeriodCommand;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.L：会社別法定労働時間の登録（変形労働）.メニュー別OCD.会社別月単位労働時間（変形労働）を削除する
 * 
 * @author tutt
 *
 */
@Stateless
public class DeleteTransMonthlyWorkTimeSetComCommandHandler
		extends CommandHandler<DeleteTransMonthlyWorkTimeSetComCommand> {

	@Inject
	private CompanyRepository companyRepo;

	@Inject
	private DeleteMonthlyWorkTimeSetComCommandHandler deleteHandler;

	@Override
	public void handle(CommandHandlerContext<DeleteTransMonthlyWorkTimeSetComCommand> context) {

		DeleteTransMonthlyWorkTimeSetComCommand cmd = context.getCommand();

		// 1: 年度の期間を取得(require, 会社ID, 年度)
		GetThePeriodOfTheYearImpl require = new GetThePeriodOfTheYearImpl();
		YearMonthPeriod yearMonths = GetThePeriodOfTheYear.getPeriodOfTheYear(require, AppContexts.user().companyId(),
				cmd.getYear());

		// 2: <call>
		// Input: 
		// 勤務区分 = 1：変形労働
		// 年月期間 = 取得した年月期間
		this.deleteHandler.handle(new DeleteMonthlyWorkTimeSetComCommand(LaborWorkTypeAttr.DEFOR_LABOR.value,
				new YearMonthPeriodCommand(yearMonths.start().v(), yearMonths.end().v())));

	}

	private class GetThePeriodOfTheYearImpl implements GetThePeriodOfTheYear.Require {

		@Override
		public Optional<Company> getComanyInfoByCid(String cid) {
			return companyRepo.getComanyInfoByCid(cid);
		}

	}

}
