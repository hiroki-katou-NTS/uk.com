package nts.uk.ctx.at.aggregation.app.command.scheduletable.outputsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OutputSettingCode;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSetting;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * <<Command>> 出力項目情報を変更する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール表.APP.出力項目情報を変更する.出力項目情報を変更する
 * @author quytb
 *
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class UpdateScheduleTableOutputSettingCommandHandler extends CommandHandler<ScheduleTableOutputSettingSaveCommand>{
	@Inject
	private ScheduleTableOutputSettingRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<ScheduleTableOutputSettingSaveCommand> context) {
		ScheduleTableOutputSettingSaveCommand  command = context.getCommand();
		String companyId = AppContexts.user().companyId();	
		
		/** 1:get(ログイン会社ID,出力設定コード):Optional<スケジュール表の出力設定>*/
		Optional<ScheduleTableOutputSetting> optional =  repository.get(companyId, new OutputSettingCode(command.getCode()));
		
		if (!optional.isPresent()) {
			return;
		}
		
		/** 2: set() */	
		ScheduleTableOutputSetting domain =  ScheduleTableOutputSettingSaveCommand.toDomain(command);		
		
		/** 3: update() */	
		this.repository.update(companyId, domain);
	}

}
