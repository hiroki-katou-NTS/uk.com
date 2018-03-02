package nts.uk.ctx.exio.ws.exi.condset;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exi.category.ExAcpCategoryDto;
import nts.uk.ctx.exio.app.find.exi.category.ExAcpCtgItemDatDto;
import nts.uk.ctx.exio.app.find.exi.condset.StdAcceptCondSetDto;
import nts.uk.ctx.exio.app.find.exi.condset.StdAcceptCondSetFinder;

@Path("exio/exi/condset")
@Produces("application/json")
public class StdAcceptCondSetWebService {
	@Inject
	private StdAcceptCondSetFinder stdAcceptCondSetFind;
	
	@POST
	@Path("getConditionBySystemType/{systemType}")
	public List<StdAcceptCondSetDto> getConditionBySystemType(@PathParam("systemType") int systemType) {
		List<StdAcceptCondSetDto> list = new ArrayList<StdAcceptCondSetDto>();

//		list.add(new StdAcceptCondSetDto(0, "001", "adfasdf", 1, 1, 4, 1, "Test-1", 1, 1));
//		list.add(new StdAcceptCondSetDto("0002", "2", "2", 3, 1, 1, 5, 1, "Test-2", 1, 1, (long) 1));
//		list.add(new StdAcceptCondSetDto("0002", "3", "2", 1, 1, 1, 7, 1, "Test-3", 1, 1, (long) 1));
//
//		if (systemType == 1) {
//			list.add(new StdAcceptCondSetDto("0001", "4", "4", 2, 1, 1, 5, 1, "Test-4", 1, 1, (long) 1));
//		}
		return list;
		// return this.stdAcceptCondSetFind.getStdAcceptCondSetBySystemType(1);
	}

	@POST
	@Path("checkExistCode/{sysType}/{condCode}")
	public boolean getConditionBySystemType(@PathParam("sysType") int systemType, @PathParam("condCode") String conditionCode) {
		return stdAcceptCondSetFind.isCodeExist(systemType, conditionCode);
	}
	
	@POST
	@Path("getTotalRecord/{fileId}")
	public int getTotalRecord(@PathParam("fileId") String fileId) {
		return stdAcceptCondSetFind.getTotalRecordCsv(fileId);
	}
	
	/**
	 * Dummy data category item data
	 * @param categoryId
	 * @return
	 */
	@POST
	@Path("getCategoryItemData/{categoryId}")
	public List<ExAcpCtgItemDatDto> getCategoryItemData(@PathParam("categoryId") String categoryId) {
		return stdAcceptCondSetFind.getCategoryItemData(categoryId);
	}
	/**
	 * Dummy data category
	 * @return
	 */
	@POST
	@Path("getAllCategory")
	public List<ExAcpCategoryDto> getAllCategory() {
		return stdAcceptCondSetFind.getAllCategory();
	}
}
