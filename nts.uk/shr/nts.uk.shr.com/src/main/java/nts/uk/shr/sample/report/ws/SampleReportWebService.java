package nts.uk.shr.sample.report.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.shr.sample.report.infra.SampleReportGenerator;

@Path("/sample/report")
@Produces("application/json")
public class SampleReportWebService {

	@Inject
	private SampleReportGenerator generator;
	
	@POST
	@Path("generate")
	public SampleReportResult generate() {
		return new SampleReportResult(this.generator.start("this is parameter").getTaskId());
	}
}
