package nts.uk.ctx.at.aggregation.app.command.scheduletable.outputsetting;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OutputSettingCode;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSetting;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * <<Command>> 出力項目情報を新規に登録する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール表.APP.出力項目情報を新規に登録する.出力項目情報を新規に登録する
 * @author quytb
 *
 */

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class RegisterScheduleTableOutputSettingCommandHandler extends CommandHandler<ScheduleTableOutputSettingSaveCommand> {
	@Inject
	private ScheduleTableOutputSettingRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<ScheduleTableOutputSettingSaveCommand> context) {
		ScheduleTableOutputSettingSaveCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
//		AppContexts.user().roles().
		
		/**1: exist(ログイン会社ID, 出力設定コード)*/
		Boolean exist = repository.exists(companyId, new  OutputSettingCode(command.getCode()));
		/** 2:[(会社ID、出力設定コード).isPresent]: <修正>()*/
		if(exist) {
			throw new BusinessException("Msg_3");
		} else {
			/** 3:create(コード, 名称, 出力項目, 職場計カテゴリ一覧, 個人計カテゴリ): スケジュール表の出力設定*/
			ScheduleTableOutputSetting domain =  ScheduleTableOutputSettingSaveCommand.toDomain(command);
			
			/** 4:persist() */		
			this.repository.insert(companyId, domain);
		}
	}
}
