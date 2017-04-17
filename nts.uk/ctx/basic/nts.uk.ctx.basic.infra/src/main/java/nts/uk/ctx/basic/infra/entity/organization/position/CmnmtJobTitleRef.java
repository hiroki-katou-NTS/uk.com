package nts.uk.ctx.basic.infra.entity.organization.position;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="CMNMT_JOB_TITLE_REF")
public class CmnmtJobTitleRef {
	
	@EmbeddedId
    public CmnmtJobTitleRefPK cmnmtJobTitlePK;

//	@Basic(optional = false)
//	@Column(name = "EXCLUS_VER")
//	public int exclusVersion;
//	
	@Basic(optional = false)
	@Column(name = "REF_SET")
	public String referenceSettings;
	
	
}