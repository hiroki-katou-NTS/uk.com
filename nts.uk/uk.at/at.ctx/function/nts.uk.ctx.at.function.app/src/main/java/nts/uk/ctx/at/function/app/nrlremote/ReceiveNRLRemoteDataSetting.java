package nts.uk.ctx.at.function.app.nrlremote;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.request.NRLRequest;
import nts.uk.ctx.at.function.app.nrl.request.Named;
import nts.uk.ctx.at.function.app.nrl.request.ResourceContext;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatListRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service.ConvertNRLRemoteReceiveToObjectService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author thanh_nx
 *
 *         タイムレコード設定を保存する
 */
@RequestScoped
@Named(value = Command.TR_REMOTE_SEND_SETTING, decrypt = true)
public class ReceiveNRLRemoteDataSetting extends NRLRequest<Frame> {

	@Inject
	private TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;

	@Override
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {
		String payload = context.getEntity().pickItem(Element.PAYLOAD);

		RequireImpl impl = new RequireImpl(timeRecordSetFormatListRepository);
		Optional<AtomTask> result = ConvertNRLRemoteReceiveToObjectService.convertData(impl,
				new ContractCode(context.getTerminal().getContractCode()), new EmpInfoTerminalCode(empInfoTerCode),
				context.getEntity().pickItem(Element.MAC_ADDR), payload);

		result.ifPresent(x -> x.run());
		context.responseAccept();

	}

	@Override
	public String responseLength() {
		return "";
	}

	@AllArgsConstructor
	public class RequireImpl implements ConvertNRLRemoteReceiveToObjectService.Require {

		private final TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;

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
