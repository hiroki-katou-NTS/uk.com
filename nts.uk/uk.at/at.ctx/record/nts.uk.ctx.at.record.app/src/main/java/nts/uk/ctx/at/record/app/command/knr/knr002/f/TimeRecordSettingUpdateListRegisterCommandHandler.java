package nts.uk.ctx.at.record.app.command.knr.knr002.f;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NRLMachineInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetUpdateListRepository;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｆ：タイムレコード設定更新に登録する.Ｆ：タイムレコード設定更新リストに登録する
 * @author xuannt
 *
 */
@Stateless
public class TimeRecordSettingUpdateListRegisterCommandHandler extends CommandHandler<TimeRecordSettingUpdateListRegisterCommand>{

	//	タイムレコード設定更新リストRepository.[3]Insert(List<タイムレコード設定更新リスト>) 
	//	タイムレコード設定更新リストRepository.[1] タイムレコード設定更新リストを取得する
	@Inject
	TimeRecordSetUpdateListRepository timeRecordSetUpdateListRepository;

	@Override
	protected void handle(CommandHandlerContext<TimeRecordSettingUpdateListRegisterCommand> context) {
		TimeRecordSettingUpdateListRegisterCommand command = context.getCommand();
		List<TimeRecordSetUpdate> timeRecordSetUpdate = command.getTimeRecordSetUpdateList();
		List<EmpInfoTerminalCode> terminalCodeList = command.getTerminalCodeList();
		List<NRLMachineInfo> nrlMachineInfoList = command.getNrlMachineInfoList();
		//	1. create(契約コード、就業情報端末コード、タイムレコード設定更新<List>)
		List<TimeRecordSetUpdateList> timeRecordList = terminalCodeList.stream()
				.map(e -> {
					Optional<NRLMachineInfo> machineInfo = nrlMachineInfoList.stream().filter(x -> Integer.parseInt(x.getEmpInfoTerCode().v()) == Integer.parseInt(e.v())).findAny();
					if (!machineInfo.isPresent()) {
						return null;
					}	
					NRLMachineInfo machineInfoVal = machineInfo.get();
					return new TimeRecordSetUpdateList
									(machineInfoVal.getEmpInfoTerCode(),
									 machineInfoVal.getEmpInfoTerName(),
									 machineInfoVal.getRomVersion(),
									 machineInfoVal.getModelEmpInfoTer(),
									 timeRecordSetUpdate);
				}).filter(t -> null != t).collect(Collectors.toList());
		
		//	2. persist
		this.timeRecordSetUpdateListRepository.insert(timeRecordList);
	}
}
