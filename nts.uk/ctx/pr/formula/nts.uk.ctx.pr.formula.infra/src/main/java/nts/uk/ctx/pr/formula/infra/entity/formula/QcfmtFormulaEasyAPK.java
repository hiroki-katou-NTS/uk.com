package nts.uk.ctx.pr.formula.infra.entity.formula;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class QcfmtFormulaEasyAPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CCD")
	public String ccd;
	
	@Column(name = "FORMULA_CD")
	public String formulaCd;
	
	@Column(name = "HIST_ID")
	public String histId;
	
	@Column(name = "EASY_FORMULA_CD")
	public String easyFormulaCd;
	
	@Column(name = "A_ITEM_CD")
	public String aItemCd;

}
