package nts.uk.ctx.sys.portal.app.find.webmenu.jobtitletying;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying.JobTitleTying;

/**
 * @author yennth
 * the class JobTitleTyingDto
 */
@Data
public class JobTitleTyingDto {
	/** The company id. */
	private String companyId;
	
	/** the job id */
	private String jobId;
	
	/** the web menu code */
	private String webMenuCode;
	
	/**
	 * from Domain
	 * @param jobTitleTying
	 * @return
	 */
	public static JobTitleTyingDto fromDomain(JobTitleTying jobTitleTying){
		JobTitleTyingDto jobTitleTyingDto = new JobTitleTyingDto();
		jobTitleTyingDto.companyId = jobTitleTying.getCompanyId();
		jobTitleTyingDto.jobId = jobTitleTying.getJobId();
		jobTitleTyingDto.webMenuCode = jobTitleTying.getWebMenuCode();
		return jobTitleTyingDto;
	}
}
