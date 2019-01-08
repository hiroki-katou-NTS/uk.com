package nts.uk.ctx.exio.ws.exo.ctgsettingoutputitem;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exo.outputitembatchsetting.CtgItemDataDto;
import nts.uk.ctx.exio.app.find.exo.outputitembatchsetting.CtgItemDatasFinder;
import nts.uk.ctx.exio.app.find.exo.outputitembatchsetting.StandarOutputItemDTO;
import nts.uk.ctx.exio.app.find.exo.outputitembatchsetting.StandarOutputItemFinder;

@Path("exio/exo/ctgoutputitem")
@Produces("application/json")
public class CtgToSettingOutputItemWebService {
	
	@Inject
	CtgItemDatasFinder ctgItemDataFinder;
	
	@Inject
	StandarOutputItemFinder standarOutputItemFinder;
	
	@POST
	@Path("getOutItems/{condSetCd}")
	public List<StandarOutputItemDTO> getListStandarOutputItem(@PathParam("condSetCd") String condSetCd){
		return standarOutputItemFinder.getListStandarOutputItem(condSetCd);
	}
	@POST
	@Path("getctgdata/{categoryId}")
	public List<CtgItemDataDto> getListCtgData(@PathParam("categoryId") int categoryId){
		return ctgItemDataFinder.getListExOutCtgItemData(categoryId);
	}

}
