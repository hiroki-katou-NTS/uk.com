package nts.uk.ctx.at.function.app.nrl.request;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

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
		String payload = Codryptofy.paddingFullBlock(info.isPresent() ? toStringObject(info.get()) : addDefault(0, MAX_RECORD_ALL).orElse(""));
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
			builder.append(Codryptofy.paddingWithByte(overTime.getSendOvertimeName(), 12));
		}
		
		addDefault(info.getOvertimes().size(), MAX_RECORD).ifPresent(data ->{
			builder.append(data);
		});
		
		for (SendOvertimeDetailImport vacation : info.getVacations()) {
			// half
			builder.append(Codryptofy.paddingWithByte(vacation.getSendOvertimeName(), 12));
		}

		addDefault(info.getVacations().size(), MAX_RECORD).ifPresent(data ->{
			builder.append(data);
		});
		
		return builder.toString();
	}

	private final int MAX_RECORD = 10;
	private final int MAX_RECORD_ALL = 20;
	private Optional<String> addDefault(int recordNumberInDB, int maxRecord) {
		if(recordNumberInDB == maxRecord) {
			return Optional.empty();
		}
		StringBuilder builder = new StringBuilder();
		IntStream.range(0, maxRecord-recordNumberInDB).boxed().forEach(ind ->{
			builder.append(Codryptofy.paddingWithByte(" ", 12));
		});
		return Optional.of(builder.toString());
	}
}
