package nts.uk.ctx.pr.formula.infra.entity.formula;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class QcfmtFormulaHistPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CCD")
	public String companyCode;
	
	@Column(name = "FORMULA_CD")
	public String formulaCode;
	
	@Column(name = "HIST_ID")
	public String historyId;
}
