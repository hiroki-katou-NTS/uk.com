package nts.uk.ctx.pereg.ws.groupitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.find.layout.groupitem.PersonInfoItemGroupDto;
import nts.uk.ctx.pereg.app.find.layout.groupitem.PersonInfoItemGroupFinder;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;

@Path("ctx/pereg/person/groupitem")
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
	
	@POST
	@Path("findby/listgroupId")
	public List<PerInfoItemDefDto> getAllItemDfFromLstGroup(List<String> groupIds) {
		return this.gItemfinder.getAllItemDfFromListGroup(groupIds);
	}
	
}
