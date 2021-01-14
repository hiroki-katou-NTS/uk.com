package nts.uk.screen.at.app.query.knr.knr002.e;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatBak;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatBakRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatListRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetUpdateListRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service.JudgmentWaitingRequest;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author dungbn
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｅ：就業情報端末のバックアップ.メニュー別OCD.Ｅ：バックアップ画面の初期表示
 *
 */
@Stateless
public class InitialDisplayBackupScreen {
	
	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;
	
	@Inject
	private TimeRecordSetFormatBakRepository timeRecordSetFormatBakRepository;
	
	@Inject
	private TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;
	
	@Inject
	private TimeRecordSetUpdateListRepository timeRecordSetUpdateListRepository;

	public InitialDisplayBackupScreenDto handle() {
		
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		// 1: get*(契約コード)：　就業情報端末
		List<EmpInfoTerminal> listEmpInfoTerminal = empInfoTerminalRepository.get(contractCode);
		
		// 2: get*(契約コード): タイムレコード設定フォーマットのバックアップ
		List<TimeRecordSetFormatBak> listTimeRecordSetFormatBak = timeRecordSetFormatBakRepository.get(contractCode);
		
		Require require = new Require(timeRecordSetFormatListRepository, timeRecordSetUpdateListRepository);
		
		// 3: リクエスト待ち状況の判断(require, 契約コード, 端末コードList): Map<就業情報端末コード、リクエスト待ちFlag>
		Map<EmpInfoTerminalCode, Boolean> mapCodeFlag = JudgmentWaitingRequest.judgmentReqWaitingStatus(require, contractCode, listEmpInfoTerminal);
		
		return toDto(listEmpInfoTerminal, listTimeRecordSetFormatBak, mapCodeFlag);
	}
	
	@AllArgsConstructor
	private static class Require implements JudgmentWaitingRequest.Require {
		
		private TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;
		
		private TimeRecordSetUpdateListRepository timeRecordSetUpdateListRepository;
		
		@Override
		public List<TimeRecordSetFormatList> getTimeRecordSetFormatList(ContractCode contractCode,
				List<EmpInfoTerminalCode> listEmpInfoTerCode) {
			return timeRecordSetFormatListRepository.get(contractCode, listEmpInfoTerCode);
		}

		@Override
		public List<TimeRecordSetUpdateList> getTimeRecordUpdateList(ContractCode contractCode,
				List<EmpInfoTerminalCode> listEmpInfoTerCode) {
			return timeRecordSetUpdateListRepository.get(contractCode, listEmpInfoTerCode);
		}
		
	}
	
	private InitialDisplayBackupScreenDto toDto(List<EmpInfoTerminal> listEmpInfoTerminal, List<TimeRecordSetFormatBak> listTimeRecordSetFormatBak, Map<EmpInfoTerminalCode, Boolean> mapCodeFlag) {
			
			List<EmpInfoTerminalEDto> listEmpDto = listEmpInfoTerminal.stream().map(e -> new EmpInfoTerminalEDto(e.getEmpInfoTerCode().v(), e.getEmpInfoTerName().v(), e.getModelEmpInfoTer().value)).collect(Collectors.toList());
			List<TimeRecordSetFormatBakEDto> listTimeRecordDto = listTimeRecordSetFormatBak.stream().map(e -> new TimeRecordSetFormatBakEDto(e.getBackupDate().toString(), e.getEmpInfoTerCode().v(), e.getEmpInfoTerName().v(), e.getModelEmpInfoTer().value)).collect(Collectors.toList());
			List<FlagByCode> listCodeFlag = mapCodeFlag.entrySet().stream().map(e -> new FlagByCode(e.getKey().v(), e.getValue())).collect(Collectors.toList());
			
			Collections.sort(listCodeFlag, (FlagByCode o1, FlagByCode o2) -> 
				Integer.parseInt(o1.getEmpInfoTerminalCode()) - Integer.parseInt(o2.getEmpInfoTerminalCode())
			);
			
			return new InitialDisplayBackupScreenDto(listEmpDto, listTimeRecordDto, listCodeFlag);
		}
}
