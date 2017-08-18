package nts.uk.ctx.at.shared.ws.grantrelationship;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.grantrelationship.DeleteGrantRelationshipCommand;
import nts.uk.ctx.at.shared.app.command.grantrelationship.DeleteGrantRelationshipCommandHandler;
import nts.uk.ctx.at.shared.app.command.grantrelationship.UpdateGrantRelationshipCommand;
import nts.uk.ctx.at.shared.app.command.grantrelationship.UpdateGrantRelationshipCommandHandler;
import nts.uk.ctx.at.shared.app.command.relationship.InsertRelationshipCommand;
import nts.uk.ctx.at.shared.app.command.relationship.InsertRelationshipCommandHandler;
import nts.uk.ctx.at.shared.app.find.grantrelationship.GrantRelationshipDto;
import nts.uk.ctx.at.shared.app.find.grantrelationship.GrantRelationshipFinder;
@Path("at/shared/grantrelationship")
@Produces("application/json")
public class GrantRelationshipWebService extends WebService{
	@Inject
	private GrantRelationshipFinder finder;
	@Inject
	private InsertRelationshipCommandHandler add;
	@Inject
	private UpdateGrantRelationshipCommandHandler update;
	@Inject
	private DeleteGrantRelationshipCommandHandler delete;
	/**
	 * get all data to list
	 * @return
	 */
	@POST
	@Path("findAll")
	public List<GrantRelationshipDto> finder(){
		return this.finder.finder();
	}
	/**
	 * insert a grant relationship 
	 * @param command
	 */
	@POST
	@Path("add")
	public void insert(InsertRelationshipCommand command){
		this.add.handle(command);
	}
	/**
	 * update grant relationship name
	 * @param command
	 */
	public void update(UpdateGrantRelationshipCommand command){
		this.update.handle(command);
	}
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
