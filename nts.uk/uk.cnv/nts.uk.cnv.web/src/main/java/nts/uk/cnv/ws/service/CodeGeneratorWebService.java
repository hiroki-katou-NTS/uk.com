package nts.uk.cnv.ws.service;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.cnv.app.dto.CodeGeneratorExcecuteDto;
import nts.uk.cnv.app.service.CodeGenerator;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.dom.tabledefinetype.databasetype.DatabaseType;

@Path("cnv/codegenerator")
@Produces("application/json")
public class CodeGeneratorWebService extends WebService{

	@Inject
	private CodeGenerator codeGeneratorService;

	@POST
	@Path("excecute")
	public void excecute(CodeGeneratorExcecuteDto param) {
		ConversionInfo info = new ConversionInfo(
				Enum.valueOf(DatabaseType.class, param.getDbtype()),
				param.getSourceDbName(),
				param.getSourceSchema(),
				param.getTargetDbName(),
				param.getTargetSchema(),
				param.getContractCode()
			);

		this.codeGeneratorService.excecute(info, param.getFilePath());
	}
}
