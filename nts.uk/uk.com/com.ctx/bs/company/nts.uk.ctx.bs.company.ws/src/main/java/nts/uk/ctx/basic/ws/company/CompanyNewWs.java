package nts.uk.ctx.basic.ws.company;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.find.CompanyInforDto;
import nts.uk.ctx.find.CompanyInforFinder;

@Path("bs/company")
@Produces("application/json")
public class CompanyNewWs extends WebService {
	@Inject
	private CompanyInforFinder finderComFinder;

	// @Inject
	// private UpdateCompanyInforCommandHandler updateCom;
	//
	// @Inject
	// private AddCompanyInforCommandHandler addCom;
	//
	// @Inject
	// private DeleteCompanyInforCommandHandler delCom;

	/**
	 * find all company
	 * 
	 * @return
	 */
	@POST 
	@Path("findCom") 
	public List<CompanyInforDto> finderCom() {
		return this.finderComFinder.finder();
	}

	/**
	 * find company by id
	 * 
	 * @return
	 */
	@POST
	@Path("findComId/{companyId}")
	public CompanyInforDto findComId(@PathParam("companyId") String companyId) {
		return this.finderComFinder.findId(companyId).orElse(null);
	}

	// /**
	// * update a company
	// * @param com
	// */
	// @POST
	// @Path("updateCom")
	// public void update(UpdateCompanyInforCommand com){
	// this.updateCom.handle(com);
	// }
	//
	// /**
	// * insert a company
	// * @param com
	// */
	// @POST
	// @Path("addCom")
	// public void add(AddCompanyInforCommand com){
	// this.addCom.handle(com);
	// }
	//
	// /**
	// * delete a company
	// * @param com
	// */
	// @POST
	// @Path("deleteCom")
	// public void delete(DeleteCompanyInforCommand com){
	// this.delCom.handle(com);
	// }

}
