package nts.uk.ctx.exio.ws.exi.codeconvert;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exi.codeconvert.AcceptCdConvertCommand;
import nts.uk.ctx.exio.app.command.exi.codeconvert.AddAcceptCdConvertCommandHandler;
import nts.uk.ctx.exio.app.command.exi.codeconvert.RemoveAcceptCdConvertCommandHandler;
import nts.uk.ctx.exio.app.command.exi.codeconvert.UpdateAcceptCdConvertCommandHandler;
import nts.uk.ctx.exio.app.find.exi.codeconvert.AcceptCdConvertDto;
import nts.uk.ctx.exio.app.find.exi.codeconvert.AcceptCdConvertFinder;

@Path("exio/exi/codeconvert")
@Produces("application/json")
public class AcceptCdConvertWebService extends WebService {

	@Inject
	private AddAcceptCdConvertCommandHandler addHandler;

	@Inject
	private UpdateAcceptCdConvertCommandHandler updateHandler;

	@Inject
	private RemoveAcceptCdConvertCommandHandler removeHandler;

	@Inject
	private AcceptCdConvertFinder codeConvertFinder;

	@POST
	@Path("getCodeConvertByCompanyId")
	public List<AcceptCdConvertDto> getCodeConvertByCompanyId() {
		return this.codeConvertFinder.getAcceptCdConvertByCompanyId();
	}

	@POST
	@Path("getAcceptCodeConvert/{convertCode}")
	public AcceptCdConvertDto getAcceptCodeConvert(@PathParam("convertCode") String convertCd) {
		return this.codeConvertFinder.getAcceptCdConvertById(convertCd);
	}

	@POST
	@Path("addAcceptCodeConvert")
	public void addAcceptCodeConvert(AcceptCdConvertCommand command) {
		this.addHandler.handle(command);
	}

	@POST
	@Path("updateAcceptCodeConvert")
	public void updateAcceptCodeConvert(AcceptCdConvertCommand command) {
		this.updateHandler.handle(command);
	}

	@POST
	@Path("removeAcceptCodeConvert")
	public void removeAcceptCodeConvert(AcceptCdConvertCommand command) {
		this.removeHandler.handle(command);
	}

}
