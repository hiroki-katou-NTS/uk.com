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

@Path("cnv/tableinfo")
@Produces("application/json")
public class TableInfoWebService {
	@Inject
	private TableInfoService tableInfoService;

	@POST
	@Path("getukcolumns")
	public List<String> getUkColumns(GetUkColumnsParamDto param) {
		return tableInfoService.getUkColumns(param.getCategory(), param.getTableName(), param.getRecordNo());
	}

	@POST
	@Path("geterpcolumns")
	public List<GetErpColumnsResultDto> getErpColumns(GetErpColumnsParamDto param) {
		return tableInfoService.getErpColumns(param.getTableName());
	}
}
