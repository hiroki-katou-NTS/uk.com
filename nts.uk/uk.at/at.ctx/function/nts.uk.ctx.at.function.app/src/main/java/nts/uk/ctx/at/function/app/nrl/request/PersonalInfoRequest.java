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
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendPerInfoNameImport;

/**
 * Personal info request.
 * 
 * 個人情報リクエスト
 * @author manhnd
 */
@RequestScoped
@Named(Command.PERSONAL_INFO)
public class PersonalInfoRequest extends NRLRequest<Frame> {

	@Inject
	private SendNRDataAdapter sendNRDataAdapter;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#sketch(nts.uk.ctx.at.function.app.nrl.request.ResourceContext)
	 */
	@Override
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {
		List<MapItem> items = new ArrayList<>();
		items.add(FrameItemArranger.SOH());
		items.add(new MapItem(Element.HDR, Command.PERSONAL_INFO.Response));
		String contractCode =  context.getEntity().pickItem(Element.CONTRACT_CODE);
		List<SendPerInfoNameImport> lstPerInfo = sendNRDataAdapter.sendPerInfo(empInfoTerCode, contractCode);
		StringBuilder builder = new StringBuilder();
		for(SendPerInfoNameImport infoName : lstPerInfo) {
			builder.append(toStringObject(infoName));
		}
		String payload = builder.toString();
		byte[] payloadBytes = Codryptofy.decode(payload);
		int length = payloadBytes.length + DefaultValue.DEFAULT_LENGTH;
		items.add(new MapItem(Element.LENGTH, Integer.toHexString(length)));
		items.add(FrameItemArranger.Version());
		items.add(FrameItemArranger.FlagEndNoAck());
		items.add(FrameItemArranger.NoFragment());
		items.add(new MapItem(Element.NRL_NO, context.getTerminal().getNrlNo()));
		items.add(new MapItem(Element.MAC_ADDR, context.getTerminal().getMacAddress()));
		items.add(new MapItem(Element.CONTRACT_CODE, contractCode));
		items.add(FrameItemArranger.ZeroPadding());
		//Number of records
		items.add(new MapItem(Element.NUMBER, String.valueOf(lstPerInfo.size())));
		context.collectEncrypt(items, payload);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#responseLength()
	 */
	@Override
	public String responseLength() {
		return null;
	}
	
	private String toStringObject(SendPerInfoNameImport data) {
		StringBuilder builder = new StringBuilder(); 
		builder.append(StringUtils.rightPad(data.getIdNumber(), 20));
		//half payload16
		builder.append(StringUtils.rightPad(data.getPerName(), 20));
		builder.append(StringUtils.rightPad(data.getDepartmentCode(), 10));
		builder.append(StringUtils.rightPad(data.getCompanyCode(), 4));
		builder.append(StringUtils.rightPad(data.getReservation(), 4));
		builder.append(StringUtils.rightPad("", 6, " "));
		return builder.toString();
	}

}
