package nts.uk.ctx.pr.formula.infra.entity.formula;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="QCFMT_FORMULA_EASY_HEAD")
public class QcfmtFormulaEasyHead implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    public QcfmtFormulaEasyHeadPK qcfmtFormulaEasyHeadPK;
	
	@Column(name = "CONDITION_ATR")
	public BigDecimal conditionAtr;	
	
}
