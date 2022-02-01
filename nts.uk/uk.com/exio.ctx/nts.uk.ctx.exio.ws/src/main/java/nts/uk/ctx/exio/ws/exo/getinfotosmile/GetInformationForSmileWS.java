package nts.uk.ctx.exio.ws.exo.getinfotosmile;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.workrule.closure.ClosureFinder;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosuresInfoDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.SmileClosureTime;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.SmileEmpClosure;
import nts.uk.ctx.bs.company.app.find.company.CompanyInforFinder;
import nts.uk.ctx.sys.gateway.app.find.tenantlogin.TenanLoginFinder;

@Path("exio/exo/getinforsmile")
@Produces("application/json")
public class GetInformationForSmileWS extends WebService{
	@Inject
	private ClosureFinder finder;
	
	@Inject
	private CompanyInforFinder finderComFinder;	
	
	@Inject
	private TenanLoginFinder tenanFinder;
	
	@Inject
	private ClosureFinder closureFinder;
	
	@POST
	@Path("get-current-closure-by-cid/{Cid}")
	public List<ClosuresInfoDto> getClosureByCid(@PathParam("Cid") String Cid) {
		List<ClosuresInfoDto> listClosure = finder.findClosureByCid(Cid);
		return listClosure;
	}
	
	@POST
	@Path("checkcom/{companyId}")
	public boolean checkCom(@PathParam("companyId") String companyId) {
		return this.finderComFinder.companyCheck(companyId);
	}
	
	@POST
	@Path("check/{contractCode}")
	public boolean check(@PathParam("contractCode") String contractCode) {
		return this.tenanFinder.checkExist(contractCode);
	}
	
	@POST
	@Path("getemployment-relate-closure/{companyId}")
	public List<SmileEmpClosure> getEmploymentClosure(@PathParam("companyId") String companyId) {
		return this.closureFinder.getEmpCloSmile(companyId);
	}
	
	@POST
	@Path("geclosure-time")
	public SmileClosureTime getEmploymentClosure(ClosureTimeParam param) {
		return this.closureFinder.getTimeSmile(param.getCompanyId(), param.getClosureId(), param.getStartYM());
	}
}
