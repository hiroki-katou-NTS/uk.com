package nts.uk.ctx.bs.person.ws.person.setting.selection;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.person.setting.selection.PerInfoSelectionItemDto;
import find.person.setting.selection.PerInfoSelectionItemFinder;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/info/setting/selection")
@Produces("application/json")
public class PerInfoSelectionItemWebservice extends WebService {
	@Inject
	private PerInfoSelectionItemFinder finder;
	
	
	@POST
	@Path("findAll")
	public List<PerInfoSelectionItemDto> getAllPerInfoSelectionItem() {
		return this.finder.getAllPerInfoSelectionItem();
	}
	
	@POST
	@Path("findItem/{selectionItemId}")
	public PerInfoSelectionItemDto getPerInfoSelectionItem(@PathParam("selectionItemId") String selectionItemId){
		return this.finder.getPerInfoSelectionItem(selectionItemId);
	}
	
}
