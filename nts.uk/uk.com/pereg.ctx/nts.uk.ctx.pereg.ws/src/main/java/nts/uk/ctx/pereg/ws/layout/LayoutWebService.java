package nts.uk.ctx.pereg.ws.layout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.remainingnumber.annleagrtremnum.AnnualLeaveNumberFinder;
import nts.uk.ctx.at.record.app.find.remainingnumber.rervleagrtremnum.ResvLeaRemainNumberFinder;
import nts.uk.ctx.at.record.dom.remainingnumber.otherholiday.OtherHolidayInfoService;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainService;
import nts.uk.ctx.pereg.app.command.addemployee.AddEmployeeCommand;
import nts.uk.ctx.pereg.app.find.layout.RegisterLayoutFinder;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.NewLayoutDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.ctx.pereg.app.find.processor.PeregProcessor;
import nts.uk.shr.pereg.app.find.PeregQuery;

/**
 * @author sonnlb
 *
 */
@Path("ctx/pereg/layout")
@Produces(MediaType.APPLICATION_JSON)
public class LayoutWebService extends WebService {

	@Inject
	private RegisterLayoutFinder layoutFinder;
	
	@Inject
	private PeregProcessor layoutProcessor;

	@Inject 
	private SpecialLeaveGrantRemainService specialLeaveGrantRemainService;
	
	@Inject
	private OtherHolidayInfoService otherHolidayInfoService;
	
	@Inject
	private AnnualLeaveNumberFinder annLeaNumberFinder;
	
	@Inject
	private ResvLeaRemainNumberFinder resvLeaNumberFinder;
	
	
	
	@Path("getByCreateType")
	@POST
	public NewLayoutDto getByCreateType(AddEmployeeCommand command) {
		return this.layoutFinder.getLayoutByCreateType(command);
	}
	
	/**
	 * get category and it's children
	 * @author xuan vinh
	 * 
	 * @param ctgId
	 * @return
	 */
	
	@Path("find/getctgtab/{categoryid}")
	@POST
	public List<PerInfoCtgFullDto> getCtgTab(@PathParam("categoryid")String ctgId){
		return this.layoutProcessor.getCtgTab(ctgId);
	}
	
	/**
	 * @author xuan vinh
	 * @param query
	 * @return
	 */
	
	@Path("find/gettabdetail")
	@POST
	public EmpMaintLayoutDto getTabDetail(PeregQuery query){
		return this.layoutProcessor.getCategoryChild(query);
	}
	
	/**
	 * @author xuan vinh
	 * @param query
	 * @return
	 */
	
	@Path("find/gettabsubdetail")
	@POST
	public EmpMaintLayoutDto getTabSubDetail(PeregQuery query){
		return this.layoutProcessor.getSubDetailInCtgChild(query);
	}
	
	@Path("calDayTime/{sid}/{specialCD}")
	@POST
	public Object calDayTime(@PathParam("sid")String sid , @PathParam("specialCD")int specialCD){
		String dayTime = specialLeaveGrantRemainService.calDayTime(sid, specialCD);
		return new Object[] {dayTime};
	}
	
	@Path("checkEnableRemainDays")
	@POST
	public Object checkEnableRemainDays(String sid){
		boolean result = otherHolidayInfoService.checkEnableLeaveMan(sid);
		return new Object[] {result};
	}
	
	@Path("checkEnableRemainLeft")
	@POST
	public Object checkEnableRemainLeft(String sid, int specialCD){
		boolean result = otherHolidayInfoService.checkEnablePayout(sid);
		return new Object[] {result};
	}
	
	@POST
	@Path("getAnnLeaNumber/{sid}")
	public Object getAnnLeaNum(@PathParam("sid") String employeeId) {
		String dayNumber = annLeaNumberFinder.getAnnualLeaveNumber(employeeId);
		return new Object[] {dayNumber};
	}
	
	@POST
	@Path("getResvLeaNumber/{sid}")
	public Object getResvLeaNumber(@PathParam("sid") String employeeId) {
		String dayNumber = resvLeaNumberFinder.getResvLeaRemainNumber(employeeId);
		return new Object[] {dayNumber};
	}
}
