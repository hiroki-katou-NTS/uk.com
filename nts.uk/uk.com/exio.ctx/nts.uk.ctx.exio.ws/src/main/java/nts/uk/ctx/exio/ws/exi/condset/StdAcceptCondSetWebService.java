package nts.uk.ctx.exio.ws.exi.condset;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.command.exi.condset.AddStdAcceptCondSetCommandHandler;
import nts.uk.ctx.exio.app.command.exi.condset.RemoveStdAcceptCondSetCommandHandler;
import nts.uk.ctx.exio.app.command.exi.condset.StdAcceptCondSetCommand;
import nts.uk.ctx.exio.app.command.exi.condset.UpdateStdAcceptCondSetCommandHandler;
import nts.uk.ctx.exio.app.find.exi.category.ExAcpCategoryDto;
import nts.uk.ctx.exio.app.find.exi.category.ExAcpCtgItemDatDto;
import nts.uk.ctx.exio.app.find.exi.condset.StdAcceptCondSetDto;
import nts.uk.ctx.exio.app.find.exi.condset.StdAcceptCondSetFinder;
import nts.uk.ctx.exio.app.find.exi.condset.SystemTypeDto;

@Path("exio/exi/condset")
@Produces("application/json")
public class StdAcceptCondSetWebService {
	@Inject
	private StdAcceptCondSetFinder stdAcceptCondSetFind;
	
	@Inject
	private AddStdAcceptCondSetCommandHandler addStdAccCondSetHandler;
	
	@Inject
	private UpdateStdAcceptCondSetCommandHandler updateStdAccCondSetHandler;
	
	@Inject
	private RemoveStdAcceptCondSetCommandHandler removeStdAccCondSetHandler;
	
	@POST
	@Path("getSysType")
	public List<SystemTypeDto> getSysType() {
		return this.stdAcceptCondSetFind.getSystemTypes();
	}
	
	@POST
	@Path("getStdAcceptCondSetBySysType/{systemType}")
	public List<StdAcceptCondSetDto> getStdAcceptCondSetBySysType(@PathParam("systemType") int systemType) {
		return this.stdAcceptCondSetFind.getStdAcceptCondSetBySysType(systemType);
	}

	@POST
	@Path("checkExistCode/{sysType}/{condCode}")
	public boolean getConditionBySystemType(@PathParam("sysType") int systemType, @PathParam("condCode") String conditionCode) {
		return stdAcceptCondSetFind.isCodeExist(systemType, conditionCode);
	}
	
	@POST
	@Path("getOneStdCondSet/{sysType}/{condCode}")
	public StdAcceptCondSetDto getConditionBySystemTypeAndCode(@PathParam("sysType") int systemType, @PathParam("condCode") String conditionCode) {
		return stdAcceptCondSetFind.getStdAccCondSet(systemType, conditionCode);
	}
	
	@POST
	@Path("registerStd")
	public void registerStandardCondition(StdAcceptCondSetCommand command) {
		
	}
	
	@POST
	@Path("deleteStd")
	public void deleteStandardCondition(StdAcceptCondSetCommand command) {
		this.removeStdAccCondSetHandler.handle(command);
	}
	
	@POST
	@Path("getNumberOfLine/{fileId}")
	public int getNumberOfLine(@PathParam("fileId") String fileId) {
		return stdAcceptCondSetFind.getNumberOfLine(fileId);
	}
	
	@POST
	@Path("getRecord/{fileId}/{numOfCol}/{index}")
	public List<String> getRecord(@PathParam("fileId") String fileId, @PathParam("numOfCol") int numOfCol, @PathParam("index") int index) {
		return stdAcceptCondSetFind.getRecordByIndex(fileId, numOfCol, index);
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
