package nts.uk.ctx.sys.portal.infra.entity.toppagesetting;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageRoleSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class SptmtTopPageRoleSet.
 * Entity 権限別トップページ設定
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SPTMT_TOPPAGE_ROLESET")
public class SptmtTopPageRoleSet extends UkJpaEntity implements TopPageRoleSetting.MementoGetter, TopPageRoleSetting.MementoSetter {

	/** The pk. */
	@EmbeddedId
	private SptmtTopPageRoleSetPK pk;
	
	/** The version. */
    @Version
    @Column(name = "EXCLUS_VER")
    private long version;

    /** The contract cd. */
    @Basic(optional = false)
    @Column(name = "CONTRACT_CD")
    private String contractCd;
	
	@Column(name = "TOP_MENU_CD")
	private String topMenuCode;
	
	@Column(name = "LOGIN_MENU_CD")
	private String loginMenuCode;
	
	@Column(name = "LOGIN_SYSTEM")
	private int system;
	
	@Column(name = "LOGIN_MENU_CLS")
	private int menuClassification;
	
	@Column(name = "SWITCH_DATE")
	private int switchingDate;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	@Override
	public void setCompanyId(String companyID) {
		if (this.pk == null) {
			this.pk = new SptmtTopPageRoleSetPK();
		}
		this.pk.setCompanyId(companyID);
	}

	@Override
	public void setRoleSetCode(String roleSetCode) {
		if (this.pk == null) {
			this.pk = new SptmtTopPageRoleSetPK();
		}
		this.pk.setRoleSetCode(roleSetCode);
	}

	@Override
	public String getCompanyId() {
		if (this.pk != null) {
			return this.pk.getCompanyId();
		}
		return null;
	}

	@Override
	public String getRoleSetCode() {
		if (this.pk != null) {
			return this.pk.getRoleSetCode();
		}
		return null;
	}

	@Override
	public void setSwitchingDate(Integer switchingDate) {
		this.switchingDate = switchingDate;
	}

	@Override
	public Integer getSwitchingDate() {
		return this.switchingDate;
	}

}
