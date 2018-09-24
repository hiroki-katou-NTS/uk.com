package nts.uk.ctx.pr.core.ws.wageprovision.statementitem.taxamount;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.TaxExemptLimitFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.TaxExemptionLimitDto;

/**
 * 
 * @author thanh.tq
 *
 */
@Path("ctx/pr/core/taxamount")
@Produces("application/json")
public class TaxAmountWebService extends WebService {

	@Inject
	private TaxExemptLimitFinder taxExemptLimitFinder;

	@POST
	@Path("getAllTaxAmountByCompanyId")
	public List<TaxExemptionLimitDto> getCodeConvertByCompanyId() {
		return this.taxExemptLimitFinder.getTaxExemptLimitByCompanyId();
	}
}
