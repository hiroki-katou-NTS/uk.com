package nts.uk.nrl.request;

import javax.enterprise.context.RequestScoped;

import nts.uk.nrl.Command;
import nts.uk.nrl.xml.Frame;

/**
 * Test request.
 * 
 * @author manhnd
 */
@RequestScoped
@Named(Command.TEST)
public class TestRequest extends NRLRequest<Frame> {
	
	/* (non-Javadoc)
	 * @see nts.uk.nrl.request.NRLRequest#sketch(nts.uk.nrl.request.ResourceContext)
	 */
	@Override
	public void sketch(ResourceContext<Frame> context) {
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
