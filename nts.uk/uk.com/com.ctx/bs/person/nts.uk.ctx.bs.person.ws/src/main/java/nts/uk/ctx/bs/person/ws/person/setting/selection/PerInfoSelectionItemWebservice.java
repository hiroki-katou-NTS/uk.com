package nts.uk.ctx.bs.person.ws.person.setting.selection;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import command.person.setting.selectionitem.AddSelectionItemCommand;
import command.person.setting.selectionitem.AddSelectionItemCommandHandler;
import command.person.setting.selectionitem.RemoveSelectionItemCommand;
import command.person.setting.selectionitem.RemoveSelectionItemCommandHandler;
import command.person.setting.selectionitem.UpdateSelectionItemCommand;
import command.person.setting.selectionitem.UpdateSelectionItemCommandHandler;
import find.person.setting.selectionitem.PerInfoHistorySelectionDto;
import find.person.setting.selectionitem.PerInfoHistorySelectionFinder;
import find.person.setting.selectionitem.PerInfoSelectionItemDto;
import find.person.setting.selectionitem.PerInfoSelectionItemFinder;
import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/info/setting/selection")
@Produces("application/json")
public class PerInfoSelectionItemWebservice extends WebService {
	@Inject
	private PerInfoSelectionItemFinder finder;

	@Inject
	private AddSelectionItemCommandHandler addCommandHandler;

	@Inject
	private UpdateSelectionItemCommandHandler updateCommandHandler;

	@Inject
	private RemoveSelectionItemCommandHandler removeCommandHandler;

	// history:
	@Inject
	private PerInfoHistorySelectionFinder hisFinder;

	@POST
	@Path("findAll")
	public List<PerInfoSelectionItemDto> getAllPerInfoSelectionItem() {
		return this.finder.getAllPerInfoSelectionItem();
	}

	@POST
	@Path("findItem/{selectionItemId}")
	public PerInfoSelectionItemDto getPerInfoSelectionItem(@PathParam("selectionItemId") String selectionItemId) {
		return this.finder.getPerInfoSelectionItem(selectionItemId);
	}

	@POST
	@Path("addSelectionItem")
	public JavaTypeResult<String> addSelectionItem(AddSelectionItemCommand command) {
		return new JavaTypeResult<String>(this.addCommandHandler.handle(command));
	}

	@POST
	@Path("updateSelectionItem")
	public void updateSelectionItem(UpdateSelectionItemCommand command) {
		this.updateCommandHandler.handle(command);
	}

	@POST
	@Path("removeSelectionItem")
	public void removeSelectionItem(RemoveSelectionItemCommand command) {
		this.removeCommandHandler.handle(command);
	}

	// history:
	@POST
	@Path("findAllHistSelection/{selectedId}")
	public List<PerInfoHistorySelectionDto> getAllPerInfoHistorySelection(@PathParam("selectedId") String selectedId) {
		return this.hisFinder.historySelection(selectedId);
	}

}
