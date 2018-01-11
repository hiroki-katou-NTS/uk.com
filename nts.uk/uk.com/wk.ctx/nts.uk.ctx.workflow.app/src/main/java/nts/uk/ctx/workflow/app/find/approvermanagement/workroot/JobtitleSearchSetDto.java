package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobtitleSearchSetDto {
	/**会社ID*/
	private String companyId;
	/**職位ID	 */
	private String jobId;
	
	private int searchSetFlg;
}
