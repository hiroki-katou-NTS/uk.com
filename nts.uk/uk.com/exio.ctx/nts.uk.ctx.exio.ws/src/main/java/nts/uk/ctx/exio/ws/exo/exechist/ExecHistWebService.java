package nts.uk.ctx.exio.ws.exo.exechist;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exo.category.ExOutCtgDto;
import nts.uk.ctx.exio.app.find.exo.categoryitemdata.CtgItemDataFinder;
import nts.uk.ctx.exio.app.find.exo.exechist.ExecHistDto;
import nts.uk.ctx.exio.app.find.exo.exechist.ExecHistFinder;
import nts.uk.ctx.exio.app.find.exo.exechist.ExecHistResultDto;
import nts.uk.ctx.exio.app.find.exo.exechist.ExecHistSearchParam;
import nts.uk.ctx.exio.app.find.exo.menu.RoleAuthorityDto;

@Path("exio/exo/exechist")
@Produces("application/json")
public class ExecHistWebService {
	@Inject
	private ExecHistFinder execHistFinder;
	
	@Inject
	private CtgItemDataFinder ctgItemDataFinder;

	@POST
	@Path("getExecHist")
	public ExecHistResultDto getExecHist(RoleAuthorityDto param) {
		return execHistFinder.getExecHist(param);
	}

	@POST
	@Path("getExOutExecHistSearch")
	public List<ExecHistDto> getExOutExecHistSearch(ExecHistSearchParam param) {
		return execHistFinder.getExOutExecHistSearch(param);
	}
	
	@POST
	@Path("getCategory")
	public List<ExOutCtgDto> getCategory(RoleAuthorityDto param) {
		return ctgItemDataFinder.getExternalOutputCategoryList(param);
	}
	
}
