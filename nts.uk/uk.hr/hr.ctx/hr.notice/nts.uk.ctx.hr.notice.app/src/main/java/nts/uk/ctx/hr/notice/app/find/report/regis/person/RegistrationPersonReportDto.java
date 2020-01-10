package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;

@Builder
@Data
public class RegistrationPersonReportDto {

	private static final String DATEFORMAT = "yyyy/MM/dd";

	private String rootSateId; // ルートインスタンスID
	private int reportID; // 届出ID
	private boolean approvalReport;
	private String reportName; // 届出名
	private String reportDetail; // 届出内容
	private int aprStatus;// 承認状況
	private String inputBussinessName; // 入力者表示氏名
	private String inputDate;// 入力日
	private String appBussinessName;// 申請者表示氏名
	private String appDate;// 申請日

	public static RegistrationPersonReportDto fromDomain(RegistrationPersonReport domain, boolean approvalReport) {
		return RegistrationPersonReportDto.builder().reportID(domain.getReportID())
				.appBussinessName(domain.getAppBussinessName()).reportName(domain.getReportName())
				.appDate(domain.getAppDate().toString(DATEFORMAT)).reportDetail(domain.getReportDetail())
				.inputBussinessName(domain.getInputBussinessName())
				.inputDate(domain.getInputDate().toString(DATEFORMAT)).aprStatus(domain.getAprStatus().value)
				.rootSateId(domain.getRootSateId()).approvalReport(approvalReport).build();
	}
}
