package nts.uk.ctx.exio.ws.exi.condset;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exi.condset.AddStdAcceptCondSetCommandHandler;
import nts.uk.ctx.exio.app.command.exi.condset.CopyStdAcceptCondSetCommand;
import nts.uk.ctx.exio.app.command.exi.condset.CopyStdAcceptCondSetCommandHandler;
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
public class StdAcceptCondSetWebService extends WebService {
	@Inject
	private StdAcceptCondSetFinder stdAcceptCondSetFind;

	@Inject
	private AddStdAcceptCondSetCommandHandler addStdAccCondSetHandler;

	@Inject
	private UpdateStdAcceptCondSetCommandHandler updateStdAccCondSetHandler;

	@Inject
	private RemoveStdAcceptCondSetCommandHandler removeStdAccCondSetHandler;

	@Inject
	private CopyStdAcceptCondSetCommandHandler copyStdAccCondSetHandler;

	@POST
	@Path("getSysType")
	public List<SystemTypeDto> getSysType() {
		return this.stdAcceptCondSetFind.getSystemTypes();
	}

	@POST
	@Path("getAllStdAcceptCondSet")
	public List<StdAcceptCondSetDto> getAllStdAcceptCondSet() {
		return this.stdAcceptCondSetFind.getAllStdAcceptCondSet();
	}
	

	@POST
	@Path("getStdAcceptCondSetBySysType/{systemType}")
	public List<StdAcceptCondSetDto> getStdAcceptCondSetBySysType(@PathParam("systemType") int systemType) {
		return this.stdAcceptCondSetFind.getStdAcceptCondSetBySysType(systemType);
	}

	@POST
	@Path("checkExistCode/{condCode}")
	public boolean getConditionBySystemType(@PathParam("condCode") String conditionCode) {
		return stdAcceptCondSetFind.isCodeExist(conditionCode);
	}

	@POST
	@Path("getOneStdCondSet/{condCode}")
	public StdAcceptCondSetDto getConditionBySystemTypeAndCode(@PathParam("condCode") String conditionCode) {
		return stdAcceptCondSetFind.getStdAccCondSet(conditionCode);
	}

	@POST
	@Path("registerStd")
	public void registerStandardCondition(StdAcceptCondSetCommand command) {
		if (command.getAction() == 0) {
			this.addStdAccCondSetHandler.handle(command);
		}else {
			this.updateStdAccCondSetHandler.handle(command);
		}
	}

	@POST
	@Path("deleteStd")
	public void deleteStandardCondition(StdAcceptCondSetCommand command) {
		this.removeStdAccCondSetHandler.handle(command);
	}

	@POST
	@Path("copyCondSet")
	public void copyCondSet(CopyStdAcceptCondSetCommand command) {
		copyStdAccCondSetHandler.handle(command);
	}

	/**
	 * Dummy data category item data
	 * 
	 * @param categoryId
	 * @return
	 */
	@POST
	@Path("getCategoryItemData/{categoryId}")
	public List<ExAcpCtgItemDatDto> getCategoryItemData(@PathParam("categoryId") int categoryId) {
		return stdAcceptCondSetFind.getCategoryItemData(categoryId);
	}

	/**
	 * Dummy data category
	 * 
	 * @return
	 */
	@POST
	@Path("getAllCategoryBystem/{systemType}")
	public List<ExAcpCategoryDto> getAllCategoryBystem(@PathParam("systemType") int systemType) {
		return stdAcceptCondSetFind.getCategoryBySystem(systemType);
	}
}
