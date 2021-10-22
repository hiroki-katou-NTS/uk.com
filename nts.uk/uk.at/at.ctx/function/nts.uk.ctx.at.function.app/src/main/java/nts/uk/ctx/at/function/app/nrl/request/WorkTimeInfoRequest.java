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
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendNRDataAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendWorkTimeNameImport;

/**
 * @author ThanhNX
 *
 *就業時間帯リクエスト
 */
@RequestScoped
@Named(Command.WORKTIME_INFO)
public class WorkTimeInfoRequest extends NRLRequest<Frame>{

	@Inject
	private SendNRDataAdapter sendNRDataAdapter;
	
	@Override
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {
		List<SendWorkTimeNameImport> lstInfo = sendNRDataAdapter.sendWorkTime(empInfoTerCode,
				context.getTerminal().getContractCode());
		StringBuilder builder = new StringBuilder();
		for (SendWorkTimeNameImport infoName : lstInfo) {
			builder.append(toStringObject(infoName));
		}
		String payload = Codryptofy.paddingFullBlock(builder.toString());
		byte[] payloadBytes = Codryptofy.decode(payload);
		int length = payloadBytes.length + DefaultValue.DEFAULT_PADDING_LENGTH;
		List<MapItem> items = NRContentList.createDefaultField(Command.WORKTIME_INFO,
				Optional.ofNullable(Integer.toHexString(length)), context.getTerminal());
		// Number of records
		items.add(new MapItem(Element.NUMBER, StringUtils.leftPad(Integer.toHexString(lstInfo.size()).toUpperCase(), 4, "0")));
		context.collectEncrypt(items, payload);
	}

	private String toStringObject(SendWorkTimeNameImport data) {
		StringBuilder builder = new StringBuilder(); 
		builder.append(StringUtils.rightPad(data.getWorkTimeNumber(), 3));
		//half payload16
		builder.append(Codryptofy.paddingWithByte(data.getWorkTimeName(), 6));
		builder.append(StringUtils.rightPad(data.getTime(), 9));
		return builder.toString();
	}
	
	@Override
	public String responseLength() {
		return "";
	}

}
