package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSet;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class JobtitleSearchSetCommand {
	/**職位ID	 */
	private String jobId;
	
	private int searchSetFlg;
	
	public JobtitleSearchSet toDomain(String jobId){
		String companyId = AppContexts.user().companyId();
		JobtitleSearchSet jobtitle = JobtitleSearchSet.createSimpleFromJavaType(companyId, jobId, this.getSearchSetFlg());
		return jobtitle;
	}
}
