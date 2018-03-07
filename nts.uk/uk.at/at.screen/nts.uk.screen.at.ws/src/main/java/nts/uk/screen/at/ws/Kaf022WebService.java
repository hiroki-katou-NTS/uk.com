package nts.uk.screen.at.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kaf022.command.Kaf022AddCommand;
import nts.uk.screen.at.app.kaf022.command.UpdateKaf022AddCommandHandler;
import nts.uk.screen.at.app.kaf022.find.DtoKaf022;
import nts.uk.screen.at.app.kaf022.find.FinderDtoKaf022;
@Path("screen/at/kaf022")
@Produces("application/json")
public class Kaf022WebService extends WebService{
	@Inject
	private UpdateKaf022AddCommandHandler update;
	
	@Inject 
	private FinderDtoKaf022 finder;
	
	@POST
	@Path("update")
	public void update(Kaf022AddCommand cm){
		this.update.handle(cm);
	}
	
	@POST
	@Path("findAll")
	public DtoKaf022 finder(){
		return this.finder.findDtoKaf022();
	}
}
