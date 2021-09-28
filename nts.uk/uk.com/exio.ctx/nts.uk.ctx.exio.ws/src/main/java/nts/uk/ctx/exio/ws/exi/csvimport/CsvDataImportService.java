package nts.uk.ctx.exio.ws.exi.csvimport;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.find.exi.csvimport.CsvImportDataFinder;
import nts.uk.ctx.exio.app.find.exi.csvimport.CsvMappingDataDto;
import nts.uk.ctx.exio.app.find.exi.csvimport.GettingCsvDataDto;

@Path("exio/exi/csvimport")
@Produces("application/json")
public class CsvDataImportService extends WebService {
	@Inject
	private CsvImportDataFinder fileFind;

	@POST
	@Path("getNumberOfLine/{fileId}/{endcoding}")
	public int getNumberOfLine(@PathParam("fileId") String fileId, @PathParam("endcoding") Integer endcoding) {
		return fileFind.getNumberOfLine(fileId, endcoding);
	}

	@POST
	@Path("getRecord/{fileId}/{dataLineNum}/{startLine}/{endcoding}")
	public List<CsvMappingDataDto> getRecord(@PathParam("fileId") String fileId,
			@PathParam("dataLineNum") int dataLineNum, 
			@PathParam("startLine") int startLine, 
			@PathParam("endcoding") Integer endcoding) {
		return fileFind.getRecordByIndex(fileId, dataLineNum, startLine, endcoding);
	}

	@POST
	@Path("getCsvRecord")
	public List<String> getRecord(GettingCsvDataDto info) {
		return fileFind.getRecord(info);
	}
}
