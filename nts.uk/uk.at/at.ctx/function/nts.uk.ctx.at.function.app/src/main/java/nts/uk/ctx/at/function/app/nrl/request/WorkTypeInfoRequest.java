package nts.uk.ctx.at.function.app.nrl.request;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.DefaultValue;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;
import nts.uk.ctx.at.function.app.nrl.data.FrameItemArranger;
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
		// TODO Auto-generated method stub
		List<MapItem> items = new ArrayList<>();
		items.add(FrameItemArranger.SOH());
		items.add(new MapItem(Element.HDR, Command.WORKTYPE_INFO.Response));
		// Get worktype info from DB, count records
		String contractCode =  context.getEntity().pickItem(Element.CONTRACT_CODE);
		List<SendWorkTypeNameImport> lstWTInfo = sendNRDataAdapter.sendWorkType(empInfoTerCode,
				contractCode);
		StringBuilder builder = new StringBuilder();
		for (SendWorkTypeNameImport infoName : lstWTInfo) {
			builder.append(toStringObject(infoName));
		}
		String payload = builder.toString();
		byte[] payloadBytes = Codryptofy.decode(payload);
		int length = payloadBytes.length +  DefaultValue.DEFAULT_LENGTH;
		items.add(new MapItem(Element.LENGTH, Integer.toHexString(length)));
		items.add(FrameItemArranger.Version());
		items.add(FrameItemArranger.FlagEndNoAck());
		items.add(FrameItemArranger.NoFragment());
		items.add(new MapItem(Element.NRL_NO, context.getTerminal().getNrlNo()));
		items.add(new MapItem(Element.MAC_ADDR, context.getTerminal().getMacAddress()));
		items.add(new MapItem(Element.CONTRACT_CODE, contractCode));
		items.add(FrameItemArranger.ZeroPadding());
		// Number of records
		items.add(new MapItem(Element.NUMBER, String.valueOf(lstWTInfo.size())));
		context.collectEncrypt(items, payload);
	}

	private String toStringObject(SendWorkTypeNameImport data) {
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.rightPad(data.getWorkTypeNumber(), 3));
		builder.append(StringUtils.rightPad(data.getDaiClassifiNum(), 2));
		// half payload16
		builder.append(StringUtils.rightPad(data.getWorkName(), 6));
		builder.append(StringUtils.rightPad("", 5, " "));
		return builder.toString();
	}

	@Override
	public String responseLength() {
		return null;
	}

}
