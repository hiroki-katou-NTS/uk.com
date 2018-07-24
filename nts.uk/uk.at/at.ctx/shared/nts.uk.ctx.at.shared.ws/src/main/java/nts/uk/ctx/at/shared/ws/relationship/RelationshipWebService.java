package nts.uk.ctx.at.shared.ws.relationship;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.relationship.DeleteRelationshipCommand;
import nts.uk.ctx.at.shared.app.command.relationship.DeleteRelationshipCommandHandler;
import nts.uk.ctx.at.shared.app.command.relationship.InsertRelationshipCommand;
import nts.uk.ctx.at.shared.app.command.relationship.InsertRelationshipCommandHandler;
import nts.uk.ctx.at.shared.app.command.relationship.UpdateRelationshipCommand;
import nts.uk.ctx.at.shared.app.command.relationship.UpdateRelationshipCommandHandler;
import nts.uk.ctx.at.shared.app.find.relationship.RelationshipDto;
import nts.uk.ctx.at.shared.app.find.relationship.RelationshipFinder;

@Path("at/shared/relationship")
@Produces("application/json")
public class RelationshipWebService extends WebService {
	@Inject
	private RelationshipFinder finder;
	@Inject
	private InsertRelationshipCommandHandler add;
	@Inject
	private UpdateRelationshipCommandHandler update;
	@Inject
	private DeleteRelationshipCommandHandler delete;

	/**
	 * get all data to list
	 * 
	 * @return
	 */
	@POST
	@Path("findAll")
	public List<RelationshipDto> finder() {
		return this.finder.finder();
	}

	/**
	 * get all data with setting to list
	 * 
	 * @return
	 */
	@POST
	@Path("findAllWithSetting/{SHENo}")
	public List<RelationshipDto> findAllWithSetting(@PathParam("SHENo") int SHENo) {
		return this.finder.findAllWithSetting(SHENo);
	}

	/**
	 * insert a relationship
	 * 
	 * @param command
	 */
	@POST
	@Path("add")
	public void insert(InsertRelationshipCommand command) {
		this.add.handle(command);
	}

	/**
	 * update relationship name
	 * 
	 * @param command
	 */
	@POST
	@Path("update")
	public void update(UpdateRelationshipCommand command) {
		this.update.handle(command);
	}

	/**
	 * delete relationship
	 * 
	 * @param command
	 */
	@POST
	@Path("delete")
	public void delete(DeleteRelationshipCommand command) {
		this.delete.handle(command);
	}
}
