package nts.uk.ctx.exio.ws.exo.condset;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exo.condset.CopyOutCondSet;
import nts.uk.ctx.exio.app.command.exo.condset.CopyOutputCondSetCommandHandler;
import nts.uk.ctx.exio.app.command.exo.condset.ExcuteCopyOutCondSetCommandHandler;
import nts.uk.ctx.exio.app.command.exo.condset.OutSetContentCommandHandler;
import nts.uk.ctx.exio.app.command.exo.condset.RegisterStdOutputCondSetCommandHandler;
import nts.uk.ctx.exio.app.command.exo.condset.RemoveStdOutputCondSetCommandHandler;
import nts.uk.ctx.exio.app.command.exo.condset.StdOutputCondSetCommand;
import nts.uk.ctx.exio.app.find.exo.categoryitemdata.CtgItemDataCndDetailDto;
import nts.uk.ctx.exio.app.find.exo.categoryitemdata.CtgItemDataDto;
import nts.uk.ctx.exio.app.find.exo.categoryitemdata.CtgItemDataFinder;
import nts.uk.ctx.exio.app.find.exo.condset.CondSetDto;
import nts.uk.ctx.exio.app.find.exo.condset.StdOutputCondSetDto;
import nts.uk.ctx.exio.app.find.exo.condset.StdOutputCondSetFinder;
import nts.uk.ctx.exio.app.find.exo.item.StdOutItemDto;

@Path("exio/exo/condset")
@Produces("application/json")
public class StdOutConSetWebService extends WebService {

	@Inject
	private StdOutputCondSetFinder stdOutputCondSetFinder;

	@Inject
	private ExcuteCopyOutCondSetCommandHandler excuteCopyOutCondSetCommandHandler;

	@Inject
	private RegisterStdOutputCondSetCommandHandler registerStdOutputCondSetCommandHandler;
	
	@Inject
	private RemoveStdOutputCondSetCommandHandler removeStdOutputCondSetCommandHandler;

	@Inject
	private CtgItemDataFinder ctgItemDataFinder;
	
	@Inject
	private CopyOutputCondSetCommandHandler copyOutputCondSetCommandHandler;
	
	@Inject
	private OutSetContentCommandHandler outSetContentCommandHandler;

	@POST
	@Path("excuteCopy")
	public CopyOutCondSet ExecuteCopy(StdOutputCondSetCommand command) {
		return excuteCopyOutCondSetCommandHandler.handle(command);
	}

	@POST
	@Path("getCndSet")
	public List<CondSetDto> getCndSet() {
		return stdOutputCondSetFinder.getCndSet();
	}

	@POST
	@Path("getOutItem")
	public List<StdOutItemDto> getOutItem(String cndSetcd) {
		return stdOutputCondSetFinder.getOutItem(cndSetcd);
	}

	@POST
	@Path("findByCode/{cndSetCd}/{outItemCode}")
	public StdOutItemDto findByCode(@PathParam("cndSetCd") String cndSetCd,
			@PathParam("outItemCode") String outItemCode) {
		return stdOutputCondSetFinder.getByKey(cndSetCd, outItemCode);
	}

	@POST
	@Path("register")
	public void register(StdOutputCondSetCommand command) {
		registerStdOutputCondSetCommandHandler.handle(command);
	}

	@POST
	@Path("delete")
	public void delete(StdOutputCondSetCommand command) {
		removeStdOutputCondSetCommandHandler.handle(command);
	}

	@POST
	@Path("getAllCategoryItem/{categoryId}")
	public List<CtgItemDataDto> getAllCategoryItem(@PathParam("categoryId") Integer categoryId) {
		return ctgItemDataFinder.getAllCategoryItem(categoryId);
	}

	@POST
	@Path("getCondSet/{modeScreen}/{cndSetCd}")
	public List<StdOutputCondSetDto> getCondSet(@PathParam("modeScreen") String modeScreen,
			@PathParam("cndSetCd") String cndSetCd) {
		return stdOutputCondSetFinder.getConditionSetting(modeScreen,cndSetCd);
		
	}
	
	@POST
	@Path("copy")
	public void copy(CopyOutCondSet copy) {
		copyOutputCondSetCommandHandler.handle(copy);
	}
	
	@POST
	@Path("getListCtgItems/{categoryId}")
	public CtgItemDataCndDetailDto getListCtgItems(@PathParam("categoryId") String categoryId) {
			int ctgItemNo = 1;
			return ctgItemDataFinder.getDataItemDetail(Integer.valueOf(categoryId), ctgItemNo);
	}
	
	@POST
	@Path("outSetContent")
	public void outSetContent(StdOutputCondSetCommand command) {
		outSetContentCommandHandler.handle(command);
	}
}
