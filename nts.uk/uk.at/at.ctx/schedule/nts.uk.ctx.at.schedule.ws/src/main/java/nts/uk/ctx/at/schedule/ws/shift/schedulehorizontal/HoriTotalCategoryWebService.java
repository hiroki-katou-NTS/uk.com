package nts.uk.ctx.at.schedule.ws.shift.schedulehorizontal;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal.AddHoriTotalCategoryCommand;
import nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal.AddHoriTotalCategoryCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal.DeleteHoriTotalCategoryCommand;
import nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal.DeleteHoriTotalCategoryCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal.UpdateHoriTotalCategoryCommand;
import nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal.UpdateHoriTotalCategoryCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal.HoriCalDaySetDto;
import nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal.HoriCalDaySetFinder;
import nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal.HoriTotalCNTFinder;
import nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal.HoriTotalCNTSetDto;
import nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal.HoriTotalCategoryDto;
import nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal.HoriTotalCategoryFinder;
import nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal.Param;
import nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal.TotalEvalItemDto;
import nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal.TotalEvalItemFinder;
import nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal.TotalEvalOrderDto;
import nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal.TotalEvalOrderFinder;
@Path("at/schedule/schedulehorizontal")
@Produces("application/json")
public class HoriTotalCategoryWebService extends WebService{
	@Inject
	private HoriTotalCategoryFinder finderCate;
	@Inject
	private TotalEvalOrderFinder finderOrder;
	@Inject
	private TotalEvalItemFinder finderItem;
	@Inject 
	private HoriCalDaySetFinder finderSet;
	@Inject 
	private HoriTotalCNTFinder finderCNT;
	@Inject
	private AddHoriTotalCategoryCommandHandler add;
	@Inject
	private UpdateHoriTotalCategoryCommandHandler update;
	@Inject
	private DeleteHoriTotalCategoryCommandHandler delete;
	
	/**
	 * get all data hori total category
	 * @return
	 */
	@POST
	@Path("findAllCate")
	public List<HoriTotalCategoryDto> finderCate(){
		return this.finderCate.finder();
	}
	
	/**
	 * get all hori total cnt set
	 * @return
	 */
	@POST
	@Path("findAllCNT")
	public List<HoriTotalCNTSetDto> finderAllCNT(){
		return this.finderCNT.finderAll();
	}
	
	/**
	 * get data total eval order
	 * @param a
	 * @param b
	 * @return
	 */
	@POST
	@Path("findOrder")
	public List<TotalEvalOrderDto> finderOrder(@PathParam("a") String a, @PathParam("b") int b){
		return this.finderOrder.finder(a, b);
	}
	
	/**
	 * get all total eval item
	 * @return
	 */
	@POST
	@Path("findItem")
	public List<TotalEvalItemDto> finderItem(){
		return this.finderItem.finder();
	}
	
	/**
	 * get all hori totalCNTSet item
	 * @return
	 */
	@POST
	@Path("findCNT")
	public List<HoriTotalCNTSetDto> finderCNT(Param param){
		return this.finderCNT.finder(param);
	}
	
	/**
	 * get all HoriCalDaysSet
	 * @return
	 */
	@POST
	@Path("findSet")
	public List<HoriCalDaySetDto> finderSet(){
		return this.finderSet.finder();
	}
	
	@POST
	@Path("update")
	public void update(UpdateHoriTotalCategoryCommand aa){
		this.update.handle(aa);
	}
	
	@POST
	@Path("add")
	public void add(AddHoriTotalCategoryCommand command){
		this.add.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(DeleteHoriTotalCategoryCommand command){
		this.delete.handle(command);
	}
}
