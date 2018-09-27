package nts.uk.ctx.pereg.ws.person.setting.selection;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.history.add.AddSelectionHistoryCommand;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.history.add.AddSelectionHistoryCommandHandler;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.history.edit.EditHistoryCommand;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.history.edit.EditHistoryCommandHandler;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.history.remove.RemoveHistoryCommand;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.history.remove.RemoveHistoryCommandHandler;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.refcompany.ReflUnrCompCommand;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.refcompany.ReflUnrCompCommandHandler;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection.add.AddSelectionCommand;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection.add.AddSelectionCommandHandler;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection.remove.RemoveSelectionCommand;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection.remove.RemoveSelectionCommandHandler;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection.update.UpdateSelectionCommand;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection.update.UpdateSelectionCommandHandler;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selectionitem.add.AddSelectionItemCommand;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selectionitem.add.AddSelectionItemCommandHandler;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selectionitem.remove.RemoveSelectionItemCommand;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selectionitem.remove.RemoveSelectionItemCommandHandler;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selectionitem.update.UpdateSelectionItemCommand;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selectionitem.update.UpdateSelectionItemCommandHandler;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selectionorder.update.UpdateSelOrderCommand;
import nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selectionorder.update.UpdateSelOrderCommandHandler;
import nts.uk.ctx.pereg.app.find.person.setting.init.item.SelectionInitDto;
import nts.uk.ctx.pereg.app.find.person.setting.selectionitem.PerInfoHistorySelectionDto;
import nts.uk.ctx.pereg.app.find.person.setting.selectionitem.PerInfoHistorySelectionFinder;
import nts.uk.ctx.pereg.app.find.person.setting.selectionitem.PerInfoSelectionItemDto;
import nts.uk.ctx.pereg.app.find.person.setting.selectionitem.PerInfoSelectionItemFinder;
import nts.uk.ctx.pereg.app.find.person.setting.selectionitem.selection.SelectionFinder;
import nts.uk.ctx.pereg.app.find.person.setting.selectionitem.selection.SelectionInitQuery;
import nts.uk.ctx.pereg.app.find.person.setting.selectionitem.selection.SelectionItemOrderDto;
import nts.uk.ctx.pereg.app.find.person.setting.selectionitem.selection.SelectionQuery;
import nts.uk.shr.pereg.app.ComboBoxObject;

@Path("ctx/pereg/person/info/setting/selection")
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
	@Inject
	private AddSelectionHistoryCommandHandler addHistory;

	// Edit History:
	@Inject
	private EditHistoryCommandHandler editHistory;

	// Delete history:
	@Inject
	private RemoveHistoryCommandHandler removeHistory;

	// Phan anh cong ty:
	@Inject
	private ReflUnrCompCommandHandler reflUnrComp;

	// hoatt - update selection order
	@Inject
	private UpdateSelOrderCommandHandler updateSelOrder;

	@POST
	@Path("findAll/{isCps017}")
	public List<PerInfoSelectionItemDto> getAllPerInfoSelectionItem(@PathParam("isCps017") boolean isCps017) {
		return this.finder.getAllPerInfoSelectionItem(isCps017);
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
	
	@POST
	@Path("checkUseSelectionItem/{selectedId}")
	public Boolean checkUseSelectionItem(@PathParam("selectedId") String selectionItemId) {
		return this.removeCommandHandler.checkSelectionItemId(selectionItemId);
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
	public JavaTypeResult<String> AddSelection(AddSelectionCommand command) {
		String newSelectionId = this.addSelectionCommandHandler.handle(command);
		return new JavaTypeResult<String>(newSelectionId);
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

	// Edit History:
	@POST
	@Path("editHistory")
	public void EditHistory(EditHistoryCommand command) {
		this.editHistory.handle(command);
	}

	// Delete History:
	@POST
	@Path("removeHistory")
	public void RemoveHistory(RemoveHistoryCommand command) {
		this.removeHistory.handle(command);
	}

	// Lanlt
	@POST
	@Path("findAllCombox")
	public List<ComboBoxObject> getAllSelectionByHistoryId(SelectionQuery query) {
		return this.selecFider.getAllComboxByHistoryId(query);
	}
	
	// Lanlt
	@POST
	@Path("findSelectionInit")
	public List<SelectionInitDto> getAllSelectionByHistoryId(SelectionInitQuery query) {
		return this.selecFider.getAllSelectionByHistoryId(query);
	}


	// Phan anh cong ty:
	@POST
	@Path("reflunrcomp")
	public void ReflUnrComp(ReflUnrCompCommand command) {
		this.reflUnrComp.handle(command);
	}

	// Lanlt
	@POST
	@Path("findAllSelectionItem")
	public List<PerInfoSelectionItemDto> getAllelectionItem() {
		return this.finder.getAllSelectionItem();
	}

	// update selection order
	@POST
	@Path("updateSelOrder")
	public void updateSelOrder(List<UpdateSelOrderCommand> lstSelOrder) {
		this.updateSelOrder.handle(lstSelOrder);
	}
	
}
