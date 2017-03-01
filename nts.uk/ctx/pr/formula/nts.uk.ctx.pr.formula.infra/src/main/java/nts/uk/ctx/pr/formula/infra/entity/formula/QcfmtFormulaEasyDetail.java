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
@Table(name="QCFMT_FORMULA_EASY_DETAIL")
public class QcfmtFormulaEasyDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;	
	
	@EmbeddedId
    public QcfmtFormulaEasyDetailPK qcfmtFormulaEasyDetailPK;

	@Column(name = "EASY_FORMULA_NAME")
	public String easyFormulaName;
	
	@Column(name = "EASY_FORMULA_TYPE_ATR")
	public BigDecimal easyFormulaTypeAttribute;
	
	@Column(name = "A_BASE_MNY_ATR")
	public BigDecimal aBaseMnyAtr;
	
	@Column(name = "A_BASE_MNY")
	public BigDecimal aBaseMny;
	
	@Column(name = "B_DIVIDE_VALUE_SET")
	public BigDecimal bDivideValueSet;
	
	@Column(name = "B_DIVIDE_VALUE")
	public BigDecimal bDivideValue;
	
	@Column(name = "C_PREMIUM_RATE")
	public BigDecimal cPremiumRate;
	
	@Column(name = "D_ROUND_ATR")
	public BigDecimal dRoundAtr;
	
	@Column(name = "E_WORK_ITEM_CD")
	public String eWorkItemCd;
	
	@Column(name = "E_WORK_VALUE")
	public String eWorkValue;
	
	@Column(name = "F_ADJUSTMENT_ATR")
	public String fAdjustmentAtr;
	
	@Column(name = "G_ROUND_ATR")
	public String gRoundAtr;
	
	@Column(name = "MIN_LIMIT_VALUE")
	public BigDecimal minLimitValue;
	
	@Column(name = "MAX_LIMIT_VALUE	")
	public BigDecimal maxLimitValue;

}
