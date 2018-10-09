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
import nts.uk.ctx.exio.app.command.exo.condset.RegisterStdOutputCondSetCommandHandler;
import nts.uk.ctx.exio.app.command.exo.condset.RemoveStdOutputCondSetCommandHandler;
import nts.uk.ctx.exio.app.command.exo.condset.StdOutputCondSetCommand;
import nts.uk.ctx.exio.app.find.exo.category.ExOutCtgDto;
import nts.uk.ctx.exio.app.find.exo.categoryitemdata.CtgItemDataDto;
import nts.uk.ctx.exio.app.find.exo.categoryitemdata.CtgItemDataFinder;
import nts.uk.ctx.exio.app.find.exo.condset.CondSetDto;
import nts.uk.ctx.exio.app.find.exo.condset.StdOutputCondSetDto;
import nts.uk.ctx.exio.app.find.exo.condset.StdOutputCondSetFinder;
import nts.uk.ctx.exio.app.find.exo.item.StdOutItemDto;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondParam;

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
	@Path("getAllCategoryItem/{categoryId}/{dataType}")
	public List<CtgItemDataDto> getAllCategoryItem(@PathParam("categoryId") Integer categoryId,
			@PathParam("dataType") Integer dataType) {
		return ctgItemDataFinder.getAllCategoryItem(categoryId, dataType);
	}

	@POST
	@Path("getCondSet")
	public List<StdOutputCondSetDto> getCondSet(StdOutputCondParam param) {
		return stdOutputCondSetFinder.getConditionSetting(param.getModeScreen(),param.getCndSetCd());
		
	}
	@POST
	@Path("getExOutCtgDto/{categoryId}")
	public ExOutCtgDto getExOutCtgDto(@PathParam("categoryId") Integer categoryId) {
		return stdOutputCondSetFinder.getExOutCtgDto(categoryId);
		
	}
	
	@POST
	@Path("copy")
	public void copy(CopyOutCondSet copy) {
		copyOutputCondSetCommandHandler.handle(copy);
	}
	
	@POST
	@Path("outSetContent/{cndSetCd}/{standType}")
	public List<StdOutItemDto> outSetContent(@PathParam("cndSetCd") String cndSetCd,
	        @PathParam("standType") int standType) {
		return stdOutputCondSetFinder.getOutItem(cndSetCd, standType);
	}

}
