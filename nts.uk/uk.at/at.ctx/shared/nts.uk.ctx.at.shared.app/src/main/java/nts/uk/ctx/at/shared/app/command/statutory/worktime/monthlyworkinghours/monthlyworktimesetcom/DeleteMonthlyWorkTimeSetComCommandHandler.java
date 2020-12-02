package nts.uk.ctx.at.shared.app.command.statutory.worktime.monthlyworkinghours.monthlyworktimesetcom;

/**
 * 
 * @author sonnlb
 *
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).法定労働時間.法定労働時間（New）.月単位の法定労働時間.APP.会社別月単位労働時間を削除する.会社別月単位労働時間を削除する
 */

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteMonthlyWorkTimeSetComCommandHandler extends CommandHandler<DeleteMonthlyWorkTimeSetComCommand> {
	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteMonthlyWorkTimeSetComCommand> context) {
		
		DeleteMonthlyWorkTimeSetComCommand cmd = context.getCommand();
		// 1. delete (ログイン会社ID,勤務区分,年月期間)
		this.monthlyWorkTimeSetRepo.removeCompany(AppContexts.user().companyId(), cmd.getLaborAttr(), cmd.getYm());
	}

}
