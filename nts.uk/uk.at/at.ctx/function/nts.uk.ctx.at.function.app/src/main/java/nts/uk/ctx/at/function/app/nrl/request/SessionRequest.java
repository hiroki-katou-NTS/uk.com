package nts.uk.ctx.at.function.app.nrl.request;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.DeleteRequestSettingTRAdapter;

/**
 * Session request.
 * 
 * 削除リクエスト
 * @author manhnd
 */
@RequestScoped
@Named(Command.SESSION)
public class SessionRequest extends NRLRequest<Frame> {

	@Inject
	private DeleteRequestSettingTRAdapter deleteRequestSettingTRAdapter;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#sketch(nts.uk.ctx.at.function.app.nrl.request.ResourceContext)
	 */
	@Override
	public void sketch(String empInfoTerCode, ResourceContext<Frame> context) {
		// Clear flags
		deleteRequestSettingTRAdapter.remove(empInfoTerCode, context.getEntity().pickItem(Element.CONTRACT_CODE)).run();
		context.responseAccept();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.request.NRLRequest#responseLength()
	 */
	@Override
	public String responseLength() {
		return null;
	}

}
