package nts.uk.cnv.ws.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.cnv.app.command.RegistConversionCategoryCommand;
import nts.uk.cnv.app.command.RegistConversionCategoryCommandHandler;

@Path("cnv/conversiontable")
@Produces("application/json")
public class ConversionTableWebService extends WebService {

	@Inject
	RegistConversionCategoryCommandHandler regstCategoryHandler;

	@POST
	@Path("category/regist")
	public void registCategory(String name, List<String> tables) {
		RegistConversionCategoryCommand command = new RegistConversionCategoryCommand(name, tables);
		regstCategoryHandler.handle(command);
	}
}
