package nts.uk.ctx.at.function.app.nrl.request;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.NRContentList;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;

/**
 * @author thanh_nx
 *
 *         再起動リクエスト
 */
@RequestScoped
@Named(Command.REBOOT)
public class RebootRequest extends NRLRequest<Frame> {

	@Override
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {
		List<MapItem> items = NRContentList.createDefaultField(Command.REBOOT,
				Optional.ofNullable(responseLength()), context.getTerminal());
		context.collect(items);
	}

	@Override
	public String responseLength() {
		return Element.Value.ACCEPT_RES_LEN;
	}

}
