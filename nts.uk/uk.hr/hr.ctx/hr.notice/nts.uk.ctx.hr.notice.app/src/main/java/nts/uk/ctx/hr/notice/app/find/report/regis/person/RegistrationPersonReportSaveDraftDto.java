/**
 * 
 */
package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationPersonReportSaveDraftDto  {
	
	private int reportID; // 届出ID
	private int  reportLayoutID; // 個別届出種類ID
	private String reportCode; // 届出コード
	private String reportName; // 届出名
	private String draftSaveDate;//下書き保存日
	private String missingDocName;//不足書類名
	

}
