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
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendReasonApplicationImport;

/**
 * @author ThanhNX
 *
 *申請理由リクエスト
 */
@RequestScoped
@Named(Command.APPLICATION_INFO)
public class ApplicationReasonRequest extends NRLRequest<Frame>{

	@Inject
	private SendNRDataAdapter sendNRDataAdapter;
	
	@Override
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {
		List<SendReasonApplicationImport> lstInfo = sendNRDataAdapter.sendReasonApp(empInfoTerCode, context.getTerminal().getContractCode());
		StringBuilder builder = new StringBuilder();
		for(SendReasonApplicationImport infoName : lstInfo) {
			builder.append(toStringObject(infoName));
		}
		String payload = Codryptofy.paddingFullBlock(builder.toString());
		byte[] payloadBytes = Codryptofy.decode(payload);
		int length =  payloadBytes.length + DefaultValue.DEFAULT_PADDING_LENGTH;
		List<MapItem> items = NRContentList.createDefaultField(Command.APPLICATION_INFO,
				Optional.ofNullable(Integer.toHexString(length)), context.getTerminal());
		//Number of records
		items.add(new MapItem(Element.NUMBER, StringUtils.leftPad(Integer.toHexString(lstInfo.size()), 4, "0").toUpperCase()));
		context.collectEncrypt(items, payload);
	}

	private String toStringObject(SendReasonApplicationImport data) {
		StringBuilder builder = new StringBuilder(); 
		builder.append(StringUtils.rightPad(data.getAppReasonNo(), 2));
		//half payload16
		builder.append(Codryptofy.paddingWithByte(data.getAppReasonName(), 40));
		return builder.toString();
	}
	
	@Override
	public String responseLength() {
		return null;
	}

}
