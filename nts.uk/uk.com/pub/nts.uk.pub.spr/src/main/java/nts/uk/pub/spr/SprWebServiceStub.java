package nts.uk.pub.spr;

import java.util.LinkedHashMap;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.val;

// @Path("public/spr")
public class SprWebServiceStub {

	/*
	@POST
	@Path("01/loginfromspr")
	@Produces("text/html")
	public String loginFromSpr(
			@FormParam("menu") String menuCode,
			@FormParam("loginemployeeCode") String loginEmployeeCode,
			@FormParam("employeeCode") String targetEmployeeCode,
			@FormParam("starttime") String startTime,
			@FormParam("endtime") String endTime,
			@FormParam("date") String targetDate,
			@FormParam("selecttype") String selectType,
			@FormParam("applicationID") String applicationID,
			@FormParam("reason") String reason,
			@FormParam("stampProtection") String stampProtection) {
		
		val paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("menu", SprStubHelper.formatParam(menuCode));
		paramsMap.put("loginemployeeCode", SprStubHelper.formatParam(loginEmployeeCode));
		paramsMap.put("employeeCode", SprStubHelper.formatParam(targetEmployeeCode));
		paramsMap.put("starttime", SprStubHelper.formatParamTime(startTime));
		paramsMap.put("endtime", SprStubHelper.formatParamTime(endTime));
		paramsMap.put("date", SprStubHelper.formatParam(targetDate));
		paramsMap.put("selecttype", SprStubHelper.formatParam(selectType));
		paramsMap.put("reason", SprStubHelper.formatParam(reason));
		paramsMap.put("stampProtection", SprStubHelper.formatParam(stampProtection));
		
		val html = new StringBuilder()
				.append("<!DOCTYPE html>")
				.append("<html><body>");
		
		paramsMap.forEach((name, value) -> {
			html.append(name + " : " + value + "<br/>");
		});
		
		html.append("</body></html>");
		
		return html.toString();
	}
	
	@POST
	@Path("02/getapplicationstatus")
	@Produces("application/json")
	public SprStubHelper.StatusContainer<SprStubHelper.RequestApplicationStatusResult> getRequestApplicationStatus(
			SprStubHelper.ApplicationStatusQuery query) {
		
		// validate date string
		query.getDatePeriod();
		
		return new SprStubHelper.StatusContainer<>(SprStubHelper.RequestApplicationStatusResult.create());
	}
	
	@POST
	@Path("03/getstatusofdaily")
	@Produces("application/json")
	public SprStubHelper.StatusContainer<SprStubHelper.RecordApplicationStatusResult> getRecordApplicationStatus(
			SprStubHelper.ApplicationStatusQuery query) {

		// validate date string
		query.getDatePeriod();
		
		return new SprStubHelper.StatusContainer<>(SprStubHelper.RecordApplicationStatusResult.create());
	}
	
	@POST
	@Path("04/getapprovalroot")
	@Produces("application/json")
	public SprStubHelper.EmployeesContainer<SprStubHelper.ApplicationTargetResult> getApprovalRoot(
			SprStubHelper.ApplicationTargetQuery query) {

		return new SprStubHelper.EmployeesContainer<>(SprStubHelper.ApplicationTargetResult.create());
	}
	
	*/
}