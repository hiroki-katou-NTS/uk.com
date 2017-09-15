package nts.uk.ctx.at.record.app.find.optitem.calculation;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalcFormulaSetting;
import nts.uk.ctx.at.record.dom.optitem.calculation.DailyRounding;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaId;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaName;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.MonthlyRounding;
import nts.uk.ctx.at.record.dom.optitem.calculation.Symbol;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
public class FormulaDto implements FormulaSetMemento, FormulaGetMemento {

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
	private FormulaSettingDto formulaSetting;

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
	public FormulaId getOptionalItemFormulaId() {
		return new FormulaId(this.formulaId);
	}

	@Override
	public OptionalItemNo getOptionalItemNo() {
		return new OptionalItemNo(this.optionalItemNo);
	}

	@Override
	public FormulaName getOptionalItemFormulaName() {
		return new FormulaName(this.formulaName);
	}

	@Override
	public CalcFormulaSetting getOptionalItemFormulaSetting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FormulaAtr getCalculationFormulaAtr() {
		return EnumAdaptor.valueOf(this.formulaAtr, FormulaAtr.class);
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
	public void setOptionalItemFormulaId(FormulaId id) {
		this.formulaId = id.v();
	}

	@Override
	public void setOptionalItemNo(OptionalItemNo optItemNo) {
		this.optionalItemNo = optItemNo.v();
	}

	@Override
	public void setOptionalItemFormulaName(FormulaName name) {
		this.formulaName = name.v();
	}

	@Override
	public void setOptionalItemFormulaSetting(CalcFormulaSetting setting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCalculationFormulaAtr(FormulaAtr atr) {
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
