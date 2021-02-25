package nts.uk.ctx.at.record.app.command.knr.knr002.e;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author dungbn
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｅ：バックアップに書き込む
 *
 */
@Stateless
public class WriteToBackupCommandHandler extends CommandHandler<WriteToBackupCommand> {

	@Inject
	private TimeRecordSetFormatBakDeleteCommandHandler timeRecordSetFormatBakDeleteCommandHandler;
	
	@Inject
	private TimeRecordSetFormatBakRegisterCommandHandler timeRecordSetFormatBakRegisterCommandHandler;
	
	@Override
	protected void handle(CommandHandlerContext<WriteToBackupCommand> context) {
		
		WriteToBackupCommand command = context.getCommand();
		
		// 1: 削除する（契約コード、就業情報端末コード）
		timeRecordSetFormatBakDeleteCommandHandler.handle(command.getEmpInfoTerCode());
		
		TimeRecordSetFormatBakRegisterCommand timeRecordSetFormatBakRegisterCommand = new TimeRecordSetFormatBakRegisterCommand(command.getContractCode(),
																																command.getEmpInfoTerCode(),
																																command.getTimeRecordSetFormatList());
		
		// 2:バックアップに登録する（契約コード、就業情報端末コード、タイムレコード設定フォーマットリスト）
		timeRecordSetFormatBakRegisterCommandHandler.handle(timeRecordSetFormatBakRegisterCommand);
	}

}
