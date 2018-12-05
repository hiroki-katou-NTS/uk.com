package nts.uk.ctx.pr.core.ws.wageprovision.wagetable;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.AddWageTableCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.AddWageTableCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.DeleteWageTableHistoryCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.DeleteWageTableHistoryCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.UpdateWageTableCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.UpdateWageTableCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.UpdateWageTableHistoryCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.UpdateWageTableHistoryCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.ElementItemNameDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.ElementRangeSettingDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableContentCreater;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableContentDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableFinder;

@Path("ctx/pr/core/wageprovision/wagetable")
@Produces("application/json")
public class WageTableWebService {

	@Inject
	private WageTableFinder finder;

	@Inject
	private AddWageTableCommandHandler addHandler;

	@Inject
	private UpdateWageTableCommandHandler updateHandler;

	@Inject
	private UpdateWageTableHistoryCommandHandler updateHistHandler;

	@Inject
	private DeleteWageTableHistoryCommandHandler deleteHistHandler;
	
	@Inject
	private WageTableContentCreater contentCreater;

	@POST
	@Path("/get-all-wagetable")
	public List<WageTableDto> getAllWageTable() {
		return finder.getAll();
	}

	@POST
	@Path("/get-wagetable-by-code/{code}")
	public WageTableDto getWageTable(@PathParam("code") String wageTableCode) {
		return finder.getWageTableById(wageTableCode);
	}
	
	@POST
	@Path("/get-wagetable-content/{histId}")
	public WageTableContentDto getWageTableContent(@PathParam("histId") String historyId) {
		return finder.getWageTableContent(historyId);
	}
	
	@POST
	@Path("/get-element-range-setting/{histId}")
	public ElementRangeSettingDto getElemRangeSet(@PathParam("histId") String historyId) {
		return finder.getElemRangeSet(historyId);
	}

	@POST
	@Path("/addWageTable")
	public JavaTypeResult<String> addWageTable(AddWageTableCommand command) {
		return new JavaTypeResult<String>(addHandler.handle(command));
	}

	@POST
	@Path("/updateWageTable")
	public JavaTypeResult<String> updateWageTable(UpdateWageTableCommand command) {
		return new JavaTypeResult<String>(updateHandler.handle(command));
	}

	@POST
	@Path("/get-all-elements")
	public List<ElementItemNameDto> getStatementItemName() {
		return finder.getElements();
	}

	@POST
	@Path("/editHistory")
	public void editWageTableHistory(UpdateWageTableHistoryCommand command) {
		updateHistHandler.handle(command);
	}

	@POST
	@Path("/deleteHistory")
	public JavaTypeResult<String> deleteWageTableHistory(DeleteWageTableHistoryCommand command) {
		return new JavaTypeResult<String>(deleteHistHandler.handle(command));
	}

	@POST
	@Path("/create-1d-wage-table")
	public WageTableContentDto createOneDimensionWageTable(ElementRangeSettingDto params) {
		return contentCreater.createOneDimensionWageTable(params);
	}
	
	@POST
	@Path("/create-2d-wage-table")
	public WageTableContentDto createTwoDimensionWageTable(ElementRangeSettingDto params) {
		return contentCreater.createTwoDimensionWageTable(params);
	}

	@POST
	@Path("/create-3d-wage-table")
	public WageTableContentDto createThreeDimensionWageTable(ElementRangeSettingDto params) {
		return contentCreater.createThreeDimensionWageTable(params);
	}
	
}
