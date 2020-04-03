package nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.BusinessApprovalSettingsDto;

@Getter
@Setter
@NoArgsConstructor
public class MenuApprovalSettingsInforDto {

	private MenuApprovalSettingsDto menuApprovalSettings;
	
	private String rptLayoutCD;
	
	private String rptLayoutName;
	
	private String displayName;
	
	private String screenID;
	
	private Boolean useApproval;
	
	private Boolean noRankOrder;

	public MenuApprovalSettingsInforDto(BusinessApprovalSettingsDto domain) {
		super();
		this.menuApprovalSettings = new MenuApprovalSettingsDto(domain.getMenuApprovalSettings());
		this.rptLayoutCD = domain.getRptLayoutCD();
		this.rptLayoutName = domain.getRptLayoutName();
		this.displayName = domain.getDisplayName();
		this.screenID = domain.getScreenID();
		this.useApproval = domain.getUseApproval();
		this.noRankOrder = domain.getNoRankOrder();
	}
	
	public BusinessApprovalSettingsDto toDomain() {
		return new BusinessApprovalSettingsDto(
				this.menuApprovalSettings.toDomain(), 
				this.rptLayoutCD, 
				this.rptLayoutName, 
				this.displayName, 
				this.screenID, 
				this.useApproval, 
				this.noRankOrder);
	}
	
}
