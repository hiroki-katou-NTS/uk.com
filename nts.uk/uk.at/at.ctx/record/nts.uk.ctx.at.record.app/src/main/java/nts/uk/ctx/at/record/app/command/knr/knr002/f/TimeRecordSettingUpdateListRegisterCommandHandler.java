package nts.uk.ctx.at.record.app.command.knr.knr002.f;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NRLMachineInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NRRomVersion;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetUpdateListRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

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
	//	就業情報端末Repository.[1]  就業情報端末を取得する
	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;
	@Override
	protected void handle(CommandHandlerContext<TimeRecordSettingUpdateListRegisterCommand> context) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		TimeRecordSettingUpdateListRegisterCommand command = context.getCommand();
		List<TimeRecordSetUpdate> timeRecordSetUpdate = command.getTimeRecordSetUpdateList();
		List<EmpInfoTerminalCode> terminalCodeList = command.getTerminalCodeList();
		List<NRLMachineInfo> nrlMachineInfoList = command.getNrlMachineInfoList();
		//	1. create(契約コード、就業情報端末コード、タイムレコード設定更新<List>)
		List<TimeRecordSetUpdateList> timeRecodeList = terminalCodeList.stream()
				.map(e -> {
					Optional<EmpInfoTerminal> terminal = this.empInfoTerminalRepository.getEmpInfoTerminal(e, contractCode);
					if(!terminal.isPresent())
						return null;
					EmpInfoTerminal terminalValue = terminal.get();
					Optional<NRLMachineInfo> romVersionOpt = nrlMachineInfoList.stream().filter(x -> Integer.parseInt(x.getEmpInfoTerCode().v()) == Integer.parseInt(terminalValue.getEmpInfoTerCode().v())).findAny();
					NRRomVersion romVersion = new NRRomVersion("");
					if (romVersionOpt.isPresent()) {
						romVersion = romVersionOpt.get().getRomVersion();
					}
					
					return new TimeRecordSetUpdateList
									(terminalValue.getEmpInfoTerCode(),
									 terminalValue.getEmpInfoTerName(),
									 romVersion,
									 terminalValue.getModelEmpInfoTer(),
									 timeRecordSetUpdate);
				}).collect(Collectors.toList());
		
		//	2. persist
		this.timeRecordSetUpdateListRepository.insert(timeRecodeList);
	}
}
