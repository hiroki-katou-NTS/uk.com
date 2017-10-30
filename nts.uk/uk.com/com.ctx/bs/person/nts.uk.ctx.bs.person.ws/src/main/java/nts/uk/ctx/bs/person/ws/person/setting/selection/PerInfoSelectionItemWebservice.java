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
import command.person.setting.selectionitem.selection.AddSelectionCommand;
import command.person.setting.selectionitem.selection.AddSelectionCommandHandler;
import command.person.setting.selectionitem.selection.AddSelectionHistoryCommand;
import command.person.setting.selectionitem.selection.AddSelectionHistoryCommandHandler;
import command.person.setting.selectionitem.selection.RemoveSelectionCommand;
import command.person.setting.selectionitem.selection.RemoveSelectionCommandHandler;
import command.person.setting.selectionitem.selection.UpdateSelectionCommand;
import command.person.setting.selectionitem.selection.UpdateSelectionCommandHandler;
import find.person.setting.selectionitem.PerInfoHistorySelectionDto;
import find.person.setting.selectionitem.PerInfoHistorySelectionFinder;
import find.person.setting.selectionitem.PerInfoSelectionItemDto;
import find.person.setting.selectionitem.PerInfoSelectionItemFinder;
import find.person.setting.selectionitem.selection.SelectionFinder;
import find.person.setting.selectionitem.selection.SelectionItemOrderDto;
import find.person.setting.selectionitem.selection.SelectionItemOrderFinder;
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

	@Inject
	private SelectionFinder selecFider;

	@Inject
	private SelectionItemOrderFinder selecItemOrderFider;

	// Add selection:
	@Inject
	private AddSelectionCommandHandler addSelectionCommandHandler;

	// update Selection:
	@Inject
	private UpdateSelectionCommandHandler updateSelection;

	// Remove Selection
	@Inject
	private RemoveSelectionCommandHandler removeSelection;

	// add history data: screen C:
	AddSelectionHistoryCommandHandler addHistory;

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

	@POST
	@Path("findAllSelection/{histId}")
	public List<SelectionItemOrderDto> getAllItemOrderSelection(@PathParam("histId") String histId) {
		return this.selecFider.getHistIdSelection(histId);
	}

	// Addselection:
	@POST
	@Path("addSelection")
	public void AddSelection(AddSelectionCommand command) {
		this.addSelectionCommandHandler.handle(command);

	}

	// Update Selection:
	@POST
	@Path("updateSelection")
	public void UpdateSelection(UpdateSelectionCommand command) {
		this.updateSelection.handle(command);
	}

	// remove Selection
	@POST
	@Path("removeSelection")
	public void removeSelection(RemoveSelectionCommand command) {
		this.removeSelection.handle(command);
	}

	// Order setting
	@POST
	@Path("OrderSetting/{histId}")
	public List<SelectionItemOrderDto> getAllOrderSetting(@PathParam("histId") String histId) {
		return this.selecFider.getHistIdSelection(histId);
	}

	// add history data Screen C:
	@POST
	@Path("addHistoryData")
	public void AddHistoryData(AddSelectionHistoryCommand command) {
		this.addHistory.handle(command);

	}
}
