package nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.MenuApprovalSettings;

@AllArgsConstructor
@Getter
public class BusinessApprovalSettingsDto {

	private MenuApprovalSettings menuApprovalSettings;
	
	private String rptLayoutCD;
	
	private String rptLayoutName;
	
	private String displayName;
	
	private String screenID;
	
	private Boolean useApproval;
	
	private Boolean noRankOrder;
	
}
