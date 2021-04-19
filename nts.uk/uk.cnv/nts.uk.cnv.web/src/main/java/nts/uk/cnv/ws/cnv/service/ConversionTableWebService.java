package nts.uk.cnv.ws.cnv.service;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.cnv.app.cnv.command.DeleteConversionRecordCommand;
import nts.uk.cnv.app.cnv.command.DeleteConversionRecordCommandHandler;
import nts.uk.cnv.app.cnv.command.DeleteConversionSourceCommandHandler;
import nts.uk.cnv.app.cnv.command.RegistConversionCategoryCommand;
import nts.uk.cnv.app.cnv.command.RegistConversionCategoryCommandHandler;
import nts.uk.cnv.app.cnv.command.RegistConversionRecordCommand;
import nts.uk.cnv.app.cnv.command.RegistConversionRecordCommandHandler;
import nts.uk.cnv.app.cnv.command.RegistConversionSourceCommand;
import nts.uk.cnv.app.cnv.command.RegistConversionSourceCommandHandler;
import nts.uk.cnv.app.cnv.command.RegistConversionTableCommand;
import nts.uk.cnv.app.cnv.command.RegistConversionTableCommandHandler;
import nts.uk.cnv.app.cnv.command.SwapConversionRecordCommand;
import nts.uk.cnv.app.cnv.command.SwapConversionRecordCommandHandler;
import nts.uk.cnv.app.cnv.dto.AddSourceResult;
import nts.uk.cnv.app.cnv.dto.FindConversionTableDto;
import nts.uk.cnv.app.cnv.dto.FindConversionTableResult;
import nts.uk.cnv.app.cnv.dto.GetCategoryTablesDto;
import nts.uk.cnv.app.cnv.service.ConversionTableService;

@Path("cnv/conversiontable")
@Produces("application/json")
public class ConversionTableWebService extends WebService {

	@Inject
	RegistConversionCategoryCommandHandler regstCategoryHandler;

	@Inject
	RegistConversionSourceCommandHandler regstSourceHandler;

	@Inject
	DeleteConversionSourceCommandHandler deleteSourceHandler;

	@Inject
	RegistConversionRecordCommandHandler regstRecordHandler;

	@Inject
	DeleteConversionRecordCommandHandler deleteRecordHandler;

	@Inject
	SwapConversionRecordCommandHandler swapRecordHandler;

	@Inject
	RegistConversionTableCommandHandler handler;

	@Inject
	ConversionTableService service;

	@POST
	@Path("find")
	public FindConversionTableResult find(FindConversionTableDto dto) {
		return service.find(dto);
	}

	@POST
	@Path("regist")
	public void regist(RegistConversionTableCommand command) {
		handler.handle(command);
	}

	@POST
	@Path("test")
	public String test(FindConversionTableDto dto) {
		return service.test(dto);
	}

	@POST
	@Path("category/regist")
	public void registCategory(RegistConversionCategoryCommand command) {
		regstCategoryHandler.handle(command);
	}

	@POST
	@Path("getcategories")
	public GetCategoryTablesDto getCategoryTables(String category) {
		return service.getCategoryTables(category.replace("\"", ""));
	}

	@POST
	@Path("source/add")
	public AddSourceResult addSource(RegistConversionSourceCommand command) {
		return regstSourceHandler.handle(command);
	}

	@POST
	@Path("source/delete")
	public void deleteSource(String sourceId) {
		deleteSourceHandler.handle(sourceId.replace("\"", ""));
	}

	@POST
	@Path("record/regist")
	public void registRecord(RegistConversionRecordCommand command) {
		regstRecordHandler.handle(command);
	}

	@POST
	@Path("record/delete")
	public void registRecord(DeleteConversionRecordCommand command) {
		deleteRecordHandler.handle(command);
	}

	@POST
	@Path("record/swap")
	public void swapRecord(SwapConversionRecordCommand command) {
		swapRecordHandler.handle(command);
	}
}
