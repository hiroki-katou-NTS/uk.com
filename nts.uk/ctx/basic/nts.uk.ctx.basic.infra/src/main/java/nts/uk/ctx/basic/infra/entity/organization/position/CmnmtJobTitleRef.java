package nts.uk.ctx.basic.infra.entity.organization.position;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="CMNMT_JOB_TITLE_REF")
public class CmnmtJobTitleRef {
	
	@EmbeddedId
    public CmnmtJobTitleRefPK cmnmtJobTitleRefPK;


	@Basic(optional = false)
	@Column(name = "REF_SET")
	public String referenceSettings;
	
	
}