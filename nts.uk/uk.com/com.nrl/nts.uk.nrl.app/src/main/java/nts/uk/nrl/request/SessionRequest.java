package nts.uk.nrl.request;

import javax.enterprise.context.RequestScoped;

import nts.uk.nrl.Command;
import nts.uk.nrl.xml.Frame;

/**
 * Session request.
 * 
 * @author manhnd
 */
@RequestScoped
@Named(Command.SESSION)
public class SessionRequest extends NRLRequest<Frame> {

	/* (non-Javadoc)
	 * @see nts.uk.nrl.request.NRLRequest#sketch(nts.uk.nrl.request.ResourceContext)
	 */
	@Override
	public void sketch(ResourceContext<Frame> context) {
		// TODO: Clear flags
		context.responseAccept();
	}

	/* (non-Javadoc)
	 * @see nts.uk.nrl.request.NRLRequest#responseLength()
	 */
	@Override
	public String responseLength() {
		return null;
	}

}
