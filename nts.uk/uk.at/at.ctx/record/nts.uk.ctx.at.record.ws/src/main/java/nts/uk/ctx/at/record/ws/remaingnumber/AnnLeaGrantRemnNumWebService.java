package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.remainingnumber.annleagrtremnum.AddAnnLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.annleagrtremnum.AnnLeaGrantRemnNumCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.annleagrtremnum.DeleteAnnLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.annleagrtremnum.DeleteLeaGrantRemnNumCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.annleagrtremnum.UpdateAnnLeaCommandHandler;
import nts.uk.ctx.at.record.app.find.remainingnumber.annleagrtremnum.AnnLeaGrantRemnNumDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.annleagrtremnum.AnnLeaGrantRemnNumFinder;
import nts.uk.ctx.at.record.app.find.remainingnumber.annualleave.remainnumber.AnnualLeaveNumberFinder;
import nts.uk.ctx.at.record.app.find.remainingnumber.annualleave.remainnumber.RemainNumberInfoDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.rervleagrtremnum.ResvLeaRemainNumberFinder;

@Path("at/record/remainnumber/annlea")
@Produces("application/json")
public class AnnLeaGrantRemnNumWebService extends WebService{

	
	@Inject
	private AnnLeaGrantRemnNumFinder finder;
	
	@Inject
	private AddAnnLeaCommandHandler addHandler;
	
	@Inject
	private UpdateAnnLeaCommandHandler updateHandler;
	
	@Inject DeleteAnnLeaCommandHandler deleteHandler;
	
	@Inject
	private AnnualLeaveNumberFinder annLeaNumberFinder;
	
	@Inject
	private ResvLeaRemainNumberFinder resvLeaNumberFinder;
	
	@POST
	@Path("getAnnLea/{empId}")
	public List<AnnLeaGrantRemnNumDto> findAnnLeaGrantRemnNum(@PathParam("empId") String employeeId) {
		return finder.getListData(employeeId);
	}
	
	@POST
	@Path("getAnnLeaNumber/{empId}")
	public RemainNumberInfoDto getAnnLeaNum(@PathParam("empId") String employeeId) {
		return annLeaNumberFinder.getAnnualLeaveNumber(employeeId);
	}
	
	@POST
	@Path("getResvLeaNumber/{empId}")
	public String getResvLeaNumber(@PathParam("empId") String employeeId) {
		return resvLeaNumberFinder.getResvLeaRemainNumber(employeeId);
	}
	
	@POST
	@Path("getAnnLeaByCheckState")
	public List<AnnLeaGrantRemnNumDto> getAnnLeaByCheckState(String employeeId, Boolean checkState) {
		return finder.getListDataByCheckState(employeeId,checkState);
	}
	@POST
	@Path("getDetail")
	public AnnLeaGrantRemnNumDto getDetail(String id) {
		return finder.getDetail(id);
	}

	@POST
	@Path("lostFocus")
	public Object lostFocus(GeneralDate grantDate){
		
		GeneralDate expiredDate = finder.lostFocus(grantDate);
		
		return new Object[]{expiredDate};
	}
	
	@POST
	@Path("add")
	public Object add(AnnLeaGrantRemnNumCommand command){
		String newId = addHandler.handle(command).getAddedRecordId();
		return new Object[] {newId};
	}
	
	@POST
	@Path("update")
	public void update(AnnLeaGrantRemnNumCommand command){
		updateHandler.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(DeleteLeaGrantRemnNumCommand command){
		deleteHandler.handle(command);
	}
	
	
}
