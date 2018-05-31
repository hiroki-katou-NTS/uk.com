package nts.uk.ctx.at.function.ws.monthlycorrection.fixedformatmonthly;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly.AddMonPfmCorrectionFormatCmdHandler;
import nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly.DeleteMonPfmCorrectionFormatCmd;
import nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly.DeleteMonPfmCorrectionFormatCmdHandler;
import nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatCmd;
import nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly.UpdateMonPfmCorrectionFormatCmdHandler;
import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatDto;
import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatFinder;
@Path("at/function/monthlycorrection/kdw008a")
@Produces(MediaType.APPLICATION_JSON)
public class MonPfmCorrectionFormatWS extends WebService {

	@Inject
	private MonPfmCorrectionFormatFinder finder;
	
	@Inject
	private AddMonPfmCorrectionFormatCmdHandler addHandler;
	
	@Inject
	private UpdateMonPfmCorrectionFormatCmdHandler updateHandler;
	
	@Inject
	private DeleteMonPfmCorrectionFormatCmdHandler deleteHandler;
	
	@POST
	@Path("findall")
	public List<MonPfmCorrectionFormatDto> findAll(){
		List<MonPfmCorrectionFormatDto> data = this.finder.getAllMonPfmCorrectionFormat();
		return data;
	}
	
	@POST
	@Path("findbycode/{monthlyPfmFormatCode}")
	public MonPfmCorrectionFormatDto findByCode(@PathParam("monthlyPfmFormatCode") String monthlyPfmFormatCode){
		MonPfmCorrectionFormatDto data =  this.finder.getMonPfmCorrectionFormat(monthlyPfmFormatCode);
		return data;
	}
	
	@POST
	@Path("add")
	public void addMonPfmCorrectionFormat(MonPfmCorrectionFormatCmd command){
		this.addHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public void updateMonPfmCorrectionFormat(MonPfmCorrectionFormatCmd command){
		this.updateHandler.handle(command);
	}
	
	@POST
	@Path("delete")
	public void deleteMonPfmCorrectionFormat(DeleteMonPfmCorrectionFormatCmd command){
		this.deleteHandler.handle(command);
	}
}
