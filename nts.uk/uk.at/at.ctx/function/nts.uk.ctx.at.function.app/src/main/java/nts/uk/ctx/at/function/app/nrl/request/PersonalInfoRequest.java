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
		List<SendPerInfoNameImport> lstPerInfo = sendNRDataAdapter.sendPerInfo(empInfoTerCode,
				context.getTerminal().getContractCode());
		StringBuilder builder = new StringBuilder();
		for (SendPerInfoNameImport infoName : lstPerInfo) {
			builder.append(toStringObject(infoName));
		}
		String payload = Codryptofy.paddingFullBlock(builder.toString());
		byte[] payloadBytes = Codryptofy.decode(payload);
		int length = payloadBytes.length + DefaultValue.DEFAULT_PADDING_LENGTH;
		List<MapItem> items = NRContentList.createDefaultField(Command.PERSONAL_INFO,
				Optional.ofNullable(Integer.toHexString(length)), context.getTerminal());
		// Number of records
		items.add(new MapItem(Element.NUMBER, StringUtils.leftPad(Integer.toHexString(lstPerInfo.size()), 4, "0").toUpperCase()));
		context.collectEncrypt(items, payload);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#responseLength()
	 */
	@Override
	public String responseLength() {
		return "";
	}
	
	private String toStringObject(SendPerInfoNameImport data) {
		StringBuilder builder = new StringBuilder(); 
		builder.append(StringUtils.rightPad(data.getIdNumber(), 20));
		//half payload16
		builder.append(Codryptofy.paddingWithByte(Codryptofy.subStringByByte(data.getPerName(), 20), 20));
		builder.append(StringUtils.rightPad(data.getDepartmentCode(), 10));
		builder.append(StringUtils.rightPad(data.getCompanyCode(), 2));
		builder.append(StringUtils.rightPad(data.getReservation(), 4));
		return builder.toString();
	}

}
