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
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.SendOvertimeNameService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendOvertimeNameExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendOvertimeNamePub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendOvertimeNameExport.SendOvertimeDetailExport;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;

@Stateless
public class SendOvertimeNamePubImpl implements SendOvertimeNamePub {

	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	@Inject
	private OvertimeWorkFrameRepository overtimeWorkFrameRepository;

	@Inject
	private WorkdayoffFrameRepository workdayoffFrameRepository;

	@Override
	public Optional<SendOvertimeNameExport> send(Integer empInfoTerCode, String contractCode) {

		RequireImpl impl = new RequireImpl(timeRecordReqSettingRepository, overtimeWorkFrameRepository,
				workdayoffFrameRepository);

		return SendOvertimeNameService.send(impl, new EmpInfoTerminalCode(empInfoTerCode), new ContractCode(contractCode)).map(x -> {

			List<SendOvertimeDetailExport> overtimes = x.getOvertimes().stream()
					.map(y -> new SendOvertimeDetailExport(y.getSendOvertimeNo(), y.getSendOvertimeName()))
					.collect(Collectors.toList());

			List<SendOvertimeDetailExport> vacations = x.getVacations().stream()
					.map(y -> new SendOvertimeDetailExport(y.getSendOvertimeNo(), y.getSendOvertimeName()))
					.collect(Collectors.toList());

			return new SendOvertimeNameExport(overtimes, vacations);

		});
	}

	@AllArgsConstructor
	public static class RequireImpl implements SendOvertimeNameService.Require {

		private final TimeRecordReqSettingRepository timeRecordReqSettingRepository;

		private final OvertimeWorkFrameRepository overtimeWorkFrameRepository;

		private final WorkdayoffFrameRepository workdayoffFrameRepository;

		@Override
		public List<OvertimeWorkFrame> getAllOvertimeWorkFrame(String companyId) {
			return overtimeWorkFrameRepository.getAllOvertimeWorkFrame(companyId);
		}

		@Override
		public List<WorkdayoffFrame> getAllWorkdayoffFrame(String companyId) {
			return workdayoffFrameRepository.getAllWorkdayoffFrame(companyId);
		}

		@Override
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {
			return timeRecordReqSettingRepository.getTimeRecordReqSetting(empInfoTerCode, contractCode);
		}

	}
}
