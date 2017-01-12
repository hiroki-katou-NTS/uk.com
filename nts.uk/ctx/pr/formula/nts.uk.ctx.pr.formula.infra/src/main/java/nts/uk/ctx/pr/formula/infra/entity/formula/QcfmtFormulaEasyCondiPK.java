package nts.uk.ctx.pr.formula.infra.entity.formula;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class QcfmtFormulaEasyCondiPK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "CCD")
	public String ccd;
	
	@Column(name = "FORMULA_CD")
	public String formulaCd;
	
	@Column(name = "HIST_ID")
	public String histId;
	
	@Column(name = "REF_MASTER_CD")
	public String refMasterCd;
}
