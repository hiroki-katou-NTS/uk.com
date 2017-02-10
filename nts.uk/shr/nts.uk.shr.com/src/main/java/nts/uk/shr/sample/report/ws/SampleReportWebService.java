package nts.uk.shr.sample.report.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.shr.sample.report.file.infra.generator.AsposeSampleReportGenerator;

@Path("/sample/report")
@Produces("application/json")
public class SampleReportWebService {

	@Inject
	private AsposeSampleReportGenerator generator;
	
	@POST
	@Path("generate")
	public FileGeneratorContext generate() {
		
		// send parameters as variable arguments
		return this.generator.start("this is parameter");
	}
}
