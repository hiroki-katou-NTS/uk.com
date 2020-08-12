package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.SendWorkTimeNameService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendNRDataPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendWorkTimeNameExport;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;

@Stateless
public class SendWorkTimeNamePubImpl implements SendNRDataPub<List<SendWorkTimeNameExport>> {

	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	@Inject
	private WorkTimeSettingRepository timeSettingRepository;

	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

	@Override
	public List<SendWorkTimeNameExport> send(Integer empInfoTerCode, String contractCode) {

		RequireImpl impl = new RequireImpl(timeRecordReqSettingRepository, timeSettingRepository,
				predetemineTimeSettingRepository);
		return SendWorkTimeNameService.send(impl, new EmpInfoTerminalCode(empInfoTerCode), new ContractCode(contractCode)).stream().map(x -> {
			return new SendWorkTimeNameExport(x.getWorkTimeNumber(), x.getWorkTimeName(), x.getTime());
		}).collect(Collectors.toList());
	}

	@AllArgsConstructor
	public static class RequireImpl implements SendWorkTimeNameService.Require {

		private final TimeRecordReqSettingRepository timeRecordReqSettingRepository;

		private final WorkTimeSettingRepository timeSettingRepository;

		private final PredetemineTimeSettingRepository predetemineTimeSettingRepository;

		@Override
		public List<WorkTimeSetting> findByCodes(String companyId, List<String> lstWorkTimeCode) {
			return timeSettingRepository.findByCodes(companyId, lstWorkTimeCode);
		}

		@Override
		public List<PredetemineTimeSetting> findByCodeList(String companyID, List<String> lstWorkTimeCode) {
			return predetemineTimeSettingRepository.findByCodeList(companyID, lstWorkTimeCode);
		}

		@Override
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {
			return timeRecordReqSettingRepository.getTimeRecordReqSetting(empInfoTerCode, contractCode);
		}

	}

}
