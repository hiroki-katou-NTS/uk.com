package nts.uk.screen.at.app.command.kmk.kmk004.g;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.dom.company.GetThePeriodOfTheYear;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.DeleteMonthlyWorkTimeSetComCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.DeleteMonthlyWorkTimeSetComCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.YearMonthPeriodCommand;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.G：会社別法定労働時間の登録（フレックス勤務）.メニュー別OCD.会社別月単位労働時間（フレックス勤務）を削除する
 */
@Stateless
public class DeleteFlexMonthlyWorkingHoursByComCommandHandler
		extends CommandHandler<Integer> {

	@Inject
	private CompanyRepository companyRepo;
	@Inject
	private DeleteMonthlyWorkTimeSetComCommandHandler deleteHandler;

	@Override
	protected void handle(CommandHandlerContext<Integer> context) {

		// 1. 年度の期間を取得(require, 会社ID, 年度)
		GetThePeriodOfTheYearImpl require = new GetThePeriodOfTheYearImpl();
		YearMonthPeriod yearMonths = GetThePeriodOfTheYear.getPeriodOfTheYear(require, AppContexts.user().companyId(),
				 context.getCommand());

		// 勤務区分 = 2：フレックス勤務
		// 年月期間 = 取得した年月期間
		this.deleteHandler.handle(new DeleteMonthlyWorkTimeSetComCommand(2,
				new YearMonthPeriodCommand(yearMonths.start().v(), yearMonths.end().v())));

	}

	private class GetThePeriodOfTheYearImpl implements GetThePeriodOfTheYear.Require {

		@Override
		public Optional<Company> getComanyInfoByCid(String cid) {
			return companyRepo.getComanyInfoByCid(cid);
		}

	}

}
