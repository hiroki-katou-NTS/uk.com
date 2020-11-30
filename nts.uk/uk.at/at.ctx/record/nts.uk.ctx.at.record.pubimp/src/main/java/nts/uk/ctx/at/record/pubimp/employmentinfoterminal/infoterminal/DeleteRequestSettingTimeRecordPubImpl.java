package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.DeleteRequestSettingTimeRecordService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.DeleteRequestSettingTimeRecordPub;

@Stateless
public class DeleteRequestSettingTimeRecordPubImpl implements DeleteRequestSettingTimeRecordPub {

	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	@Override
	public AtomTask remove(String empInfoTerCode, String contractCode) {
		RequireImpl Impl = new RequireImpl(timeRecordReqSettingRepository);
		return DeleteRequestSettingTimeRecordService.process(Impl, new EmpInfoTerminalCode(empInfoTerCode),
				new ContractCode(contractCode));

	}

	@AllArgsConstructor
	public class RequireImpl implements DeleteRequestSettingTimeRecordService.Require {

		private final TimeRecordReqSettingRepository timeRecordReqSettingRepository;

		@Override
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode) {
			return timeRecordReqSettingRepository.getTimeRecordReqSetting(empInfoTerCode, contractCode);
		}

		@Override
		public void updateSetting(TimeRecordReqSetting setting) {

			timeRecordReqSettingRepository.updateSetting(setting);
		}

	}

}
