/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "KMFMT_COMPENS_LEAVE_EMP")
public class KmfmtCompensLeaveEmp extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KmfmtCompensLeaveEmpPK kmfmtCompensLeaveEmpPK;
    @Column(name = "MANAGE")
    private Integer manage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXPIRE_TIME")
    private Integer expireTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PREEMP_PERMIT")
    private Integer preempPermit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME_MANAGE")
    private Integer timeManage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DIGESTIVE_UNIT")
    private Integer digestiveUnit;

    public KmfmtCompensLeaveEmp() {
    }

    public KmfmtCompensLeaveEmp(KmfmtCompensLeaveEmpPK kmfmtCompensLeaveEmpPK) {
        this.kmfmtCompensLeaveEmpPK = kmfmtCompensLeaveEmpPK;
    }

    public KmfmtCompensLeaveEmp(KmfmtCompensLeaveEmpPK kmfmtCompensLeaveEmpPK, Integer manage, Integer expireTime, Integer preempPermit, Integer timeManage, Integer digestiveUnit) {
        this.kmfmtCompensLeaveEmpPK = kmfmtCompensLeaveEmpPK;
        this.manage = manage;
        this.expireTime = expireTime;
        this.preempPermit = preempPermit;
        this.timeManage = timeManage;
        this.digestiveUnit = digestiveUnit;
    }

    public KmfmtCompensLeaveEmp(String cid, String empCd) {
        this.kmfmtCompensLeaveEmpPK = new KmfmtCompensLeaveEmpPK(cid, empCd);
    }

    public KmfmtCompensLeaveEmpPK getKmfmtCompensLeaveEmpPK() {
        return kmfmtCompensLeaveEmpPK;
    }

    public void setKmfmtCompensLeaveEmpPK(KmfmtCompensLeaveEmpPK kmfmtCompensLeaveEmpPK) {
        this.kmfmtCompensLeaveEmpPK = kmfmtCompensLeaveEmpPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kmfmtCompensLeaveEmpPK != null ? kmfmtCompensLeaveEmpPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KmfmtCompensLeaveEmp)) {
            return false;
        }
        KmfmtCompensLeaveEmp other = (KmfmtCompensLeaveEmp) object;
        if ((this.kmfmtCompensLeaveEmpPK == null && other.kmfmtCompensLeaveEmpPK != null) || (this.kmfmtCompensLeaveEmpPK != null && !this.kmfmtCompensLeaveEmpPK.equals(other.kmfmtCompensLeaveEmpPK))) {
            return false;
        }
        return true;
    }

	@Override
	protected Object getKey() {
		return this.kmfmtCompensLeaveEmpPK;
	}
}
