package nts.uk.file.com.ws.person.matrix;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.file.com.app.person.matrix.PersonInfoMatrixExportService;
import nts.uk.file.com.app.person.matrix.datasource.PersonInfoMatrixDataSource;

@Path("/person/matrix/report")
@Produces("application/json") 
public class PersonInfoMatrixWebservice {
	@Inject
	private PersonInfoMatrixExportService matrixService;
	@POST
	@Path("printMatrix")
	public ExportServiceResult generate(PersonInfoMatrixDataSource query) {

		return this.matrixService.start(query);
	}
	
}
