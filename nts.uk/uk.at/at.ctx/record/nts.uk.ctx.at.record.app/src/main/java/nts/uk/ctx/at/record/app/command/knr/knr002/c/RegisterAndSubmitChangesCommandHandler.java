package nts.uk.ctx.at.record.app.command.knr.knr002.c;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ReqComStatusMonitoring;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.ReqComStatusMonitoringRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NRRomVersion;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.SettingValue;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.VariableName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatListRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetUpdateListRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author dungbn
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｃ：変更の登録と送信.Ｃ：変更の登録と送信
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterAndSubmitChangesCommandHandler extends CommandHandler<RegisterAndSubmitChangesCommand> {

	@Inject
	private ReqComStatusMonitoringRepository reqComStatusMonitoringRepository;
	
	@Inject
	private DeleteTimeRecordSetUpdateListCommandHandler deleteCommandHandler;
	
	@Inject
	private RegisterTimeRecordSetUpdateListCommandHandler registerTimeRecordSetUpdateListCommandHandler;
	
	@Inject
	private TimeRecordSetUpdateListRepository timeRecordSetUpdateListRepository;
	
	//	タイムレコード設定フォーマットリストRepository.get
	@Inject
	private TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;
	
	@Override
	protected void handle(CommandHandlerContext<RegisterAndSubmitChangesCommand> context) {
		
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		RegisterAndSubmitChangesCommand command = context.getCommand();
		List<EmpInfoTerminalCode> listEmpInfoTerminalCodes = command.getEmpInfoTerCode().stream().map(e -> new EmpInfoTerminalCode(e)).collect(Collectors.toList());
		
		// 1: get*(契約コード、就業情報端末コード<List>、通信中): リクエスト通信の状態監視
		List<ReqComStatusMonitoring> listReqComStatusMonitoring = reqComStatusMonitoringRepository.get(contractCode, listEmpInfoTerminalCodes, true);
		
		List<TimeRecordSetUpdateList> listTimeRecordSetUpdateList = timeRecordSetUpdateListRepository.get(contractCode, listEmpInfoTerminalCodes);
		
		List<TimeRecordSetFormatList> machineInfoLst = this.timeRecordSetFormatListRepository.get(contractCode, listEmpInfoTerminalCodes);
		
		// 2: リクエスト通信の状態監視＞0件
		if (!listReqComStatusMonitoring.isEmpty()) {
			throw new BusinessException("Msg_1984");
		}
		
		// 3: delete(require, 契約コード, 就業情報端末コード<List>)
		deleteCommandHandler.handle(listTimeRecordSetUpdateList);
		
		List<TimeRecordSetUpdate> listTimeRecordSetUpdate = command.getListTimeRecordSetUpdateDto().stream()
																.map(e -> new TimeRecordSetUpdate(new VariableName(e.getVariableName()), new SettingValue(e.getUpdateValue())))
																.collect(Collectors.toList());
		
		List<TimeRecordSetUpdateList> listTimeRecordSetUpdateListForRegister = 
										listEmpInfoTerminalCodes.stream()
										.map(e -> {
											Optional<TimeRecordSetFormatList> machineInfo = machineInfoLst.stream().filter(x -> Integer.parseInt(x.getEmpInfoTerCode().v()) == Integer.parseInt(e.v())).findAny();
											if(!machineInfo.isPresent()) {
												//	do something
												return new TimeRecordSetUpdateList(e, new EmpInfoTerminalName(""), new NRRomVersion(""),  ModelEmpInfoTer.valueOf(command.getModelEmpInfoTer()), listTimeRecordSetUpdate);
											}
											TimeRecordSetFormatList machineInfoVal = machineInfo.get();
											TimeRecordSetUpdateList timeRecordSetUpdateList = 
																		new TimeRecordSetUpdateList(e,
																		  machineInfoVal.getEmpInfoTerName(),
																		  machineInfoVal.getRomVersion(),
																		  machineInfoVal.getModelEmpInfoTer(),
																		  listTimeRecordSetUpdate
																			);
											return timeRecordSetUpdateList;	
											}).collect(Collectors.toList());
		
		// 4: 登録する(require, 契約コード、就業情報端末コード、タイムレコード設定更新リスト)
		registerTimeRecordSetUpdateListCommandHandler.handle(listTimeRecordSetUpdateListForRegister);
	}

}
