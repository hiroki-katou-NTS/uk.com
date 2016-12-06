package nts.uk.pr.file.ws.company;

import java.util.Arrays;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.pr.file.infra.company.CompanyPrintFileGenerator;

@Path("/file/company")
@Produces("application/json")
public class CompanyPrintWebservice {

	@Inject
	private CompanyPrintFileGenerator companyPrint;

	@POST
	@Path("print")
	public String print() {
		return this.companyPrint.start(Arrays.asList("1")).getTask();
	}
}
