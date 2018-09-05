package nts.uk.ctx.exio.ws.qmm.taxamount;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.find.qmm.taxexemptlimit.TaxExemptLimitDto;
import nts.uk.ctx.exio.app.find.qmm.taxexemptlimit.TaxExemptLimitFinder;

/**
 * 
 * @author thanh.tq
 *
 */
@Path("exio/qmm/taxamount")
@Produces("application/json")
public class TaxAmount extends WebService {

	@Inject
	private TaxExemptLimitFinder taxExemptLimitFinder;

	@POST
	@Path("getAllTaxAmountByCompanyId")
	public List<TaxExemptLimitDto> getCodeConvertByCompanyId() {
		return this.taxExemptLimitFinder.getTaxExemptLimitByCompanyId();
	}
}
