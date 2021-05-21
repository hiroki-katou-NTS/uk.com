package nts.uk.ctx.at.function.app.nrl.request;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.DefaultValue;
import nts.uk.ctx.at.function.app.nrl.NRContentList;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendNRDataAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendOvertimeNameImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendOvertimeNameImport.SendOvertimeDetailImport;

/**
 * @author ThanhNX
 *
 *残業・休日出勤リクエスト
 */
@RequestScoped
@Named(Command.OVERTIME_INFO)
public class OverTimeInfoRequest extends NRLRequest<Frame> {

	@Inject
	private SendNRDataAdapter sendNRDataAdapter;

	@Override
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {
		Optional<SendOvertimeNameImport> info = sendNRDataAdapter.sendOvertime(empInfoTerCode,
				context.getTerminal().getContractCode());
		String payload = info.isPresent() ? toStringObject(info.get()) : "";
		byte[] payloadBytes = Codryptofy.decode(payload);
		int length = payloadBytes.length + DefaultValue.DEFAULT_LENGTH;
		List<MapItem> items = NRContentList.createDefaultField(Command.OVERTIME_INFO,
				Optional.ofNullable(Integer.toHexString(length)), context.getTerminal());
		// Number of records
		context.collectEncrypt(items, payload);

	}

	@Override
	public String responseLength() {
		return "";
	}

	private String toStringObject(SendOvertimeNameImport info) {
		StringBuilder builder = new StringBuilder();
		for (SendOvertimeDetailImport overTime : info.getOvertimes()) {
			// half
			builder.append(StringUtils.rightPad(overTime.getSendOvertimeName(), 12));
		}

		for (SendOvertimeDetailImport vacation : info.getVacations()) {
			// half
			builder.append(StringUtils.rightPad(vacation.getSendOvertimeName(), 12));
		}

		return builder.toString();
	}

}
