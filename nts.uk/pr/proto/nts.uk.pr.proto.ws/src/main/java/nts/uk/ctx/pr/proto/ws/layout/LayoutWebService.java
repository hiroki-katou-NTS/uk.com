package nts.uk.ctx.pr.proto.ws.layout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutCommand;
import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutCommandHandler;
import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutHistoryCommand;
import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutHistoryCommandHandler;
import nts.uk.ctx.pr.proto.app.command.layout.DeleteLayoutHistoryCommand;
import nts.uk.ctx.pr.proto.app.command.layout.DeleteLayoutHistoryCommandHandler;
import nts.uk.ctx.pr.proto.app.command.layout.UpdateLayoutHistoryCommand;
import nts.uk.ctx.pr.proto.app.command.layout.UpdateLayoutHistoryCommandHandler;
import nts.uk.ctx.pr.proto.app.command.layout.register.RegisterLayoutCommand;
import nts.uk.ctx.pr.proto.app.command.layout.register.RegisterLayoutCommandHandler;
import nts.uk.ctx.pr.proto.app.find.layout.LayoutDto;
import nts.uk.ctx.pr.proto.app.find.layout.LayoutMasterFinder;
import nts.uk.ctx.pr.proto.app.find.layout.category.LayoutMasterCategoryDto;
import nts.uk.ctx.pr.proto.app.find.layout.category.LayoutMasterCategoryFinder;
import nts.uk.ctx.pr.proto.app.find.layout.detail.LayoutMasterDetailDto;
import nts.uk.ctx.pr.proto.app.find.layout.detail.LayoutMasterDetailFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("pr/proto/layout")
@Produces("application/json")
public class LayoutWebService extends WebService {

	@Inject
	private CreateLayoutCommandHandler createLayoutData;
	@Inject
	private CreateLayoutHistoryCommandHandler createHistoryData;
	@Inject
	private UpdateLayoutHistoryCommandHandler updateData;
	@Inject
	private DeleteLayoutHistoryCommandHandler deleteData;
	@Inject 
	private RegisterLayoutCommandHandler registerLayoutHandler;
	@Inject
	private LayoutMasterFinder find;
	@Inject
	private LayoutMasterCategoryFinder categoryFinder;
	@Inject
	private LayoutMasterDetailFinder detailFinder;
	
	@POST
	@Path("findalllayout")
	public List<LayoutDto> getAllLayout(){
		return this.find.getAllLayout(AppContexts.user().companyCode());		
	}
	@POST
	@Path("findlayout/{stmtCode}/{startYm}")
	public LayoutDto getLayout(@PathParam("stmtCode") String stmtCode, @PathParam("startYm") int startYm){
		return this.find.getLayout(AppContexts.user().companyCode(), stmtCode, startYm).get();
	}
	@POST
	@Path("findlayoutdetail/{stmtCode}/{startYm}/{categoryAtr}/{itemCd}")
	public LayoutMasterDetailDto getDetail(@PathParam("stmtCode") String stmtCode, @PathParam("startYm") int startYm,
			@PathParam("categoryAtr") int categoryAtr, @PathParam("itemCd") String itemCd){
		try{
			if(this.detailFinder.getDetail(stmtCode, startYm, categoryAtr, itemCd).isPresent()){
				return this.detailFinder.getDetail(stmtCode, startYm, categoryAtr, itemCd).get();
			}
		return null;
		}
		catch(Exception ex){
			throw ex;
		}
	}
	@POST
	@Path("findCategoies/full/{layoutCd}/{startYm}")
	public List<LayoutMasterCategoryDto> getCategoriesFullData(@PathParam("layoutCd") String layoutCd, @PathParam("startYm") int startYm){
		return this.categoryFinder.getCategoriesFullData(layoutCd, startYm);
	}
	@POST
	@Path("findlayoutwithmaxstartym")
	public List<LayoutDto> getLayoutsWithMaxStartYm(){
		return this.find.getLayoutsWithMaxStartYm(AppContexts.user().companyCode());
	}
	
	@POST
	@Path("createlayout")
	public void createLayout(CreateLayoutCommand command){
		this.createLayoutData.handle(command);
	}
	@POST
	@Path("createlayouthistory")
	public void createLayoutHistory(CreateLayoutHistoryCommand command){
		this.createHistoryData.handle(command);
	}
	@POST
	@Path("updatedata")
	public void updateData(UpdateLayoutHistoryCommand command){
		this.updateData.handle(command);
	}
	@POST
	@Path("deletedata")
	public void deleteData(DeleteLayoutHistoryCommand command){
		this.deleteData.handle(command);
	}
	@POST
	@Path("register")
	public void registerLayout(RegisterLayoutCommand command){
		this.registerLayoutHandler.handle(command);
	}
	
	
}
