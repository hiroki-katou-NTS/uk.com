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
@Table(name="QCFMT_FORMULA")
public class QcfmtFormula implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    public QcfmtFormulaPK qcfmtFormulaPK;
	
	@Column(name ="FORMULA_NAME")
	public String formulaName;
	
	@Column(name ="STR_YM")
	public BigDecimal strYm;

	@Column(name ="END_YM")
	public BigDecimal endYm;
	
	@Column(name ="DIFFICULTY_ATR")
	public BigDecimal difficultyAtr;

}
