package nts.uk.ctx.at.function.app.nrl.request;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;
import nts.uk.ctx.at.function.app.nrl.data.FrameItemArranger;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendNRDataAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendReservationMenuImport;

/**
 * @author ThanhNX
 *
 *予約メニューリクエスト
 */
@RequestScoped
@Named(Command.RESERVATION_INFO)
public class ReservationMenuInfoRequest extends NRLRequest<Frame> {

	@Inject
	private SendNRDataAdapter sendNRDataAdapter;

	@Override
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {
		// TODO Auto-generated method stub
		List<MapItem> items = new ArrayList<>();
		items.add(FrameItemArranger.SOH());
		items.add(new MapItem(Element.HDR, Command.RESERVATION_INFO.Response));
		// Get work time info from DB, count records
		String contractCode =  context.getEntity().pickItem(Element.CONTRACT_CODE);
		List<SendReservationMenuImport> lstInfo = sendNRDataAdapter.sendReservMenu(empInfoTerCode,
				contractCode);
		String payload = toStringObject(lstInfo);
		byte[] payloadBytes = Codryptofy.decode(payload);
		int length = payloadBytes.length + 52;
		items.add(new MapItem(Element.LENGTH, Integer.toHexString(length)));
		items.add(FrameItemArranger.Version());
		items.add(FrameItemArranger.FlagEndNoAck());
		items.add(FrameItemArranger.NoFragment());
		items.add(new MapItem(Element.NRL_NO, context.getTerminal().getNrlNo()));
		items.add(new MapItem(Element.MAC_ADDR, context.getTerminal().getMacAddress()));
		items.add(new MapItem(Element.CONTRACT_CODE, contractCode));
		items.add(FrameItemArranger.ZeroPadding());
		// Number of records
		context.collectEncrypt(items, payload);

	}

	@Override
	public String responseLength() {
		// TODO Auto-generated method stub
		return null;
	}

	private String toStringObject(List<SendReservationMenuImport> lstInfo) {
		StringBuilder builder = new StringBuilder();
		for(SendReservationMenuImport data : lstInfo) {
			//half
			builder.append(StringUtils.rightPad(data.getBentoMenu(), 16));
			builder.append(StringUtils.rightPad(data.getUnit(), 2));
		}
		builder.append(StringUtils.rightPad("", 8, " "));
		return builder.toString();
	}

}
