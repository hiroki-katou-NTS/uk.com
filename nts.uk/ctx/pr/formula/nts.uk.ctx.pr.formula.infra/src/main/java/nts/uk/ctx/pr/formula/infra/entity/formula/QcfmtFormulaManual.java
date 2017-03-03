package nts.uk.ctx.pr.formula.infra.entity.formula;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="QCFMT_FORMULA_MANUAL")
public class QcfmtFormulaManual implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    public QcfmtFormulaManualPK qcfmtFormulaManualPK;	
	
	@Column(name = "FORMULA_CONTENT")
	public String formulaContent;
	
	@Column(name = "REFER_MONTH_ATR")
	public String referMonthAtr;
	
	@Column(name = "ROUND_DIGIT")
	public String roundDigit;
	
	@Column(name = "ROUND_ATR")
	public String roundAtr;
	
}
