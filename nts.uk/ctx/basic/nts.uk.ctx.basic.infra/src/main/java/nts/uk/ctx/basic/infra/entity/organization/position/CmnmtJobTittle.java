package nts.uk.ctx.basic.infra.entity.organization.position;

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
@Table(name = "CMNMT_JOB_TITLE")
public class CmnmtJobTittle implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public CmnmtJobTittlePK cmnmtJobTittlePK;
	
	@Basic(optional = false)
	@Column(name ="STR_YM")
	public int strYm;

	@Basic(optional = false)
	@Column(name = "END_YM")
	public int endYm;

	@Basic(optional = false)
	@Column(name = "EXCLUS_VER")
	public int exclusiveVersion;
	
	@Basic(optional = false)
	@Column(name = "MEMO")
	public int memo;
	
}
