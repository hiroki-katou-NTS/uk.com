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
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.SendWorkTypeNameService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendNRDataPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendWorkTypeNameExport;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class SendWorkTypeNamePubImpl implements SendNRDataPub<List<SendWorkTypeNameExport>> {

	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Override
	public List<SendWorkTypeNameExport> send(Integer empInfoTerCode, String contractCode) {

		RequireImpl impl = new RequireImpl(timeRecordReqSettingRepository, workTypeRepository);

		return SendWorkTypeNameService.send(impl, new EmpInfoTerminalCode(empInfoTerCode), new ContractCode(contractCode)).stream()
				.map(x -> new SendWorkTypeNameExport(x.getWorkTypeNumber(), x.getDaiClassifiNum(), x.getWorkName()))
				.collect(Collectors.toList());
	}

	@AllArgsConstructor
	public static class RequireImpl implements SendWorkTypeNameService.Require {

		private final TimeRecordReqSettingRepository timeRecordReqSettingRepository;

		private final WorkTypeRepository workTypeRepository;

		@Override
		public List<WorkType> getPossibleWork(String companyId, List<String> workTypeCode) {
			return workTypeRepository.getPossibleWorkType(companyId, workTypeCode);
		}

		@Override
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {
			return timeRecordReqSettingRepository.getTimeRecordReqSetting(empInfoTerCode, contractCode);
		}

	}

}
