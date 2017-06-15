/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KWPMT_WORKPLACE")
@XmlRootElement

@Getter
@Setter
public class KwpmtWorkplace extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	protected KwpmtWorkplacePK kwpmtWorkplacePK;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 120)
	@Column(name = "WKPCD")
	private String wkpcd;
	
	@Size(max = 120)
	@Column(name = "WKPNAME")
	private String wkpname;
	
	@Column(name = "STR_D")
	@Temporal(TemporalType.TIMESTAMP)
	private GeneralDate strD;
	
	@Column(name = "END_D")
	@Temporal(TemporalType.TIMESTAMP)
	private GeneralDate endD;

	public KwpmtWorkplace() {
	}

	public KwpmtWorkplace(KwpmtWorkplacePK kwpmtWorkplacePK) {
		this.kwpmtWorkplacePK = kwpmtWorkplacePK;
	}

	public KwpmtWorkplace(KwpmtWorkplacePK kwpmtWorkplacePK, int exclusVer, String wkpcd) {
		this.kwpmtWorkplacePK = kwpmtWorkplacePK;
		this.wkpcd = wkpcd;
	}

	public KwpmtWorkplace(String cid, String wkpid) {
		this.kwpmtWorkplacePK = new KwpmtWorkplacePK(cid, wkpid);
	}

	public KwpmtWorkplacePK getKwpmtWorkplacePK() {
		return kwpmtWorkplacePK;
	}

	public void setKwpmtWorkplacePK(KwpmtWorkplacePK kwpmtWorkplacePK) {
		this.kwpmtWorkplacePK = kwpmtWorkplacePK;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kwpmtWorkplacePK != null ? kwpmtWorkplacePK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof KwpmtWorkplace)) {
			return false;
		}
		KwpmtWorkplace other = (KwpmtWorkplace) object;
		if ((this.kwpmtWorkplacePK == null && other.kwpmtWorkplacePK != null)
				|| (this.kwpmtWorkplacePK != null && !this.kwpmtWorkplacePK.equals(other.kwpmtWorkplacePK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.KwpmtWorkplace[ kwpmtWorkplacePK=" + kwpmtWorkplacePK + " ]";
	}

	@Override
	protected Object getKey() {
		return this.kwpmtWorkplacePK;
	}

}
