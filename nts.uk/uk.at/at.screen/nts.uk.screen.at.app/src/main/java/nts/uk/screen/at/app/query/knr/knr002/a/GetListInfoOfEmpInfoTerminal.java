package nts.uk.screen.at.app.query.knr.knr002.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.dom.adapter.imploymentinfoterminal.infoterminal.EmpInfoTerminalComStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.imploymentinfoterminal.infoterminal.EmpInfoTerminalComStatusImport;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TerminalComStatus;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TerminalCurrentState;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.DeterminingReqStatusTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.JudgCurrentStatusEmpInfoTerminal;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author dungbn
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ａ：就業情報端末の監視.メニュー別OCD.Ａ：就業情報端末の監視画面を起動する
 *
 */
@Stateless
public class GetListInfoOfEmpInfoTerminal {

	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;
	
	@Inject
	private WorkLocationRepository workLocationRepository;
	
	@Inject
	private EmpInfoTerminalComStatusAdapter empInfoTerminalComStatusAdapter;
	
	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;
	
	public InfoOfEmpInfoTerminalDto getInfoOfEmpInfoTerminal() {
		String companyId = AppContexts.user().companyId();
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		RequireJudImpl requireJudImpl = new RequireJudImpl(empInfoTerminalComStatusAdapter);
		RequireDeterminingImpl requireDeterminingImpl = new RequireDeterminingImpl(timeRecordReqSettingRepository);
		List<EmpInfoTerminal> listEmpInfo = empInfoTerminalRepository.get(contractCode);
		List<String> listEmpInfoCode = listEmpInfo.stream().map(e -> e.getEmpInfoTerCode().v()).collect(Collectors.toList());
		Map<String, String> mapWorkLocationCodeAndName = workLocationRepository.getNameByCode(companyId, listEmpInfoCode);
		TerminalComStatus terminalComStatus = JudgCurrentStatusEmpInfoTerminal.judgingTerminalCurrentState(requireJudImpl, contractCode, listEmpInfo);
		Map<EmpInfoTerminalCode, Boolean> mapResults = DeterminingReqStatusTerminal.determiningReqStatusTerminal(requireDeterminingImpl, contractCode, listEmpInfo);
		List<EmpInfoTerminalDto> listEmpInfoTerminalDto = new ArrayList<EmpInfoTerminalDto>();
				
		listEmpInfo.stream().forEach(e -> {
			EmpInfoTerminalDto empDto = new EmpInfoTerminalDto(
					e.getEmpInfoTerCode().v(),
					e.getEmpInfoTerName().v(),
					e.getModelEmpInfoTer().value,
					e.getCreateStampInfo().getWorkLocationCd().get().v(),
					mapWorkLocationCodeAndName.get(e.getEmpInfoTerCode().v()),
					terminalComStatus.getMapByCode(terminalComStatus.getListComstate()).get(e.getEmpInfoTerCode()).getSignalLastTime(),
					terminalComStatus.getMapByCode(terminalComStatus.getListComstate()).get(e.getEmpInfoTerCode()).getTerminalCurrentState().value,
					mapResults.get(e.getEmpInfoTerCode()));
			listEmpInfoTerminalDto.add(empDto);
		});
		
		InfoOfEmpInfoTerminalDto dto = new InfoOfEmpInfoTerminalDto(
				terminalComStatus.getTotalNumberOfTer(),
				(int) terminalComStatus.getListStateCount().stream().filter(e -> e.getTerminalCurrentState().equals(TerminalCurrentState.NORMAL)).count(),
				(int) terminalComStatus.getListStateCount().stream().filter(e -> e.getTerminalCurrentState().equals(TerminalCurrentState.ABNORMAL)).count(),
				(int) terminalComStatus.getListStateCount().stream().filter(e -> e.getTerminalCurrentState().equals(TerminalCurrentState.NOT_COMMUNICATED)).count(),
				listEmpInfoTerminalDto);
		return dto;
	}
	
	@AllArgsConstructor
	private static class RequireJudImpl implements JudgCurrentStatusEmpInfoTerminal.Require {
		
		private EmpInfoTerminalComStatusAdapter empInfoTerminalComStatusAdapter;

		@Override
		public List<EmpInfoTerminalComStatusImport> get(ContractCode contractCode,
				List<EmpInfoTerminalCode> empInfoTerCodeList) {
			return empInfoTerminalComStatusAdapter.get(contractCode, empInfoTerCodeList);
		}
	}
	
	@AllArgsConstructor
	private static class RequireDeterminingImpl implements DeterminingReqStatusTerminal.RequireDetermining {

		private TimeRecordReqSettingRepository timeRecordReqSettingRepository;
		
		@Override
		public List<TimeRecordReqSetting> get(ContractCode contractCode, List<EmpInfoTerminalCode> listCode) {
			return timeRecordReqSettingRepository.get(contractCode, listCode);
		}
	}
}
