package nts.uk.ctx.at.aggregation.app.command.scheduletable.outputsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.aggregation.dom.scheduletable.CopyScheduleTableOutputSettingService;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OutputSettingCode;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OutputSettingName;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSetting;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * <<Command>> 出力項目情報を複製して登録する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール表.APP.出力項目情報を複製して登録する
 * @author quytb
 *
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class CopyScheduleTableOutputSettingCommandHandler extends CommandHandler<ScheduleTableOutputSettingCopyCommand>{
	@Inject
	private ScheduleTableOutputSettingRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<ScheduleTableOutputSettingCopyCommand> context) {
		ScheduleTableOutputSettingCopyCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		Optional<ScheduleTableOutputSetting> copySource =  this.repository.get(companyId, new OutputSettingCode(command.getCopySourceCode()));
		/** 1:複製する(@Require, スケジュール表の出力設定, 出力設定コード, 出力設定名称): AtomTask*/
		CopyScheduleTableOutputSettingImpl require = new CopyScheduleTableOutputSettingImpl(repository) ;

		if(copySource.isPresent()) {
			AtomTask copy = CopyScheduleTableOutputSettingService.copy(require, copySource.get(), 
					 new OutputSettingCode(command.getNewCode()), 
					 new OutputSettingName(command.getNewName()));
			transaction.execute(() -> {
				copy.run();
			});			
		}		
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@AllArgsConstructor
	private static class CopyScheduleTableOutputSettingImpl implements CopyScheduleTableOutputSettingService.Require {		
		private ScheduleTableOutputSettingRepository repository;
		
		@Override
		public boolean isScheduleTableOutputSettingRegistered(OutputSettingCode code) {
			String companyId = AppContexts.user().companyId();
			return repository.exists(companyId, code);
		}

		@Override
		public void insertScheduleTableOutputSetting(ScheduleTableOutputSetting domain) {
			String companyId = AppContexts.user().companyId();
			this.repository.insert(companyId, domain);			
		}		
	}
}
