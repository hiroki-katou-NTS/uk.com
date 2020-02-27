package nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.MenuApprovalSettings;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
@NoArgsConstructor
public class MenuApprovalSettingsDto {

	private String cId;

	private Integer workId;

	private String programId;

	private String screenId;

	private Boolean useApproval;

	private Boolean availableAprRoot;

	private Boolean availableAprWork1;

	private Boolean availableAprWork2;
	
	private Integer rptLayoutId;

	private String apr1Sid;

	private String apr1Scd;

	private String apr1BusinessName;

	private String app1Devcd;

	private String app1DevName;

	private String app1Poscd;

	private String app1PosName;

	private String apr2Sid;

	private String apr2Scd;

	private String apr2BusinessName;

	private String app2Devcd;

	private String app2DevName;

	private String app2Poscd;

	private String app2PosName;

	public MenuApprovalSettingsDto(MenuApprovalSettings domain) {
		super();
		this.cId = domain.getCId();
		this.workId = domain.getWorkId();
		this.rptLayoutId = domain.getRptLayoutId();
		this.programId = domain.getProgramId();
		this.screenId = domain.getScreenId();
		this.useApproval = domain.isUseApproval();
		this.availableAprRoot = domain.isAvailableAprRoot();
		this.availableAprWork1 = domain.isAvailableAprWork1();
		this.availableAprWork2 = domain.isAvailableAprWork2();
		this.apr1Sid = domain.getApr1Sid().orElse(null);
		this.apr1Scd = domain.getApr1Scd().orElse(null);
		this.apr1BusinessName = domain.getApr1BusinessName().orElse(null);
		this.app1Devcd = domain.getApp1Devcd().orElse(null);
		this.app1DevName = domain.getApp1DevName().orElse(null);
		this.app1Poscd = domain.getApp1Poscd().orElse(null);
		this.app1PosName = domain.getApp1PosName().orElse(null);
		this.apr2Sid = domain.getApr2Sid().orElse(null);
		this.apr2Scd = domain.getApr2Scd().orElse(null);
		this.apr2BusinessName = domain.getApr2BusinessName().orElse(null);
		this.app2Devcd = domain.getApp2Devcd().orElse(null);
		this.app2DevName = domain.getApp2DevName().orElse(null);
		this.app2Poscd = domain.getApp2Poscd().orElse(null);
		this.app2PosName = domain.getApp2PosName().orElse(null);
	}
	
	public MenuApprovalSettings toDomain() {
		return new MenuApprovalSettings(
				AppContexts.user().companyId(), 
				this.workId,  
				this.programId, 
				this.screenId, 
				this.useApproval, 
				this.availableAprRoot, 
				this.availableAprWork1, 
				this.availableAprWork2, 
				this.rptLayoutId,
				this.apr1Sid, 
				this.apr1Scd, 
				this.apr1BusinessName, 
				this.app1Devcd, 
				this.app1DevName, 
				this.app1Poscd, 
				this.app1PosName, 
				this.apr2Sid, 
				this.apr2Scd, 
				this.apr2BusinessName, 
				this.app2Devcd, 
				this.app2DevName, 
				this.app2Poscd, 
				this.app2PosName
			);
	}

}
