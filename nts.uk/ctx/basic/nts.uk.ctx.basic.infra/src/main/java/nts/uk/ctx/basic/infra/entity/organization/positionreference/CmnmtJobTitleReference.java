package nts.uk.ctx.basic.infra.entity.organization.positionreference;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CMNMT_JOB_TITLE_REF")
public class CmnmtJobTitleReference implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public CmnmtJobTitleReferencePK cmnmtJobTitleReferencePK;
	
	@Basic(optional = false)
	@Column(name ="REF_SET")
	public String referenceSettings;

	public String getReferenceSettings() {
		return referenceSettings;
	}

	public void setAuthorizationCode(String referenceSettings) {
		this.referenceSettings = referenceSettings;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
