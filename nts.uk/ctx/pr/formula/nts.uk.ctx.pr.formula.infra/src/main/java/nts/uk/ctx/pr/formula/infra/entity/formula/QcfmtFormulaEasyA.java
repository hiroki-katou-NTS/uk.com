package nts.uk.ctx.pr.formula.infra.entity.formula;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="QCFMT_FORMULA_EASY_A")
public class QcfmtFormulaEasyA implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    public QcfmtFormulaEasyAPK qcfmtFormulaEasyAPK;
	
}
