package nts.uk.screen.at.app.query.knr.knr002.e;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.record.app.command.knr.knr002.e.WriteToBackupCommand;
import nts.uk.ctx.at.record.app.command.knr.knr002.e.WriteToBackupCommandHandler;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatListRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author dungbn
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｅ：就業情報端末のバックアップ.メニュー別OCD.Ｅ：バックアップを取る
 *
 */
@Stateless
public class GetBackup {
	
	@Inject
	private WriteToBackupCommandHandler writeToBackupCommandHandler;
	
	@Inject
	private TimeRecordSetFormatListRepository repository;
	
	public void handle(String code) {
		
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		EmpInfoTerminalCode empInfoTerCode = new EmpInfoTerminalCode(code);
		
		// 1:get*（契約コード、就業情報端末コード）：タイムレコード設定フォーマットリスト
		Optional<TimeRecordSetFormatList> timeRecordSetFormatList = repository.findSetFormat(empInfoTerCode, contractCode);
		
		if (!timeRecordSetFormatList.isPresent()) {
			throw new BusinessException("Msg_2113");
		}
		
		WriteToBackupCommand command = new WriteToBackupCommand(timeRecordSetFormatList.get(), empInfoTerCode, contractCode);
		
		// 2:登録する（契約コード、就業譲歩端末コード、タイムレコード設定フォーマット）
		writeToBackupCommandHandler.handle(command);
		
	}
}
