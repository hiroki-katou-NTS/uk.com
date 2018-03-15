package nts.uk.ctx.exio.ws.exi.csvimport;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exi.csvimport.CsvImportDataFinder;
import nts.uk.ctx.exio.app.find.exi.csvimport.GettingCsvDataDto;

@Path("exio/exi/csvimport")
@Produces("application/json")
public class CsvDataImportService {
	@Inject
	private CsvImportDataFinder fileFind;
	
	@POST
	@Path("getNumberOfLine/{fileId}")
	public int getNumberOfLine(@PathParam("fileId") String fileId) {
		return fileFind.getNumberOfLine(fileId);
	}
	
	@POST
	@Path("getRecord/{fileId}/{numOfCol}/{index}")
	public List<String> getRecord(@PathParam("fileId") String fileId, @PathParam("numOfCol") int numOfCol, @PathParam("index") int index) {
		return fileFind.getRecordByIndex(fileId, numOfCol, index);
	}
	
	@POST
	@Path("getCsvRecord")
	public List<String> getRecord(GettingCsvDataDto info) {
		return fileFind.getRecord(info);
	}
}
