package nts.uk.ctx.at.function.app.nrlremote;

import java.io.InputStream;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatListRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service.ConvertNRLRemoteReceiveToObjectService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

@Stateless
public class ReceiveNRLRemoteDataSetting {

	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;

	@Inject
	private TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;

	public void process(InputStream in) {

		RequireImpl impl = new RequireImpl(empInfoTerminalRepository, timeRecordSetFormatListRepository);
		Optional<AtomTask> result = ConvertNRLRemoteReceiveToObjectService.convertData(impl, in);

		result.ifPresent(x -> x.run());

	}

	@AllArgsConstructor
	public class RequireImpl implements ConvertNRLRemoteReceiveToObjectService.Require {

		private final EmpInfoTerminalRepository empInfoTerminalRepository;

		private final TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;

		@Override
		public Optional<EmpInfoTerminal> getEmpInfoTerWithMac(MacAddress macAdd, ContractCode contractCode) {
			return empInfoTerminalRepository.getEmpInfoTerWithMac(macAdd, contractCode);
		}

		@Override
		public void removeTRSetFormatList(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {
			timeRecordSetFormatListRepository.removeTRSetFormatList(empInfoTerCode, contractCode);
		}

		@Override
		public void insert(ContractCode code, TimeRecordSetFormatList trSetFormat) {
			timeRecordSetFormatListRepository.insert(code, trSetFormat);
		}

	}

}
