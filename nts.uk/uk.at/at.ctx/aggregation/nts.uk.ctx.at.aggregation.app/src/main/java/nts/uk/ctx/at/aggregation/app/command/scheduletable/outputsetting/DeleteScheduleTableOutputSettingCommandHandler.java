package nts.uk.ctx.at.aggregation.app.command.scheduletable.outputsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OutputSettingCode;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * <<Command>> 出力項目情報を削除する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール表.APP.出力項目情報を削除する.出力項目情報を削除する
 * @author quytb
 *
 */
@Stateless
public class DeleteScheduleTableOutputSettingCommandHandler extends CommandHandler<ScheduleTableOutputSettingDeleteCommand> {
	@Inject
	private ScheduleTableOutputSettingRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<ScheduleTableOutputSettingDeleteCommand> context) {
		String companyId = AppContexts.user().companyId();
		ScheduleTableOutputSettingDeleteCommand command = context.getCommand();
		
		/** 1:delete(ログイン会社ID,出力項目コード)*/
		this.repository.delete(companyId, new OutputSettingCode(command.getCode()));
	}	

}
