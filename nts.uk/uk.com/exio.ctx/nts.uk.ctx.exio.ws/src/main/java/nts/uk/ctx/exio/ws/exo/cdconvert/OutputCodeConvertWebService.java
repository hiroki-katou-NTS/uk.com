package nts.uk.ctx.exio.ws.exo.cdconvert;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exo.cdconvert.AddOutputCodeConvertCommandHandler;
import nts.uk.ctx.exio.app.command.exo.cdconvert.OutputCodeConvertCommand;
import nts.uk.ctx.exio.app.command.exo.cdconvert.RemoveOutputCodeConvertCommandHandler;
import nts.uk.ctx.exio.app.command.exo.cdconvert.UpdateOutputCodeConvertCommandHandler;
import nts.uk.ctx.exio.app.find.exo.cdconvert.OutputCodeConvertDTO;
import nts.uk.ctx.exio.app.find.exo.cdconvert.OutputCodeConvertFinder;

@Path("exio/exo/codeconvert")
@Produces("application/json")
public class OutputCodeConvertWebService extends WebService {
	
	@Inject
	private AddOutputCodeConvertCommandHandler addHandler;
	
	@Inject
	private UpdateOutputCodeConvertCommandHandler updateHandler;
	
	@Inject
	private RemoveOutputCodeConvertCommandHandler removeHandler;

	@Inject
	private OutputCodeConvertFinder outputCodeConvertFinder;

	@POST
	@Path("getOutputCodeConvertByCompanyId")
	public List<OutputCodeConvertDTO> getOutputCodeConvertByCid() {
		return this.outputCodeConvertFinder.getOutputCodeConvertByCid();
	}
	
	@POST
	@Path("getOutputCodeConvertByConvertCode/{convertCode}")
	public OutputCodeConvertDTO getOutputCodeConvertByConvertCode(@PathParam("convertCode") String convertCode) {
		return this.outputCodeConvertFinder.getOutputCodeConvertByConvertCode(convertCode);
	}
	
	@POST
	@Path("addOutputCodeConvert")
	public void addOutputCodeConvert(OutputCodeConvertCommand command){
		this.addHandler.handle(command);
	}
	
	@POST
	@Path("updateOutputCodeConvert")
	public void updateOutputCodeConvert(OutputCodeConvertCommand command){
		this.updateHandler.handle(command);
	}
	
	@POST
	@Path("removeOutputCodeConvert")
	public void removeOutputCodeConvert(OutputCodeConvertCommand command){
		this.removeHandler.handle(command);
	}	
	
	@POST
	@Path("checkBeforeRemove/{convertCode}")
	public void checkBeforeRemove(@PathParam("convertCode") String convertCode) {
		this.outputCodeConvertFinder.checkBeforeRemove(convertCode);
	}
}
