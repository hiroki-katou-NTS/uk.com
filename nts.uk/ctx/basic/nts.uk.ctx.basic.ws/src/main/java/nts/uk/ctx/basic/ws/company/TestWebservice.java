package nts.uk.ctx.basic.ws.company;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import nts.uk.ctx.basic.app.find.company.TestDto;

@Path("ctx/proto/test")
@Produces("application/json")
public class TestWebservice {
	@POST
	@Path("find")
	public TestDto getTestDto(TestDto testDto) {
		TestDto result = testDto;
		return result;
	}
}