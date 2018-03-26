package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.AddResvLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.AddResvLeaRemainCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.UpdateResvLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.UpdateResvLeaRemainCommand;
import nts.uk.ctx.at.record.app.find.remainingnumber.rervleagrtremnum.ResvLeaGrantRemNumDto;
import nts.uk.ctx.at.record.dom.remainingnumber.base.LeaveExpirationStatus;

@Path("record/remainnumber/resv-lea")
@Produces("application/json")
public class ResvLeaGrantRemNumWebService extends WebService{

	
	//@Inject
	//private AnnLeaGrantRemnNumFinder finder;
	
	@Inject
	private AddResvLeaCommandHandler addHandler;
	
	@Inject
	private UpdateResvLeaCommandHandler updateHandler;
	
	@POST
	@Path("get-resv-lea/{empId}")
	public List<ResvLeaGrantRemNumDto> getAll(){
		return getData();
	}
	/*public List<AnnLeaGrantRemnNumDto> getAll() {
		List<AnnLeaGrantRemnNumDto> datatest = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			AnnLeaGrantRemnNumDto item = new AnnLeaGrantRemnNumDto(GeneralDate.fromString("2014/02/0"+i, "yyyy/MM/dd"),  GeneralDate.fromString("2014/03/0"+i, "yyyy/MM/dd"), 
					0, Double.parseDouble("1.4"), i, Double.parseDouble("1.4" + i), i, Double.parseDouble("2.5" + i), i);
			datatest.add(item);
		}
		return datatest;
	}*/
	/*public List<SpecialLeaveGrantDto> getAll() {
		List<SpecialLeaveGrantDto> datatest = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			SpecialLeaveGrantDto dto = SpecialLeaveGrantDto.createFromDomain(SpecialLeaveGrantRemainingData.createFromJavaType("", "", i, 
					GeneralDate.fromString("2018/03/0"+i, "yyyy/MM/dd"),
					GeneralDate.fromString("2018/04/0"+i, "yyyy/MM/dd"), 0, 0, 10, 12, 14.0, 15, 16.0, 17, 18, 19.0, 20));
			datatest.add(dto);
		}
		
		//return finder.getListData(sid, ctgcode);
		// @PathParam("sid") String sid ,@PathParam("ctgcd") int ctgcode
		return datatest;
	}*/
	
	@POST
	@Path("add")
	public void add(AddResvLeaRemainCommand command) {
		addHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(UpdateResvLeaRemainCommand command) {
		updateHandler.handle(command);
	}
	
	private List<ResvLeaGrantRemNumDto> getData(){
		List<ResvLeaGrantRemNumDto> lst = new ArrayList<>();
		for(int i = 1; i < 10; i++) {
			String grantDate = "2018/06/0" + i;
			String deadline = "2018/05/0"+i;
			lst.add(new ResvLeaGrantRemNumDto(GeneralDate.fromString(grantDate, "yyyy/MM/dd"), GeneralDate.fromString(deadline, "yyyy/MM/dd"), 
					EnumAdaptor.valueOf(i%2==0?1: 0, LeaveExpirationStatus.class), Double.parseDouble(i + ""), Double.parseDouble(i + ""), Double.parseDouble(i + ""), Double.parseDouble(i + "")));
		}
		return lst;
	}
	
}
