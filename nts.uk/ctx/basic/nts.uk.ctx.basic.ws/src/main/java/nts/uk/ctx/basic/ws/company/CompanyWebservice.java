//package nts.uk.ctx.basic.ws.company;
//
//import java.util.List;
//import java.util.Optional;
//
//import javax.inject.Inject;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//
//import com.fasterxml.jackson.databind.util.RawValue;
//
//import nts.arc.layer.ws.WebService;
//import nts.uk.ctx.basic.app.command.company.AddCompanyCommandHandler;
//import nts.uk.ctx.basic.app.command.company.DeleteCompanyCommand;
//import nts.uk.ctx.basic.app.command.company.DeleteCompanyCommandHandler;
//import nts.uk.ctx.basic.app.command.company.UpdateCompanyCommandHandler;
//import nts.uk.ctx.basic.app.find.company.CompanyDto;
//import nts.uk.ctx.basic.app.find.company.CompanyFinder;
//
///**
// * 
// * @author lanlt
// *
// */
//@Path("ctx/proto/company")
//@Produces("application/json")
//public class CompanyWebservice extends WebService{
//	@Inject
//	private AddCompanyCommandHandler addCompany;
//	@Inject
//	private DeleteCompanyCommandHandler deleteCompany;
//	@Inject
//	private UpdateCompanyCommandHandler updateCompany;
//	@Inject
//	private CompanyFinder finder;
//	@POST
//	@Path("findallcompany")
//	public List<CompanyDto> getAllCompanys(){
//		
//		return this.finder.getAllCompanys();
//		
//	}
//	@POST
//	@Path("findacompany")
//	public CompanyDto getCompanyDetail(String companyCode){
//	}
//	public void deleteData(){
//		this.finder.delete(companyCode);
//	}
//	public void updateData(){
//		
//	}
//}
