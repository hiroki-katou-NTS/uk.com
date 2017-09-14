package nts.uk.ctx.at.record.app.find.optionalitem.calculationformula;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.optionalitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optionalitem.calculationformula.CalculationFormulaAtr;
import nts.uk.ctx.at.record.dom.optionalitem.calculationformula.DailyRounding;
import nts.uk.ctx.at.record.dom.optionalitem.calculationformula.MonthlyRounding;
import nts.uk.ctx.at.record.dom.optionalitem.calculationformula.OptionalItemFormulaGetMemento;
import nts.uk.ctx.at.record.dom.optionalitem.calculationformula.OptionalItemFormulaId;
import nts.uk.ctx.at.record.dom.optionalitem.calculationformula.OptionalItemFormulaName;
import nts.uk.ctx.at.record.dom.optionalitem.calculationformula.OptionalItemFormulaSetMemento;
import nts.uk.ctx.at.record.dom.optionalitem.calculationformula.OptionalItemFormulaSetting;
import nts.uk.ctx.at.record.dom.optionalitem.calculationformula.Symbol;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
public class CalcFormulaDto implements OptionalItemFormulaSetMemento, OptionalItemFormulaGetMemento {

	/** The formula id. */
	// 計算式ID
	private String formulaId;

	/** The optional item no. */
	// 任意項目NO
	private String optionalItemNo;

	/** The formula name. */
	// 計算式名称
	private String formulaName;

	/** The formula setting. */
	// 計算式設定
	private OptionalItemFormulaSettingDto formulaSetting;

	/** The formula atr. */
	// 属性
	private int formulaAtr;

	/** The symbol. */
	// 記号
	private String symbolValue;

	// ===================== Optional ======================= //
	/** The monthly rounding. */
	// 月別端数処理
	private MonthlyRoundingDto monthlyRounding;

	/** The daily rounding. */
	// 日別端数処理
	private DailyRoundingDto dailyRounding;

	@Override
	public void setCompanyId(CompanyId cId) {
		// Not used.
	}

	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	@Override
	public OptionalItemFormulaId getOptionalItemFormulaId() {
		return new OptionalItemFormulaId(this.formulaId);
	}

	@Override
	public OptionalItemNo getOptionalItemNo() {
		return new OptionalItemNo(this.optionalItemNo);
	}

	@Override
	public OptionalItemFormulaName getOptionalItemFormulaName() {
		return new OptionalItemFormulaName(this.formulaName);
	}

	@Override
	public OptionalItemFormulaSetting getOptionalItemFormulaSetting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CalculationFormulaAtr getCalculationFormulaAtr() {
		return EnumAdaptor.valueOf(this.formulaAtr, CalculationFormulaAtr.class);
	}

	@Override
	public Symbol getSymbol() {
		return new Symbol(this.symbolValue);
	}

	@Override
	public MonthlyRounding getMonthlyRounding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DailyRounding getDailyRounding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOptionalItemFormulaId(OptionalItemFormulaId id) {
		this.formulaId = id.v();
	}

	@Override
	public void setOptionalItemNo(OptionalItemNo optItemNo) {
		this.optionalItemNo = optItemNo.v();
	}

	@Override
	public void setOptionalItemFormulaName(OptionalItemFormulaName name) {
		this.formulaName = name.v();
	}

	@Override
	public void setOptionalItemFormulaSetting(OptionalItemFormulaSetting setting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCalculationFormulaAtr(CalculationFormulaAtr atr) {
		this.formulaAtr = atr.value;
	}

	@Override
	public void setSymbol(Symbol symbol) {
		this.symbolValue = symbol.v();
	}

	@Override
	public void setMonthlyRounding(MonthlyRounding rounding) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDailyRounding(DailyRounding rounding) {
		// TODO Auto-generated method stub

	}
}
