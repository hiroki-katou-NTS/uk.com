package nts.uk.ctx.at.record.app.command.knr.knr002.f;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ReqComStatusMonitoring;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.ReqComStatusMonitoringRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NRLMachineInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.SettingValue;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormat;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.VariableName;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｆ：タイムレコード設定更新に登録する.Ｆ：タイムレコード設定更新に登録する
 * @author xuannt
 *
 */
@Stateless
public class TimeRecordSettingUpdateRegisterCommandHandler extends CommandHandler<TimeRecordSettingUpdateRegisterCommand>{
	//	リクエスト通信の状態監視Repository.[4] 取得する
	@Inject
	ReqComStatusMonitoringRepository reqComStatusMonitoringRepository;
	//	タイムレコード設定更新リストの削除CommandHandler.handle
	@Inject
	TimeRecordSettingUpdateListDeleteCommandHandler timeRecordSettingUpdateListDeleteCommandHandler;
	//	タイムレコード設定更新リストに登録するCommandHandler.handle
	@Inject
	TimeRecordSettingUpdateListRegisterCommandHandler timeRecordSettingUpdateListRegisterCommandHandler;
	@Override
	protected void handle(CommandHandlerContext<TimeRecordSettingUpdateRegisterCommand> context) {
		String contractCode = AppContexts.user().contractCode();
		TimeRecordSettingUpdateRegisterCommand command = context.getCommand();
		List<EmpInfoTerminalCode> terminalCodeList = command.getRestoreDestinationTerminalList();
		List<EmpInfoTerminalCode> listTerminalCode = command.getRestoreDestinationTerminalList();
		List<TimeRecordSetFormat> timeRecordSetFormat = command.getTimeRecordSettingFormatList();
		List<NRLMachineInfo> nrlMachineInfoList = command.getNrlMachineInfoList();
		//	「タイムレコード設定更新」の作成
		List<TimeRecordSetUpdate> timeRecordSetUpdate = timeRecordSetFormat.stream()
														.map(e -> new TimeRecordSetUpdate
																(new VariableName(e.getVariableName().v()),
																 new SettingValue(e.getSettingValue().v())))
														.collect(Collectors.toList());
															
		//	1. get*(契約コード、就業情報端末コード<List>、通信中): リクエスト通信の状態監視
		List<ReqComStatusMonitoring> reqComStatusMonitoring = this.reqComStatusMonitoringRepository.get(new ContractCode(contractCode), listTerminalCode, true);
		//	2. リクエスト通信の状態監視＞0件
		if(reqComStatusMonitoring.size() > 0)
			throw new BusinessException("Msg_1984");
		//	3. 削除する(契約コード、就業情報端末コード<List>): 契約コード、復旧先就業情報端末コード<List>
		this.timeRecordSettingUpdateListDeleteCommandHandler
			.handle(new TimeRecordSettingUpdateListDeleteCommand(command.getRestoreDestinationTerminalList()));
		//	4. 登録する(契約コード、就業情報端末コード<List>、タイムレコード設定フォーマット)
		this.timeRecordSettingUpdateListRegisterCommandHandler
			.handle(new TimeRecordSettingUpdateListRegisterCommand(terminalCodeList, timeRecordSetUpdate, nrlMachineInfoList));
	}
}
