package nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetsha;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 
 * @author sonnlb
 *
 *    UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).法定労働時間.法定労働時間（New）.月単位の法定労働時間.APP.雇用別月単位労働時間を複写する.雇用別月単位労働時間を複写する
 */

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.dom.company.GetThePeriodOfTheYear;
import nts.uk.screen.at.app.command.kmk.kmk004.MonthlyLaborTimeCommand;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CopyMonthlyWorkTimeSetShaCommandHandler extends CommandHandler<CopyMonthlyWorkTimeSetShaCommand> {
	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	@Inject
	private CompanyRepository companyRepo;

	@Inject
	private SaveMonthlyWorkTimeSetShaCommandHandler saveHandler;

	@Override
	protected void handle(CommandHandlerContext<CopyMonthlyWorkTimeSetShaCommand> context) {

		CopyMonthlyWorkTimeSetShaCommand cmd = context.getCommand();

		// 1. 年度の期間を取得(require, 会社ID, 年度)
		GetThePeriodOfTheYearImpl require = new GetThePeriodOfTheYearImpl();
		YearMonthPeriod yearMonths = GetThePeriodOfTheYear.getPeriodOfTheYear(require, AppContexts.user().companyId(),
				cmd.getYear());

		// 2. get(ログイン会社ID,社員ID,勤務区分,年月期間)

		List<MonthlyWorkTimeSetShaCommand> workTimeSetShas = this.monthlyWorkTimeSetRepo
				.findEmployeeByPeriod(AppContexts.user().companyId(), cmd.getCopySourceSid(),
						EnumAdaptor.valueOf(cmd.getLaborAttr(), LaborWorkTypeAttr.class), yearMonths)
				.stream().map(sha -> fromDomainToCommand(sha)).collect(Collectors.toList());

		// 3 .社員別月単位労働時間（List

		cmd.getCopyDestinationSids().forEach(empId -> {
			// ※複写元の社員IDで取得した社員別月単位労働時間の 社員IDに複写先社員IDをセットする
			workTimeSetShas.forEach(sha -> {
				sha.setEmpId(empId);
			});

			SaveMonthlyWorkTimeSetShaCommand command = new SaveMonthlyWorkTimeSetShaCommand(workTimeSetShas);

			this.saveHandler.handle(command);
		});

	}

	private MonthlyWorkTimeSetShaCommand fromDomainToCommand(MonthlyWorkTimeSetSha domain) {
		return new MonthlyWorkTimeSetShaCommand(domain.getEmpId(), domain.getLaborAttr().value, domain.getYm().v(),
				new MonthlyLaborTimeCommand(domain.getLaborTime().getLegalLaborTime().v(),
						domain.getLaborTime().getWithinLaborTime().map(x -> x.v()).orElse(null),
						domain.getLaborTime().getWeekAvgTime().map(x -> x.v()).orElse(null)));
	}

	private class GetThePeriodOfTheYearImpl implements GetThePeriodOfTheYear.Require {

		@Override
		public Optional<Company> getComanyInfoByCid(String cid) {
			return companyRepo.getComanyInfoByCid(cid);
		}

	}

}
