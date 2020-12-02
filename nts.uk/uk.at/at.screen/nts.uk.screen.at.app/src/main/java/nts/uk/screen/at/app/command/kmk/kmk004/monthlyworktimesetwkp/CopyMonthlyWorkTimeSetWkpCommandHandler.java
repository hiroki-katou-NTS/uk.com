package nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetwkp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @author sonnlb
 *
 *    UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).法定労働時間.法定労働時間（New）.月単位の法定労働時間.APP.職場別月単位労働時間を複写する.職場別月単位労働時間を複写する
 */

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.LaborTimeMonthlyDto;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.dom.company.GetThePeriodOfTheYear;
import nts.uk.screen.at.app.command.kmk.kmk004.MonthlyLaborTimeCommand;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CopyMonthlyWorkTimeSetWkpCommandHandler extends CommandHandler<CopyMonthlyWorkTimeSetWkpCommand> {
	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	@Inject
	private CompanyRepository companyRepo;

	@Inject
	private SaveMonthlyWorkTimeSetWkpCommandHandler saveHandler;

	@Override
	protected void handle(CommandHandlerContext<CopyMonthlyWorkTimeSetWkpCommand> context) {

		CopyMonthlyWorkTimeSetWkpCommand cmd = context.getCommand();

		// 1. 年度の期間を取得(require, 会社ID, 年度)
		GetThePeriodOfTheYearImpl require = new GetThePeriodOfTheYearImpl();
		YearMonthPeriod yearMonths = GetThePeriodOfTheYear.getPeriodOfTheYear(require, AppContexts.user().companyId(),
				cmd.getYear());

		// 2. get(ログイン会社ID,職場ID,勤務区分,年月期間)

		List<MonthlyWorkTimeSetWkp> monthlyWorkTimeSetWkps = this.monthlyWorkTimeSetRepo.findWorkplaceByPeriod(
				AppContexts.user().companyId(), cmd.getCopySourceWorkplaceId(),
				EnumAdaptor.valueOf(cmd.getLaborAttr(), LaborWorkTypeAttr.class), yearMonths);

		// 3職場別月単位労働時間（List）
		// ※複写元の職場IDで取得した職場別月単位労働時間の 職場IDに複写先職場IDをセットする
		List<MonthlyWorkTimeSetWkpCommand> workTimeSetWkps = new ArrayList<MonthlyWorkTimeSetWkpCommand>();

		monthlyWorkTimeSetWkps.forEach(wkp -> {
			workTimeSetWkps.add(
					new MonthlyWorkTimeSetWkpCommand(wkp.getWorkplaceId(), wkp.getLaborAttr().value, wkp.getYm().v(),
							new MonthlyLaborTimeCommand(wkp.getLaborTime().getLegalLaborTime().v(),
									wkp.getLaborTime().getWithinLaborTime().map(x -> x.v()).orElse(null),
									wkp.getLaborTime().getWeekAvgTime().map(x -> x.v()).orElse(null))));
		});

		cmd.getCopyDestinationWorkplaceIds().forEach(wkpId -> {

			workTimeSetWkps.forEach(wkp -> {
				wkp.setWorkplaceId(wkpId);
			});

			SaveMonthlyWorkTimeSetWkpCommand command = new SaveMonthlyWorkTimeSetWkpCommand(workTimeSetWkps);

			this.saveHandler.handle(command);
		});

	}

	private class GetThePeriodOfTheYearImpl implements GetThePeriodOfTheYear.Require {

		@Override
		public Optional<Company> getComanyInfoByCid(String cid) {
			return companyRepo.getComanyInfoByCid(cid);
		}

	}

}
