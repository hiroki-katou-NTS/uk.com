package nts.uk.cnv.ws.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.cnv.app.cnv.command.UkTableDesignImportCommandHandler;
import nts.uk.cnv.app.cnv.dto.ExportToFileDto;
import nts.uk.cnv.app.cnv.dto.GetErpColumnsResultDto;
import nts.uk.cnv.app.cnv.dto.GetUkColumnsParamDto;
import nts.uk.cnv.app.cnv.dto.GetUkColumnsResultDto;
import nts.uk.cnv.app.cnv.dto.TableDesignExportDto;
import nts.uk.cnv.app.td.service.TableDesignerService;
import nts.uk.cnv.dom.td.service.ExportDdlServiceResult;

@Path("cnv/tabledesign")
@Produces("application/json")
public class TableDesignWebService extends WebService{

	@Inject
	private UkTableDesignImportCommandHandler handler;

	@Inject
	private TableDesignerService tdService;

//	@POST
//	@Path("import")
//	public void importTable(UkTableDesignImportCommand command) {
//		handler.handle(command);
//	}

	@POST
	@Path("exportddl")
	public ExportDdlServiceResult export(TableDesignExportDto params) {
		return tdService.exportDdl(params);
	}

//	@POST
//	@Path("importfromfile")
//	public ImportFromFileResult importFromFile(ImportFromFileDto param) {
//		return tdService.importFromFile(param);
//	}

	@POST
	@Path("exporttofile")
	public void exportToFile(ExportToFileDto param) {
		tdService.exportToFile(param);
	}

//	@POST
//	@Path("importfromfile_erp")
//	public ImportFromFileResult importErpFromFile(ImportFromFileDto param) {
//		return tdService.importErpFromFile(param);
//	}

	@POST
	@Path("getukcolumns")
	public List<GetUkColumnsResultDto> getUkColumns(GetUkColumnsParamDto param) {
		return tdService.getUkColumns(param.getCategory(), param.getTableId(), param.getRecordNo(), param.getFeature());
	}

	@POST
	@Path("geterpcolumns")
	public List<GetErpColumnsResultDto> getErpColumns(String tableName) {
		return tdService.getErpColumns(tableName);
	}
}
