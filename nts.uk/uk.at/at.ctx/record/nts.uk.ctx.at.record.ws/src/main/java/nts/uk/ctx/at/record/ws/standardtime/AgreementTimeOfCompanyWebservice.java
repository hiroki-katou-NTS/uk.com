package nts.uk.ctx.at.record.ws.standardtime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

//import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.standardtime.company.AddAgreementTimeOfCompanyCommand;
import nts.uk.ctx.at.record.app.command.standardtime.company.AddAgreementTimeOfCompanyCommandHandler;
import nts.uk.ctx.at.record.app.command.standardtime.company.UpdateAgreementTimeOfCompanyCommand;
import nts.uk.ctx.at.record.app.command.standardtime.company.UpdateAgreementTimeOfCompanyCommandHandler;
import nts.uk.ctx.at.record.app.find.standardtime.AgreementTimeOfCompanyFinder;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementTimeOfCompanyDto;

@Path("at/record/agreementTimeOfCompany")
@Produces("application/json")
public class AgreementTimeOfCompanyWebservice extends WebService {
	
	@Inject
	private AgreementTimeOfCompanyFinder agreementTimeOfCompanyFinder;
	
	@Inject
	private AddAgreementTimeOfCompanyCommandHandler addAgreementTimeOfCompanyCommandHandler;
	
	@Inject
	private UpdateAgreementTimeOfCompanyCommandHandler updateAgreementTimeOfCompanyCommandHandler;

	@POST
	@Path("getAgreementTimeOfCompany/{laborSystemAtr}")
	public AgreementTimeOfCompanyDto getAgreementTimeOfCompany(@PathParam("laborSystemAtr") int laborSystemAtr) {
		return this.agreementTimeOfCompanyFinder.findAll(laborSystemAtr);
	}
	
	@POST
	@Path("addAgreementTimeOfCompany")
	public List<String> addAgreementTimeOfCompany(AddAgreementTimeOfCompanyCommand command) {
		return this.addAgreementTimeOfCompanyCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateAgreementTimeOfCompany")
	public List<String> updateFormulaMaster(UpdateAgreementTimeOfCompanyCommand command) {
		return this.updateAgreementTimeOfCompanyCommandHandler.handle(command);
	}
}
