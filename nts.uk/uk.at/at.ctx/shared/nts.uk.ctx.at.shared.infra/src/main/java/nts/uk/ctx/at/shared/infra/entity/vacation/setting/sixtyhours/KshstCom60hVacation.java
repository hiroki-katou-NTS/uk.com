/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Setter
@Getter
@Entity
@Table(name = "KSHST_COM_60H_VACATION")
public class KshstCom60hVacation extends KshstSixtyHourVacationSetting implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 17)
	@Column(name = "CID")
	private String cid;
	
	public KshstCom60hVacation() {
		super();
	}

	public KshstCom60hVacation(String cid) {
		this.cid = cid;
	}


	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshstCom60hVacation)) {
			return false;
		}
		KshstCom60hVacation other = (KshstCom60hVacation) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.KshstCom60hVacation[ cid=" + cid + " ]";
	}

	@Override
	protected Object getKey() {
		return this.cid;
	}

}
