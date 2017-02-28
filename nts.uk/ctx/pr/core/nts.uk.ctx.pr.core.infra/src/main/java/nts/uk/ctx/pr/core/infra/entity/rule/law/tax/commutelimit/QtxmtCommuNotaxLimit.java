package nts.uk.ctx.pr.core.infra.entity.rule.law.tax.commutelimit;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "QTXMT_COMMU_NOTAX_LIMIT")
public class QtxmtCommuNotaxLimit {
	
	@EmbeddedId
	public QtxmtCommuNotaxLimitPK qtxmtCommuNotaxLimitPK;
	
	
	@Basic(optional = false)
	@Column(name = "COMMU_NOTAX_LIMIT_NAME")
	public String commuNotaxLimitName;

	@Basic(optional = false)
	@Column(name = "COMMU_NOTAX_LIMIT_VALUE")
	public BigDecimal commuNotaxLimitValue;
	
	@Basic(optional = false)
	@Column(name = "EXCLUS_VER")
	public int exclusVer;
}
