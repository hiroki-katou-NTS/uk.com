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
		// TODO Auto-generated method stub
		List<MapItem> items = new ArrayList<>();
		items.add(FrameItemArranger.SOH());
		items.add(new MapItem(Element.HDR, Command.WORKTIME_INFO.Response));
		//Get work time info from DB, count records
		String contractCode =  context.getEntity().pickItem(Element.CONTRACT_CODE);
		List<SendWorkTimeNameImport> lstInfo = sendNRDataAdapter.sendWorkTime(empInfoTerCode, contractCode);
		StringBuilder builder = new StringBuilder();
		for(SendWorkTimeNameImport infoName : lstInfo) {
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
		//Number of records
		items.add(new MapItem(Element.NUMBER, String.valueOf(lstInfo.size())));
		context.collectEncrypt(items, payload);
	}

	private String toStringObject(SendWorkTimeNameImport data) {
		StringBuilder builder = new StringBuilder(); 
		builder.append(StringUtils.rightPad(data.getWorkTimeNumber(), 3));
		//half payload16
		builder.append(StringUtils.rightPad(data.getWorkTimeName(), 6));
		builder.append(StringUtils.rightPad(data.getTime(), 9));
		builder.append(StringUtils.rightPad("", 14, " "));
		return builder.toString();
	}
	
	@Override
	public String responseLength() {
		return null;
	}

}
