package nts.uk.screen.at.app.command.kmk.kmk004.h;

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
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.DeleteMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.DeleteMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.query.kmk004.common.WorkplaceList;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.H：職場別法定労働時間の登録（フレックス勤務）.メニュー別OCD.職場別月単位労働時間（フレックス勤務）を削除する
 */
@Stateless
public class DeleteFlexMonthlyWorkTimeSetWkpCommandHandler
		extends CommandHandlerWithResult<DeleteFlexMonthlyWorkTimeSetWkpCommand, List<String>> {

	@Inject
	private CompanyRepository companyRepo;

	@Inject
	private DeleteMonthlyWorkTimeSetWkpCommandHandler deleteHandler;

	@Inject
	private WorkplaceList workplaceList;

	@Override
	protected List<String> handle(CommandHandlerContext<DeleteFlexMonthlyWorkTimeSetWkpCommand> context) {

		DeleteFlexMonthlyWorkTimeSetWkpCommand cmd = context.getCommand();
		// 1. 年度の期間を取得(require, 会社ID, 年度)
		GetThePeriodOfTheYearImpl require = new GetThePeriodOfTheYearImpl();
		YearMonthPeriod yearMonths = GetThePeriodOfTheYear.getPeriodOfTheYear(require, AppContexts.user().companyId(),
				cmd.getYear());
		// input：
		// 職場ID = 選択中の職場ID
		// 勤務区分 = 2：フレックス勤務
		// 年月期間 = 取得した年月期間
		this.deleteHandler
				.handle(new DeleteMonthlyWorkTimeSetWkpCommand(cmd.getWorkplaceId(), LaborWorkTypeAttr.FLEX.value,
						new YearMonthPeriodCommand(yearMonths.start().v(), yearMonths.end().v())));
		// 職場リストを表示する
		return this.workplaceList.get(LaborWorkTypeAttr.FLEX).stream().map(x -> x.workplaceId)
				.collect(Collectors.toList());

	}

	private class GetThePeriodOfTheYearImpl implements GetThePeriodOfTheYear.Require {

		@Override
		public Optional<Company> getComanyInfoByCid(String cid) {
			return companyRepo.getComanyInfoByCid(cid);
		}

	}

}
