package nts.uk.cnv.ws.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.cnv.app.dto.GetErpColumnsParamDto;
import nts.uk.cnv.app.dto.GetErpColumnsResultDto;
import nts.uk.cnv.app.dto.GetUkColumnsParamDto;
import nts.uk.cnv.app.service.TableInfoService;
import nts.uk.cnv.dom.conversiontable.OneColumnConversion;

@Path("cnv/tableinfo")
@Produces("application/json")
public class TableInfoWebService {
//	@Inject
//	private ErpTableDesignImportCommandHandler handler;

	@Inject
	private TableInfoService tableInfoService;
//	@Inject
//	private ImportDdlService importDdlService;

//	@POST
//	@Path("import")
//	public void importTable(ErpTableDesignImportCommand command) {
//		handler.handle(command);
//	}

//	@POST
//	@Path("importfromfile")
//	public ImportFromFileResult importFromFile(ImportFromFileDto param) {
//		return importDdlService.importErpFromFile(param);
//	}

	@POST
	@Path("getukcolumns")
	public List<OneColumnConversion> getUkColumns(GetUkColumnsParamDto param) {
		return tableInfoService.getUkColumns(param.getCategory(), param.getTableName(), param.getRecordNo());
	}

	@POST
	@Path("geterpcolumns")
	public List<GetErpColumnsResultDto> getErpColumns(GetErpColumnsParamDto param) {
		return tableInfoService.getErpColumns(param.getTableName());
	}
}
