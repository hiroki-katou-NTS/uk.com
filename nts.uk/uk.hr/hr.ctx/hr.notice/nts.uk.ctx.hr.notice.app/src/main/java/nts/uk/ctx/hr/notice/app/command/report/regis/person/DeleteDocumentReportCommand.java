package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class DeleteDocumentReportCommand {
	 String cid; 
	 String fileId; 	
	 String reportId; //届出ID
	 String missingDocName;
}
