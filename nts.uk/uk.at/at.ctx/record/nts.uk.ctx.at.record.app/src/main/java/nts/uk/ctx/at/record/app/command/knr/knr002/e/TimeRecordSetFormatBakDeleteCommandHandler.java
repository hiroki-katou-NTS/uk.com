package nts.uk.ctx.at.record.app.command.knr.knr002.e;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatBak;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatBakRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author dungbn
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｅ：バックアップに書き込む.Ｅ：タイムレコード設定フォーマットのバックアップを削除する
 *
 */
@Stateless
public class TimeRecordSetFormatBakDeleteCommandHandler extends CommandHandler<EmpInfoTerminalCode> {

	@Inject
	private TimeRecordSetFormatBakRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<EmpInfoTerminalCode> context) {
		
		EmpInfoTerminalCode empInfoTerminalCode = context.getCommand();
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		
		// 1:get*(契約コード、就業情報端末コード): タイムレコード設定フォーマットのバックアップ
		Optional<TimeRecordSetFormatBak> timeRecordSetFormatBak = repository.get(contractCode, empInfoTerminalCode);
		
		// 2:「タイムレコード設定フォーマットのバックアップ＞0件」:delete(タイムレコード設定フォーマットのバックアップ)
		if (timeRecordSetFormatBak.isPresent()) {
			repository.delete(timeRecordSetFormatBak.get());
		}
	}
}
