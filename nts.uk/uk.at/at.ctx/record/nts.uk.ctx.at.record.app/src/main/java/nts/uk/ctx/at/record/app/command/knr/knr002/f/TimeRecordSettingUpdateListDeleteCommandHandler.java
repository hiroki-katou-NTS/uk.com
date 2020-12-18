package nts.uk.ctx.at.record.app.command.knr.knr002.f;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetUpdateListRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｆ：タイムレコード設定更新に登録する.Ｆ：タイムレコード設定更新リストの削除
 * @author xuannt
 *
 */
@Stateless
public class TimeRecordSettingUpdateListDeleteCommandHandler extends CommandHandler<TimeRecordSettingUpdateListDeleteCommand>{
	//	タイムレコード設定更新リストRepository.[4]delete(List<タイムレコード設定更新リスト>)
	@Inject
	TimeRecordSetUpdateListRepository timeRecordSetUpdateListRepository;

	@Override
	protected void handle(CommandHandlerContext<TimeRecordSettingUpdateListDeleteCommand> context) {
		String contractCode = AppContexts.user().contractCode();
		TimeRecordSettingUpdateListDeleteCommand command = context.getCommand();
		//	1. get*(契約コード、就業情報端末コード<List>): タイムレコード設定更新リスト
		List<TimeRecordSetUpdateList> timeRecordSetUpdate = this.timeRecordSetUpdateListRepository
																.get(new ContractCode(contractCode), command.getRestoreDestinationTerminalList());
		//	2. delete(タイムレコード設定更新リスト<List>)														
		if(timeRecordSetUpdate.size() > 0 )
			this.timeRecordSetUpdateListRepository.delete(timeRecordSetUpdate);
	}	
}
