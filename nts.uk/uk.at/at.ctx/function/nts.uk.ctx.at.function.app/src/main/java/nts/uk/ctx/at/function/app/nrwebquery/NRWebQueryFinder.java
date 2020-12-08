package nts.uk.ctx.at.function.app.nrwebquery;

import javax.ws.rs.core.Response;

import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;

public interface NRWebQueryFinder {
	public Response process(NRWebQuerySidDateParameter queryParam);
}
