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
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendWorkTypeNameImport;

/**
 * @author ThanhNX
 *
 *勤務種類リクエスト
 */
@RequestScoped
@Named(Command.WORKTYPE_INFO)
public class WorkTypeInfoRequest extends NRLRequest<Frame> {

	@Inject
	private SendNRDataAdapter sendNRDataAdapter;

	@Override
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {
		List<SendWorkTypeNameImport> lstWTInfo = sendNRDataAdapter.sendWorkType(empInfoTerCode,
				context.getTerminal().getContractCode());
		StringBuilder builder = new StringBuilder();
		for (SendWorkTypeNameImport infoName : lstWTInfo) {
			builder.append(toStringObject(infoName));
		}
		String payload = Codryptofy.paddingFullBlock(builder.toString());
		byte[] payloadBytes = Codryptofy.decode(payload);
		int length = payloadBytes.length + DefaultValue.DEFAULT_PADDING_LENGTH;
		List<MapItem> items = NRContentList.createDefaultField(Command.WORKTYPE_INFO,
				Optional.ofNullable(Integer.toHexString(length)), context.getTerminal());
		// Number of records
		items.add(new MapItem(Element.NUMBER, StringUtils.leftPad(Integer.toHexString(lstWTInfo.size()), 4, "0").toUpperCase()));
		context.collectEncrypt(items, payload);
	}

	private String toStringObject(SendWorkTypeNameImport data) {
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.rightPad(data.getWorkTypeNumber(), 3));
		builder.append(StringUtils.rightPad(data.getDaiClassifiNum(), 2));
		// half payload16
		builder.append(Codryptofy.paddingWithByte(data.getWorkName(), 6));
		return  builder.toString();
	}

	@Override
	public String responseLength() {
		return "";
	}

}
