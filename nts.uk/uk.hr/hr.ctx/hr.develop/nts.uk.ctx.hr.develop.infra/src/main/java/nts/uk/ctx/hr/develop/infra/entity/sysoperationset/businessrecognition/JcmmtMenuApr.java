package nts.uk.ctx.hr.develop.infra.entity.sysoperationset.businessrecognition;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.MenuApprovalSettings;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Table(name="JCMMT_MENU_APR")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JcmmtMenuApr extends UkJpaEntity {
	
	@EmbeddedId
    public JcmmtMenuAprPk pkJcmmtMenuApr; 
	
	@Column(name = "USE_APPROVAL")
	public Integer useApproval;

	@Column(name = "AVAILABLE_APR_ROOT")
	public Integer availableAprRoot;

	@Column(name = "AVAILABLE_APR_WORK1")
	public Integer availableAprWork1;

	@Column(name = "AVAILABLE_APR_WORK2")
	public Integer availableAprWork2;
	
	@Column(name = "RPT_LAYOUT_ID")
	public Integer rptLayoutId;

	@Column(name = "APR1_SID")
	public String apr1Sid;

	@Column(name = "APR1_SCD")
	public String apr1Scd;

	@Column(name = "APR1_BUSINESS_NAME")
	public String apr1BusinessName;

	@Column(name = "APP1_DEVCD")
	public String app1Devcd;

	@Column(name = "APP1_DEV_NAME")
	public String app1DevName;

	@Column(name = "APP1_POSCD")
	public String app1Poscd;

	@Column(name = "APP1_POS_NAME")
	public String app1PosName;

	@Column(name = "APR2_SID")
	public String apr2Sid;

	@Column(name = "APR2_SCD")
	public String apr2Scd;

	@Column(name = "APR2_BUSINESS_NAME")
	public String apr2BusinessName;

	@Column(name = "APP2_DEVCD")
	public String app2Devcd;

	@Column(name = "APP2_DEV_NAME")
	public String app2DevName;

	@Column(name = "APP2_POSCD")
	public String app2Poscd;

	@Column(name = "APP2_POS_NAME")
	public String app2PosName;
	
	@Override
	protected Object getKey() {
		return pkJcmmtMenuApr;
	}
	
	public MenuApprovalSettings toDomain() {
		return new MenuApprovalSettings(
				this.pkJcmmtMenuApr.cId, 
				this.pkJcmmtMenuApr.workId, 
				this.pkJcmmtMenuApr.programId, 
				this.pkJcmmtMenuApr.screenId, 
				this.useApproval == 1, 
				this.availableAprRoot == 1, 
				this.availableAprWork1 == 1, 
				this.availableAprWork2 == 1,
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
	
	public static JcmmtMenuApr toEntity(MenuApprovalSettings domain) {
		return new JcmmtMenuApr( 
				new JcmmtMenuAprPk(domain.getCId(), domain.getWorkId(), domain.getProgramId(), domain.getScreenId()),
				domain.isUseApproval()? 1: 0, 
				domain.isAvailableAprRoot()? 1: 0, 
				domain.isAvailableAprWork1()? 1: 0, 
				domain.isAvailableAprWork2()? 1:0, 
				domain.getRptLayoutId(), 
				domain.getApr1Sid().orElse(null), 
				domain.getApr1Scd().orElse(null), 
				domain.getApr1BusinessName().orElse(null), 
				domain.getApp1Devcd().orElse(null), 
				domain.getApp1DevName().orElse(null), 
				domain.getApp1Poscd().orElse(null), 
				domain.getApp1PosName().orElse(null), 
				domain.getApr2Sid().orElse(null), 
				domain.getApr2Scd().orElse(null), 
				domain.getApr2BusinessName().orElse(null), 
				domain.getApp2Devcd().orElse(null), 
				domain.getApp2DevName().orElse(null), 
				domain.getApp2Poscd().orElse(null), 
				domain.getApp2PosName().orElse(null)
			);
	}
}
