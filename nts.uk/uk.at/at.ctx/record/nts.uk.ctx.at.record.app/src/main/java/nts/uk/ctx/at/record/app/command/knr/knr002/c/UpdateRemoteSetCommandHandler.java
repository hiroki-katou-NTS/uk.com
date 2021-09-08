package nts.uk.ctx.at.record.app.command.knr.knr002.c;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateRemoteSetCommandHandler extends CommandHandler<UpdateRemoteSetCommand> {

	@Inject
	private TimeRecordReqSettingRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateRemoteSetCommand> context) {
		
		String contractCode = AppContexts.user().contractCode();
		UpdateRemoteSetCommand command = context.getCommand();
		
		// 1: 就業情報端末のリクエスト一覧を取得する(ログイン契約コード、就業情報端末コード): Optional<就業情報端末のリクエスト一覧>
		Optional<TimeRecordReqSetting> timeRecordReqSetting = repository.getTimeRecordReqSetting(new EmpInfoTerminalCode(command.getEmpInfoTerCode()),new ContractCode(contractCode));
		
		if (!timeRecordReqSetting.isPresent()) {
			
			// 2: [就業情報端末のリクエスト一覧＝0件]:create(ログイン約コード、就業情報端末コード、リモート設定＝true)
			repository.insert(new EmpInfoTerminalCode(command.getEmpInfoTerCode()),new ContractCode(contractCode));
		} else {
			
			// 3: [就業情報端末のリクエスト一覧＞0件]: set(ログイン契約コード、就業情報端末コード、リモート設定＝true)
			timeRecordReqSetting.get().setRemoteSetting(true);
			repository.updateSetting(timeRecordReqSetting.get());
		}
	}
}
