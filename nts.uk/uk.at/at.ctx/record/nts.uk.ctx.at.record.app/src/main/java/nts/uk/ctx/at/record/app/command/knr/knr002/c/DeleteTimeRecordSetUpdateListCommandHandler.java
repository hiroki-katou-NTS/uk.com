package nts.uk.ctx.at.record.app.command.knr.knr002.c;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetUpdateListRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author dungbn
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｃ：タイムレコード設定更新リストを削除する.Ｃ：タイムレコード設定更新リストを削除する
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteTimeRecordSetUpdateListCommandHandler extends CommandHandler<List<TimeRecordSetUpdateList>>{

	@Inject
	private TimeRecordSetUpdateListRepository timeRecordSetUpdateListRepository;
	
	@Override
	protected void handle(CommandHandlerContext<List<TimeRecordSetUpdateList>> context) {
		
		// 1: get*(契約コード、就業情報端末コード<List>): タイムレコード設定更新リスト<List>
		List<TimeRecordSetUpdateList> listTimeRecordSetUpdateList = context.getCommand();
		
		// 2: タイムレコード設定更新リスト<List>＞0件: delete(契約コード、タイムレコード設定更新リスト<List>)
		if (!listTimeRecordSetUpdateList.isEmpty()) {
			timeRecordSetUpdateListRepository.delete(listTimeRecordSetUpdateList);
		}
	}
}
