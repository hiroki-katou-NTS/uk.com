package nts.uk.ctx.at.shared.ws.grantrelationship;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.specialholiday.grantrelationship.DeleteGrantRelationshipCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.grantrelationship.DeleteGrantRelationshipCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.grantrelationship.InsertGrantRelationshipCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.grantrelationship.InsertGrantRelationshipCommandHandler;
import nts.uk.ctx.at.shared.app.find.specialholiday.grantrelationship.GrantRelationshipDto;
import nts.uk.ctx.at.shared.app.find.specialholiday.grantrelationship.GrantRelationshipFinder;
import nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.repository.GrantRelationshipRepository;
@Path("at/shared/grantrelationship")
@Produces("application/json")
public class GrantRelationshipWebService extends WebService{
	@Inject
	private GrantRelationshipFinder finder;
	@Inject
	private InsertGrantRelationshipCommandHandler add;
/*	@Inject
	private UpdateGrantRelationshipCommandHandler update;*/
	@Inject
	private DeleteGrantRelationshipCommandHandler delete;
	/**
	 * get all data to list
	 * @return
	 */
	@POST
	@Path("findAll/{sphdCode}")
	public List<GrantRelationshipDto> finder(@PathParam ("sphdCode") String sphdCode){
		return this.finder.finder(sphdCode);
	}
	
	/**
	 * insert a grant relationship 
	 * @param command
	 */
	@POST
	@Path("add")
	public void insert(InsertGrantRelationshipCommand command){
		this.add.handle(command);
	}
	/**
	 * update grant relationship name
	 * @param command
	 */
/*	@POST
	@Path("update")
	public void update(UpdateGrantRelationshipCommand command){
		this.update.handle(command);
	}*/
	/**
	 * delete grant relationship
	 * @param command
	 */
	@POST
	@Path("delete")
	public void delete(DeleteGrantRelationshipCommand command){
		this.delete.handle(command);
	}
}
