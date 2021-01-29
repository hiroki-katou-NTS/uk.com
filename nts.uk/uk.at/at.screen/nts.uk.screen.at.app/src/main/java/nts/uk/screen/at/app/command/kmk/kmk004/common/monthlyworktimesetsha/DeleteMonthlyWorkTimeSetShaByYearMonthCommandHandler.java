package nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 社員別月単位労働時間を年月単位で削除する
 * @author chungnt
 *
 */

@Stateless
public class DeleteMonthlyWorkTimeSetShaByYearMonthCommandHandler extends CommandHandler<DeleteMonthlyWorkTimeSetShaByYearMonthCommand> {

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteMonthlyWorkTimeSetShaByYearMonthCommand> context) {
		DeleteMonthlyWorkTimeSetShaByYearMonthCommand cmd = context.getCommand();
		// 1. delete (ログイン会社ID,社員ID,勤務区分,年月期間)
		this.monthlyWorkTimeSetRepo.removeEmployeeByYearMonth(AppContexts.user().companyId(),
				cmd.getEmpId(),
				cmd.getLaborAttr(),
				cmd.getYearMonth());	
	}
}
