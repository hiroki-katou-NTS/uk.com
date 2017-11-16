package nts.uk.ctx.bs.person.ws.groupitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.groupitem.PersonInfoItemGroupDto;
import find.groupitem.PersonInfoItemGroupFinder;
import find.person.info.item.PerInfoItemDefDto;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/groupitem")
@Produces("application/json")

public class GroupItemWebServices extends WebService {

	@Inject
	private PersonInfoItemGroupFinder gItemfinder;
	
	@POST
	@Path("getAll")
	public List<PersonInfoItemGroupDto> getAllGruopItem() {
		return this.gItemfinder.getAllPersonInfoGroup();
	}
	
	
	@POST
	@Path("getById/{id}")
	public PersonInfoItemGroupDto getById(@PathParam("id") String id) {
		return this.gItemfinder.getById(id);
	}
	
	
	@POST
	@Path("getAllItemDf/{groupId}")
	public List<PerInfoItemDefDto> getAllItemDf(@PathParam("groupId") String groupId) {
		return this.gItemfinder.getAllItemDf(groupId);
	}
	
}
