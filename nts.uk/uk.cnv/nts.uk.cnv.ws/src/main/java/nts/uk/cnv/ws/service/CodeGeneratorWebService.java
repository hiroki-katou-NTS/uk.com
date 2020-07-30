package nts.uk.cnv.ws.service;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.cnv.app.service.CodeGeneratorService;
import nts.uk.cnv.dom.databasetype.DatabaseType;
import nts.uk.cnv.dom.service.ConversionInfo;

@Path("cnv/codegenerator")
@Produces("application/json")
public class CodeGeneratorWebService {

	@Inject
	private CodeGeneratorService codeGeneratorService;
	
	@POST
	@Path("excecute")
	public String excecute(String dbtype, String sourceDbName, String sourceSchema,
			 String targetDbName, String targetSchema, String contractCode) {
		ConversionInfo info = new ConversionInfo(
				Enum.valueOf(DatabaseType.class, dbtype),
				sourceDbName,
				sourceSchema,
				targetDbName,
				targetSchema,
				contractCode
			);
		
		return this.codeGeneratorService.excecute(info);
	}
}
