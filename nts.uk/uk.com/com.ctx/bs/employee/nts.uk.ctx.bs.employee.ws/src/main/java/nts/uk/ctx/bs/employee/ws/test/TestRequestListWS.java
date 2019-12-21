package nts.uk.ctx.bs.employee.ws.test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.employment.DataEmployeeExport;
import nts.uk.ctx.bs.employee.app.find.employment.EmployeeBasicInfoExport;
import nts.uk.ctx.bs.employee.app.find.employment.EmploymentFinder;
import nts.uk.ctx.bs.employee.app.find.employment.EmploymentInfoExport;
import nts.uk.ctx.bs.employee.app.find.employment.ObjectParam;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Path("test-request-list")
@Produces(MediaType.APPLICATION_JSON)
public class TestRequestListWS {

	@Inject
	private EmploymentFinder finder;

	@POST
	@Path("/638") // test service
	public List<EmployeeBasicInfoExport> get638(ParamTest1 param) {
		List<ObjectParam> listObjParam = Arrays.asList(new ObjectParam(param.employmentCode1, new DatePeriod(param.birthDayStart1, param.endDayStart1)),
													   new ObjectParam(param.employmentCode1, new DatePeriod(param.birthDayStart1, param.endDayStart1)),
													   new ObjectParam(param.employmentCode1, new DatePeriod(param.birthDayStart1, param.endDayStart1)));
		List<EmployeeBasicInfoExport> result = finder.getEmploymentBasicInfo(listObjParam, param.baseDate, param.cid);
		return result;
	}
	
	@POST
	@Path("/639") // test service
	public List<EmploymentInfoExport> get639(String cid, Boolean getEmploymentNameParam, Boolean getEmpExternalCodeParam,
			Boolean getMemoParam, Boolean getempCommonMasterIDParam,
			Boolean getempCommonMasterItemIDParam) {
		List<EmploymentInfoExport> result = finder.getEmploymentInfo(cid, Optional.of(getEmploymentNameParam), Optional.of(getEmpExternalCodeParam), 
				Optional.of(getMemoParam), Optional.of(getempCommonMasterIDParam), Optional.of(getempCommonMasterItemIDParam));
		return result;
	}
	
	@POST
	@Path("/640") // test service
	public List<DataEmployeeExport> get640(List<String> listSId , GeneralDate startDate, GeneralDate endDate) {
		List<DataEmployeeExport> result = finder.getEmployeeInfo( listSId ,  new DatePeriod(startDate, endDate));
		return result;
	}
	
}
