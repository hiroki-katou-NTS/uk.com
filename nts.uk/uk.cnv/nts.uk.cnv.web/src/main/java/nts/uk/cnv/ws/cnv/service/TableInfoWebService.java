package nts.uk.cnv.ws.cnv.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.cnv.app.cnv.command.ErpTableDesignImportCommand;
import nts.uk.cnv.app.cnv.command.ErpTableDesignImportCommandHandler;
import nts.uk.cnv.app.cnv.dto.GetErpColumnsResultDto;
import nts.uk.cnv.app.cnv.dto.GetUkColumnsParamDto;
import nts.uk.cnv.app.cnv.dto.GetUkColumnsResultDto;
import nts.uk.cnv.app.cnv.dto.ImportFromFileDto;
import nts.uk.cnv.app.cnv.dto.ImportFromFileResult;
import nts.uk.cnv.app.cnv.service.ImportDdlService;
import nts.uk.cnv.app.cnv.service.TableInfoService;

@Path("cnv/tableinfo")
@Produces("application/json")
public class TableInfoWebService {
	@Inject
	private ErpTableDesignImportCommandHandler handler;

	@Inject
	private TableInfoService tableInfoService;
	@Inject
	private ImportDdlService importDdlService;


	@POST
	@Path("import")
	public void importTable(ErpTableDesignImportCommand command) {
		handler.handle(command);
	}

	@POST
	@Path("importfromfile")
	public ImportFromFileResult importFromFile(ImportFromFileDto param) {
		return importDdlService.importErpFromFile(param);
	}

	@POST
	@Path("getukcolumns")
	public List<GetUkColumnsResultDto> getUkColumns(GetUkColumnsParamDto param) {
		return tableInfoService.getUkColumns(param.getCategory(), param.getTableName(), param.getRecordNo());
	}

	@POST
	@Path("geterpcolumns")
	public List<GetErpColumnsResultDto> getErpColumns(String tableName) {
		return tableInfoService.getErpColumns(tableName);
	}
}
