package nts.uk.ctx.exio.ws.exo.condset;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exo.condset.ExcuteCopyOutCondSet;
import nts.uk.ctx.exio.app.command.exo.condset.ExcuteCopyOutCondSetCommandHandler;
import nts.uk.ctx.exio.app.command.exo.condset.RegisterStdOutputCondSetCommandHandler;
import nts.uk.ctx.exio.app.command.exo.condset.StdOutputCondSetCommand;
import nts.uk.ctx.exio.app.find.exo.categoryitemdata.CtgItemDataDto;
import nts.uk.ctx.exio.app.find.exo.categoryitemdata.CtgItemDataFinder;
import nts.uk.ctx.exio.app.find.exo.condset.StdOutputCondSetFinder;
import nts.uk.ctx.exio.app.find.exo.item.StdOutItemDto;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;

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
	private CtgItemDataFinder ctgItemDataFinder;

	@POST
	@Path("excuteCopy")
	public ExcuteCopyOutCondSet ExecuteCopy(StdOutputCondSetCommand command) {
		return excuteCopyOutCondSetCommandHandler.handle(command);
	}

	@POST
	@Path("getCndSet")
	public List<StdOutputCondSet> getCndSet() {
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
		registerStdOutputCondSetCommandHandler.handle(command);
	}

	@POST
	@Path("getAllCategoryItem/{categoryId}")
	public List<CtgItemDataDto> getAllCategoryItem(@PathParam("categoryId") String categoryId) {
		return ctgItemDataFinder.getAllCategoryItem(categoryId);
	}

}
