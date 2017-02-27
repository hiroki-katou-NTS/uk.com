package nts.uk.ctx.pr.core.infra.entity.rule.employement.processing.yearmonth;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.TableEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPDMT_STANDARD_DAY")
public class QpdmtStandardDay extends TableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public QpdmtStandardDayPK qpdmtStandardDayPk;

	@Basic(optional = false)
	@Column(name = "SOCIAL_INS_STD_YEAR_ATR")
	public Number socialInsStdYearAtr;
	
	@Basic(optional = false)
	@Column(name = "SOCIAL_INS_STD_MON")
	public Number socialInsStdMon;
	
	@Basic(optional = false)
	@Column(name = "SOCIAL_INS_STD_DAY")
	public Number socialInsStdDay;
	
	@Basic(optional = false)
	@Column(name = "EMP_INS_STD_MON")
	public Number empInsStdMon;
	
	@Basic(optional = false)
	@Column(name = "EMP_INS_STD_DAY")
	public Number empInsStdDay;
	
	@Basic(optional = false)
	@Column(name = "INCOMETAX_STD_YEAR_ATR")
	public Number incometaxStdYearAtr;
	
	@Basic(optional = false)
	@Column(name = "INCOMETAX_STD_MON")
	public Number incometaxStdMon;
	
	@Basic(optional = false)
	@Column(name = "INCOMETAX_STD_DAY")
	public Number incometaxStdDay;
	
}
