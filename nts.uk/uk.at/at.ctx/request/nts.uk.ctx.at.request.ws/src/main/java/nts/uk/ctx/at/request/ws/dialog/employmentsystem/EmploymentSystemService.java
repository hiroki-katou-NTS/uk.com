package nts.uk.ctx.at.request.ws.dialog.employmentsystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import lombok.Value;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.dialog.employmentsystem.EmployeeBasicInfoDto;
import nts.uk.ctx.at.request.app.find.dialog.employmentsystem.EmploymentSystemFinder;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecGenerationDigestionHis;

@Path("at/request/dialog/employmentsystem")
@Produces("application/json")
public class EmploymentSystemService extends WebService {
	@Inject
	EmploymentSystemFinder employeeFinder;
	
	// Code for KDL005
	
	@POST
	@Path("getEmployeeList")
	public DataParam getEmployeeList(EmployeeParam param)
	{		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.now();
		String baseDate = dtf.format(localDate);
		
		// アルゴリズム「代休確認ダイア起動」を実行する
		List<EmployeeBasicInfoDto> employeeBasicInfo = employeeFinder.getEmployee(param.getEmployeeIds(), param.getBaseDate().isEmpty() ? baseDate : param.getBaseDate());
				
		DataParam result = new DataParam(employeeBasicInfo, param.getBaseDate().isEmpty() ? baseDate : param.getBaseDate());
		
		return result;
	}
	
	
	// Code for KDL009
	
	@POST
	@Path("getEmployee")
	public DataParam getEmployee(EmployeeParam param)
	{		
		// アルゴリズム「振休確認ダイアログ開始」を実行する
		List<EmployeeBasicInfoDto> employeeBasicInfo = employeeFinder.getEmployee(param.getEmployeeIds(), param.getBaseDate());
		
		DataParam result = new DataParam(employeeBasicInfo, param.getBaseDate());
		
		return result;
	}
	
	/**
	 * アルゴリズム「振休残数情報の取得」を実行する
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	@POST
	@Path("getAbsRecGenDigesHis/{employeeId}/{baseDate}")
	public AbsRecGenerationDigestionHis getAbsRecGenDigesHis(@PathParam("employeeId") String employeeId, @PathParam("baseDate") String baseDate)
	{		
		// 振休残数情報の取得
		return employeeFinder.getAbsRecGenDigesHis(employeeId, baseDate);
	}
}

@Value
class DataParam{
	List<EmployeeBasicInfoDto> employeeBasicInfo;
	String baseDate;
}

@Value
class EmployeeParam{
	 List<String> employeeIds;
	 String baseDate;
}