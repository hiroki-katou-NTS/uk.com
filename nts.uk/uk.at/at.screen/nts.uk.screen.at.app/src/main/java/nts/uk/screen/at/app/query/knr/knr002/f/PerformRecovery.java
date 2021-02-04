package nts.uk.screen.at.app.query.knr.knr002.f;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.knr.knr002.f.TimeRecordSettingUpdateRegisterCommand;
import nts.uk.ctx.at.record.app.command.knr.knr002.f.TimeRecordSettingUpdateRegisterCommandHandler;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NRLMachineInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormat;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatBakRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatListRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｆ：復旧対象の選択ダイアログ.メニュー別OCD.Ｆ：復旧を行う
 * @author xuannt
 *
 */
@Stateless
public class PerformRecovery {
	//	タイムレコード設定フォーマットのバックアップReporitory.[5]タイムレコード設定フォーマットを 取得する
	//	タイムレコード設定フォーマットのバックアップReporitory.[4] 取得する
	@Inject
	TimeRecordSetFormatBakRepository timeRecordSetFormatBakRepository;
	//	タイムレコード設定更新に登録するCommandHandler.handle
	@Inject
	TimeRecordSettingUpdateRegisterCommandHandler timeRecordSettingUpdateRegisterCommandHandler;
	//	タイムレコード設定フォーマットリストRepository.get
	@Inject
	private TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;
	/**
	 * 復旧する
	 * @param empInfoTerCode
	 * @param terminalCodeList
	 * @return
	 */
	public PerformRecoveryDto recovery(String empInfoTerCode, List<String> terminalCodeList) {
		PerformRecoveryDto dto = new PerformRecoveryDto();
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		List<EmpInfoTerminalCode> restoreDestinationTerminalList = terminalCodeList.stream()
				   																   .map(e -> new EmpInfoTerminalCode(e))
				   																   .collect(Collectors.toList());
		//	1. タイムレコード設定フォーマットを 取得する(契約コード、就業情報端末コード): タイムレコード設定フォーマット<List>
		List<TimeRecordSetFormat> timeRecordSettingFormatList = this.timeRecordSetFormatBakRepository
													   .getTimeRecordSetFormat
													   		(contractCode,
													   		new EmpInfoTerminalCode(empInfoTerCode));
		//	2. 登録する(契約コード、復旧先就業情報端末コード<List>、タイムレコード設定フォーマット<List>)
		List<TimeRecordSetFormatList> machineInfoLst = this.timeRecordSetFormatListRepository
				   .get(contractCode, terminalCodeList.stream().map(e -> new EmpInfoTerminalCode(e)).collect(Collectors.toList()));
		List<NRLMachineInfo> nrlMachineInfoList = machineInfoLst.stream()
													  .map(e -> 
														  new NRLMachineInfo(e.getEmpInfoTerCode(),
																  e.getEmpInfoTerName(),
																  e.getRomVersion(),
																  e.getModelEmpInfoTer())
													  ).collect(Collectors.toList());
		this.timeRecordSettingUpdateRegisterCommandHandler
			.handle(new TimeRecordSettingUpdateRegisterCommand(
					restoreDestinationTerminalList,
					timeRecordSettingFormatList,
					nrlMachineInfoList));
		return dto;
	}
}
