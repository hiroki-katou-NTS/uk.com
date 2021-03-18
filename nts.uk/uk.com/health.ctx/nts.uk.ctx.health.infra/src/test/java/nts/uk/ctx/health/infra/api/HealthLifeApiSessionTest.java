package nts.uk.ctx.health.infra.api;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import lombok.Data;
import lombok.Value;
import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.health.dom.linkage.HealthLifeApiLinkage;
import nts.uk.ctx.health.infra.api.sample.SampleOshiraseDto;

public class HealthLifeApiSessionTest {

	@Test
	public void test() {
		
		HealthLifeApiLinkage linkage = new HealthLifeApiLinkage(
				"tenantCode",
				"https://heal-test.kinjirou-e.com/healthcare.web/",
				"000000000260",
				"0");
		
		String companyCode = "0001";
		
//		HealthLifeApiSession.Context context = NtsAssert.Invoke.staticMethod(
//				HealthLifeApiSession.class,
//				"beginSession",
//				linkage,
//				companyCode);
//		
//		val requestEntity = new Request(
//				GeneralDate.ymd(2020, 1, 1).date(),
//				GeneralDate.ymd(2020, 1, 31).date(),
//				context.getHealthLifeCompanyCode());
//		
//		val response = context.post(
//				"webapi/func/linkage/uk/notice/getbyperiod",
//				requestEntity,
//				Response.class);
//		
//		response.toString();
	}

	@Value
	public static class Request {
		private final Date startDate;
		private final Date endDate;
		private final int companyCode;
	}
	
	@Data
	public static class Response {
		private List<SampleOshiraseDto> list;
	}
}
