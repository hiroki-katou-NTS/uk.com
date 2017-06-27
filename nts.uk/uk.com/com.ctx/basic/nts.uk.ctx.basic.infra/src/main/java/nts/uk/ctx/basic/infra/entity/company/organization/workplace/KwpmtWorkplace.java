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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
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
	
	@Size(max = 120)
	@Column(name = "WPL_NAME")
	private String wplname;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 120)
	@Column(name = "WPL_CD")
	private String wplcd;
	
	public KwpmtWorkplace() {
	}

	public KwpmtWorkplace(KwpmtWorkplacePK kwpmtWorkplacePK) {
		this.kwpmtWorkplacePK = kwpmtWorkplacePK;
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
	protected Object getKey() {
		return this.kwpmtWorkplacePK;
	}

}
