package nts.uk.screen.at.app.query.knr.knr002.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatusImport;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ComState;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.StateCount;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TerminalComStatus;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TerminalCurrentState;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TerminalProSwitchManagement;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TerminalComStatus.ComStateobject;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TerminalProSwitchManagementRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.DeterminingReqStatusTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.JudgCurrentStatusEmpInfoTerminal;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author dungbn
 * UKDesign.UniversalK.??????.KNR_??????????????????.KNR002_???????????????????????????.?????????????????????????????????.???????????????OCD.??????????????????????????????????????????????????????
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
	
	@Inject
	private TerminalProSwitchManagementRepository terminalProSwitchManagementRepository;
	
	public InfoOfEmpInfoTerminalDto getInfoOfEmpInfoTerminal() {
		
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		
		RequireJudImpl requireJudImpl = new RequireJudImpl(empInfoTerminalComStatusAdapter);
		RequireDeterminingImpl requireDeterminingImpl = new RequireDeterminingImpl(timeRecordReqSettingRepository);
		
		// 1: get*(???????????????)????????????????????????<List>
		List<EmpInfoTerminal> listEmpInfo = empInfoTerminalRepository.get(contractCode);
		
		if (listEmpInfo.isEmpty()) {
			return new InfoOfEmpInfoTerminalDto();
		}
				
		List<String> listWorkLocationCd = listEmpInfo.stream().filter(e -> e.getCreateStampInfo().getWorkLocationCd().isPresent())
															  .map(e -> e.getCreateStampInfo().getWorkLocationCd().get().v())
															  .collect(Collectors.toList());
		
		// 2: get*([???????????????????????????????????????????????????.?????????????????????.?????????????????????]<List>): ??????????????????
		Map<String, String> mapWorkLocationCodeAndName = workLocationRepository.getNameByCode(contractCode.v(), listWorkLocationCd);
		
		// 3: ??????????????????????????????(require, ???????????????, ??????????????????List): ?????????????????????
		TerminalComStatus terminalComStatus = JudgCurrentStatusEmpInfoTerminal.judgingTerminalCurrentState(requireJudImpl, contractCode, listEmpInfo);
		Map<EmpInfoTerminalCode, ComStateobject> mapByCode = terminalComStatus.getMapByCode(terminalComStatus.getListComstate());
		
		// 4: ???????????????????????????????????????(require, ???????????????, ???????????????????????????List): Map<?????????????????????????????????Flag>
		Map<EmpInfoTerminalCode, Boolean> mapResults = DeterminingReqStatusTerminal.determiningReqStatusTerminal(requireDeterminingImpl, contractCode, listEmpInfo);
		List<EmpInfoTerminalDto> listEmpInfoTerminalDto = new ArrayList<EmpInfoTerminalDto>();
				
		listEmpInfo.stream().forEach(e -> {
			EmpInfoTerminalDto empDto = new EmpInfoTerminalDto(
					e.getEmpInfoTerCode().v(),
					e.getEmpInfoTerName().v(),
					e.getModelEmpInfoTer().value,
					e.getCreateStampInfo().getWorkLocationCd().isPresent() ?
										  e.getCreateStampInfo().getWorkLocationCd().get().v() : "",
					e.getCreateStampInfo().getWorkLocationCd().isPresent() ? mapWorkLocationCodeAndName.get(e.getCreateStampInfo().getWorkLocationCd().get().v()) : "",
					mapByCode.get(e.getEmpInfoTerCode())
											.getSignalLastTime()
											.isPresent() 
											? terminalComStatus.getMapByCode(terminalComStatus.getListComstate()).get(e.getEmpInfoTerCode()).getSignalLastTime().get().toString()
											: "",
					terminalComStatus.getMapByCode(terminalComStatus.getListComstate()).get(e.getEmpInfoTerCode()).getTerminalCurrentState().value,
					mapResults.get(e.getEmpInfoTerCode()));
			listEmpInfoTerminalDto.add(empDto);
		});
		
		int numNormalState = 0;
		
		int numAbnormalState = 0;
		
		int numUntransmitted = 0;
		
		for (StateCount state : terminalComStatus.getListStateCount()) {
			
			if (state.getTerminalCurrentState().equals(TerminalCurrentState.NORMAL)) {
				numNormalState = state.getNumberOfTerminal();
			}
			if (state.getTerminalCurrentState().equals(TerminalCurrentState.ABNORMAL)) {
				numAbnormalState = state.getNumberOfTerminal();
			}
			if (state.getTerminalCurrentState().equals(TerminalCurrentState.NOT_COMMUNICATED)) {
				numUntransmitted = state.getNumberOfTerminal();
			}
		}
		
		// 5: get(???????????????): ???????????????????????????
		Optional<TerminalProSwitchManagement> terminalProSwitchManagement = terminalProSwitchManagementRepository.get(contractCode);
		
		InfoOfEmpInfoTerminalDto dto = new InfoOfEmpInfoTerminalDto(
				terminalComStatus.getTotalNumberOfTer(),
				numNormalState,
				numAbnormalState,
				numUntransmitted,
				listEmpInfoTerminalDto,
				terminalProSwitchManagement.isPresent() ? terminalProSwitchManagement.get().getManagementAtr().value : 3);
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
