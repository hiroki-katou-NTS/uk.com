package nts.uk.ctx.at.shared.ws.era.name;

import javax.ws.rs.Produces;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.era.name.EraNameDeleteCommand;
import nts.uk.ctx.at.shared.app.command.era.name.EraNameDeleteCommandHandler;
import nts.uk.ctx.at.shared.app.command.era.name.EraNameSaveCommand;
import nts.uk.ctx.at.shared.app.command.era.name.EraNameSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.era.name.EraNameFindDto;
import nts.uk.ctx.at.shared.app.find.era.name.EraNameFinder;

@Path("at/shared/eraname")
@Produces(MediaType.APPLICATION_JSON)
public class EraNameWS extends WebService {
	
	@Inject
	private EraNameSaveCommandHandler saveCmdHandler;
	
	@Inject
	private EraNameFinder finder;
	
	@Inject 
	private EraNameDeleteCommandHandler deleteCmdHandler;
	
	@POST
	@Path("getAllEraName")
	public List<EraNameFindDto> getAllEraName() {
		return this.finder.getAllEraNameItem();
	}
	
	@POST
	@Path("getEraNameItem")
	public EraNameFindDto getEraNameItem(String eraNameId) {
		return this.finder.getEraNameItem(eraNameId);
	}
	
	@POST
	@Path("save")
	public void save(EraNameSaveCommand command) {
		this.saveCmdHandler.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(EraNameDeleteCommand command) {
		this.deleteCmdHandler.handle(command);;
	}
	
}