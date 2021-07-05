package nts.uk.ctx.at.function.app.nrl.request;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.DefaultValue;
import nts.uk.ctx.at.function.app.nrl.NRContentList;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
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
		List<SendReservationMenuImport> lstInfo = sendNRDataAdapter.sendReservMenu(empInfoTerCode,
				context.getTerminal().getContractCode());
		String payload = Codryptofy.paddingFullBlock(toStringObject(lstInfo));
		byte[] payloadBytes = Codryptofy.decode(payload);
		int length = payloadBytes.length + 42;
		List<MapItem> items = NRContentList.createDefaultField(Command.RESERVATION_INFO,
				Optional.ofNullable(Integer.toHexString(length)), context.getTerminal(), DefaultValue.ZERO_PADDING);
		// Number of records
		context.collectEncrypt(items, payload);

	}

	@Override
	public String responseLength() {
		return "";
	}

	private String toStringObject(List<SendReservationMenuImport> lstInfo) {
		StringBuilder builder = new StringBuilder();
		for(SendReservationMenuImport data : lstInfo) {
			//half
			builder.append(Codryptofy.paddingWithByte(data.getBentoMenu(), 16));
			builder.append(Codryptofy.paddingWithByte(data.getUnit(), 2));
		}
		return builder.toString();
	}
}
