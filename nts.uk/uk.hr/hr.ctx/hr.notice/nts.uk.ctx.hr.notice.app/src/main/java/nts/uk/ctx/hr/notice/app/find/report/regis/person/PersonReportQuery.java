package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonReportQuery {
	// 申請日
	private DateQuery appDate;
	// 届出名
	private Integer reportId;
	// 入力者・申請者
	private String inputName;
	// 承認状況
	private Integer approvalStatus;
	// 承認届出
	private boolean approvalReport;

}
