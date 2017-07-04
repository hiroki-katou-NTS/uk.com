package nts.uk.ctx.sys.portal.dom.jobtitletying;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.enums.MenuAtr;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.enums.WebMenuSetting;
import nts.uk.ctx.sys.portal.dom.standardmenu.MenuCode;
import nts.uk.ctx.sys.portal.dom.standardmenu.MenuDisplayName;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
/**
 * @author yennth
 * The class JobTitleTying
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class JobTitleTying extends AggregateRoot{
	
	/** The company id*/
	private String companyId;
	
	/**	The job id */
	private String jobId;
	
	/**	The web menu code */
	private String webMenuCode;

	public JobTitleTying(String companyId, String jobId, String webMenuCode) {
		this.companyId = companyId;
		this.jobId = jobId;
		this.webMenuCode = webMenuCode;
	}
	
	/**
	 * @author yennth
	 * update web menu code 
	 * @param companyId
	 * @param jobId
	 * @param webMenuCode
	 * @return
	 */
	public static JobTitleTying updateWebMenuCode(String companyId, String jobId, String webMenuCode){
		return new JobTitleTying (companyId, jobId, webMenuCode);
	}
	
	public static JobTitleTying createFromJavaType(String companyId, String jobId, String webMenuCode) {
		return new JobTitleTying(companyId, jobId, webMenuCode);
	}	
}
