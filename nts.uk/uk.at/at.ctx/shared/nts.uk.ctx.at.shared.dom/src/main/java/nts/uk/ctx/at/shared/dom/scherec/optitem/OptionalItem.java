/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.CalcResultOfAnyItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.Formula;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.ResultOfCalcFormula;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder.FormulaDispOrder;

/**
 * The Class OptionalItem.
 */
// 任意項目
// 責務 : 帳票で使用する項目を設定する
// Responsibility: Set items to be used in the form
@Getter
public class OptionalItem extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The optional item no. */
	// 任意項目NO
	private OptionalItemNo optionalItemNo;

	/** The optional item name. */
	// 任意項目名称
	private OptionalItemName optionalItemName;

	/** The usage atr. */
	// 任意項目利用区分
	private OptionalItemUsageAtr usageAtr;
	
	/** The emp condition atr. */
	// 雇用条件区分
	private EmpConditionAtr empConditionAtr;

	/** The performance atr. */
	// 実績区分
	private PerformanceAtr performanceAtr;

	/** The optional item atr. */
	// 属性
	private OptionalItemAtr optionalItemAtr;

	// 入力制御設定
	private InputControlSetting inputControlSetting;

	/** The unit. */
	// 単位
	private Optional<UnitOfOptionalItem> unit;
	
	/** The Calculation Classification */
	// 計算区分
	private CalculationClassification calcAtr;
	
	/** The note */
	// 任意項目のメモ
	private Optional<NoteOptionalItem> note;
	
	/** The Description */
	// 説明文
	private Optional<DescritionOptionalItem> description;

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		if (this.inputControlSetting.getCalcResultRange().hasBothLimit()) {
			if (this.performanceAtr.equals(PerformanceAtr.DAILY_PERFORMANCE)) {
			    switch (this.optionalItemAtr) {
			    case NUMBER:
			        if (this.inputControlSetting.getCalcResultRange().getNumberRange().get().getDailyTimesRange().get().isInvalidRange()) {
			            throw new BusinessException("Msg_574");
			        }
			        if (!this.inputControlSetting.getDailyInputUnit().isPresent() || !this.inputControlSetting.getDailyInputUnit().get().getNumberItemInputUnit().isPresent()) {
                        throw new BusinessException("Msg_2307");
                    }
			        break;
			    case AMOUNT:
			        if (this.inputControlSetting.getCalcResultRange().getAmountRange().get().getDailyAmountRange().get().isInvalidRange()) {
                        throw new BusinessException("Msg_574");
			        }
                    if (!this.inputControlSetting.getDailyInputUnit().isPresent() || !this.inputControlSetting.getDailyInputUnit().get().getAmountItemInputUnit().isPresent()) {
                        throw new BusinessException("Msg_2307");
                    }
			        break;
			    case TIME:
			        if (this.inputControlSetting.getCalcResultRange().getTimeRange().get().getDailyTimeRange().get().isInvalidRange()) {
                        throw new BusinessException("Msg_574");
			        }
                    if (!this.inputControlSetting.getDailyInputUnit().isPresent() || !this.inputControlSetting.getDailyInputUnit().get().getTimeItemInputUnit().isPresent()) {
                        throw new BusinessException("Msg_2307");
                    }
			        break;
			    default:
			        throw new RuntimeException("unknown value of enum OptionalItemAtr");
			    }
			} else {
			    switch (this.optionalItemAtr) {
                case NUMBER:
                    if (this.inputControlSetting.getCalcResultRange().getNumberRange().get().getMonthlyTimesRange().get().isInvalidRange()) {
                        throw new BusinessException("Msg_574");
                    }
                    break;
                case AMOUNT:
                    if (this.inputControlSetting.getCalcResultRange().getAmountRange().get().getMonthlyAmountRange().get().isInvalidRange()) {
                        throw new BusinessException("Msg_574");
                    }
                    break;
                case TIME:
                    if (this.inputControlSetting.getCalcResultRange().getTimeRange().get().getMonthlyTimeRange().get().isInvalidRange()) {
                        throw new BusinessException("Msg_574");
                    }
                    break;
                default:
                    throw new RuntimeException("unknown value of enum OptionalItemAtr");
                }
			}
		}
	}

	/**
	 * Checks if is used.
	 *
	 * @return true, if is used
	 */
	public boolean isUsed() {
		return this.usageAtr.equals(OptionalItemUsageAtr.USE);
	}

	/**
	 * Instantiates a new optional item.
	 *
	 * @param memento the memento
	 */
	public OptionalItem(OptionalItemGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.optionalItemNo = memento.getOptionalItemNo();
		this.optionalItemName = memento.getOptionalItemName();
		this.optionalItemAtr = memento.getOptionalItemAtr();
		this.usageAtr = memento.getOptionalItemUsageAtr();
		this.empConditionAtr = memento.getEmpConditionAtr();
		this.performanceAtr = memento.getPerformanceAtr();
		this.inputControlSetting = memento.getInputControlSetting();
		this.unit = memento.getUnit();
		this.calcAtr = memento.getCalcAtr();
		this.note = memento.getNote();
		this.description = memento.getDescription();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OptionalItemSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setOptionalItemNo(this.optionalItemNo);
		memento.setOptionalItemAtr(this.optionalItemAtr);
		memento.setOptionalItemName(this.optionalItemName);
		memento.setOptionalItemUsageAtr(this.usageAtr);
		memento.setEmpConditionAtr(this.empConditionAtr);
		memento.setPerformanceAtr(this.performanceAtr);
		memento.setInputControlSetting(this.inputControlSetting);
		memento.setUnit(this.unit);
		memento.setCalAtr(this.calcAtr);
		memento.setNote(this.note);
		memento.setDescription(this.description);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((optionalItemNo == null) ? 0 : optionalItemNo.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OptionalItem other = (OptionalItem) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (optionalItemNo == null) {
			if (other.optionalItemNo != null)
				return false;
		} else if (!optionalItemNo.equals(other.optionalItemNo))
			return false;
		return true;
	}

	/**
	 * 日別利用条件の判定
	 * @param empCondition 適用する雇用条件
	 * @param bsEmploymentHistOpt 個人の雇用条件
	 * @return 利用条件
	 */
	public TermsOfUseForOptItem checkTermsOfUseDaily(Optional<EmpCondition> empCondition,Optional<BsEmploymentHistoryImport> bsEmploymentHistOpt){
		
		// 利用区分の確認
		if (this.usageAtr == OptionalItemUsageAtr.NOT_USE) return TermsOfUseForOptItem.NOT_USE;
		
		// 実績区分の確認
		if (this.performanceAtr == PerformanceAtr.MONTHLY_PERFORMANCE) return TermsOfUseForOptItem.NOT_USE;
		
		// 計算条件の判定
		if (!this.checkTermsOfCalc(empCondition, bsEmploymentHistOpt)) return TermsOfUseForOptItem.NOT_USE;
		
		// 「利用する」を返す
		return TermsOfUseForOptItem.USE;
	}
	
	/**
	 * 月別利用条件の判定
	 * @param empCondition 適用する雇用条件
	 * @param bsEmploymentHistOpt 個人の雇用条件
	 * @return 利用条件
	 */
	public TermsOfUseForOptItem checkTermsOfUseMonth(Optional<EmpCondition> empCondition,Optional<BsEmploymentHistoryImport> bsEmploymentHistOpt){
		
		// 利用区分の確認
		if (this.usageAtr == OptionalItemUsageAtr.NOT_USE) return TermsOfUseForOptItem.NOT_USE;
		
		// 実績区分の確認
		if (this.performanceAtr == PerformanceAtr.DAILY_PERFORMANCE) return TermsOfUseForOptItem.DAILY_VTOTAL;
		
		// 計算条件の判定
		if (!this.checkTermsOfCalc(empCondition, bsEmploymentHistOpt)) return TermsOfUseForOptItem.NOT_USE;

		// 「利用する」を返す
		return TermsOfUseForOptItem.USE;
	}
	
	/**
	 * 計算条件の判定
	 * @param empCondition 適用する雇用条件
	 * @param bsEmploymentHistOpt 個人の雇用条件
	 * @return true=計算する,false=計算しない
	 */
	public boolean checkTermsOfCalc(Optional<EmpCondition> empCondition,Optional<BsEmploymentHistoryImport> bsEmploymentHistOpt) {
		// 計算区分を確認
		if(this.calcAtr == CalculationClassification.NOT_CALC) {
			return false;
		}
		// 雇用条件区分を確認
		if(this.empConditionAtr.isNoCondition()) {
			return true;
		}
		// 「適用する雇用条件」を取得
		if(!empCondition.isPresent()||empCondition.get().getEmpConditions().isEmpty()) {
			return true;
		}
		// 雇用条件判断
		return empCondition.get().checkEmpCondition(bsEmploymentHistOpt);
	}
	
	
    /**
     * 計算処理
     * @param companyId 会社ID
     * @param formulaList 計算式リスト
     * @param formulaOrderList 計算式の並び順リスト
     * @param dailyRecordDto 日次データ
     * @param monthlyRecordDto 月次データ
     * @return 任意項目の計算結果
     */
    public CalcResultOfAnyItem caluculationFormula(String companyId,
    												List<Formula> formulaList,
    												List<FormulaDispOrder> formulaOrderList,
    												Optional<DailyRecordToAttendanceItemConverter> dailyRecordDto,
    												Optional<MonthlyRecordToAttendanceItemConverter> monthlyRecordDto,
    												PerformanceAtr performanceAtr) {
	
		//2019/8/9 UPD shuichi_ishida Redmine #108654　（記号順でなく、並び順でソート）
		//任意項目計算式を記号の昇順でソート
		//formulaList.sort((first,second) -> first.getSymbol().compareTo(second.getSymbol()));
		//任意項目計算式を並び順でソート
		Map<String, Formula> formulaMap = new HashMap<>();
		for (Formula formula : formulaList) formulaMap.putIfAbsent(formula.getFormulaId().v(), formula);
		List<String> addedFormulaId = new ArrayList<>();
		List<Formula> sortedFormula = new ArrayList<>();
		formulaOrderList.sort((a, b) -> a.getDispOrder().compareTo(b.getDispOrder()));
		for (FormulaDispOrder formulaOrder : formulaOrderList) {
			String formulaId = formulaOrder.getOptionalItemFormulaId().v();
			if (formulaMap.containsKey(formulaId)) {
				sortedFormula.add(formulaMap.get(formulaId));
				addedFormulaId.add(formulaId);
			}
		}
		for (Formula formula : formulaList) {
			String formulaId = formula.getFormulaId().v();
			if (!addedFormulaId.contains(formulaId)) sortedFormula.add(formula);
		}
		formulaList = sortedFormula;
		
        /*パラメータ：任意項目の計算結果を定義*/
        List<ResultOfCalcFormula> calcResultAnyItem = new ArrayList<>();
        //計算式分ループ
        for(Formula formula : formulaList) {
            calcResultAnyItem.add(formula.dicisionCalc(this, performanceAtr, calcResultAnyItem, dailyRecordDto, monthlyRecordDto));
        }
        //Listが空だった場合
        if(calcResultAnyItem.isEmpty()) {
            return new CalcResultOfAnyItem(this.optionalItemNo,
            							   Optional.of(BigDecimal.valueOf(0)),
										   Optional.of(BigDecimal.valueOf(0)),
										   Optional.of(BigDecimal.valueOf(0)));
        }
        //一番最後の計算式の結果を取得
        ResultOfCalcFormula resultOfCalcFormula = calcResultAnyItem.get(calcResultAnyItem.size()-1);
        //一番最後の計算式の結果を基に任意項目の計算結果を作成
        CalcResultOfAnyItem result = new CalcResultOfAnyItem(this.optionalItemNo,
				   											 resultOfCalcFormula.getCount(),
				   											 resultOfCalcFormula.getTime(),
				   											 resultOfCalcFormula.getMoney());
        //上限下限チェック
        result = this.inputControlSetting.getCalcResultRange().checkRange(result, this, performanceAtr);
        
        return result;
    }

    /**
     * 回数の0or.5への丸め
     * @param rawCountValue
     * @param decimalCount
     * @return
     */
//    private BigDecimal controlCountValue(BigDecimal rawCountValue, int decimalCount) {
//
//        //countを2倍する
//    	BigDecimal multipleValue = rawCountValue.multiply(BigDecimal.valueOf(2));
//        //countで四捨五入する
//    	// -1 の意味　→ setScaleは小数点第１→０、第２→１を渡す必要があるため、小数点以下の桁数からマイナス１してる
//    	multipleValue = multipleValue.setScale(decimalCount - 1, BigDecimal.ROUND_HALF_UP);
//        //countを2で割る(元の数値に戻す)
//    	return multipleValue.divide(BigDecimal.valueOf(2));
//    }
    
    /**
     * 小数点以下の桁数チェック
     * @param value 値
     * @return 桁数
     */
//    private int getPrecision(BigDecimal value){
//      String str = String.valueOf(value.stripTrailingZeros());
//      // 文末が ".0"とか".00000"で終わってるやつは全部桁０とする
//      if(str.matches("^.*\\.0+$")){
//        return 0;
//      }
//      int index = str.indexOf(".");
//      return str.substring(index + 1).length();
//   	}
    
    /**
	 * 	[1] 任意項目に対応する日次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getDaiLyAttendanceIdByNo() {
		return Arrays.asList(this.optionalItemNo.v()+640);
	}
	
	/**
	 * 	[2] 任意項目に対応する月次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdByNo() {
		return Arrays.asList(this.optionalItemNo.v()+588);
	}
    
	
	/**
	 * 	[3] 利用できない日次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getDailyAttendanceIdNotAvailable() {
		if(this.usageAtr == OptionalItemUsageAtr.NOT_USE) {
			return this.getDaiLyAttendanceIdByNo();
		}
		return new ArrayList<>();
	}
	
	/**
	 * 	[4] 利用できない月次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdNotAvailable() {
		if(this.usageAtr == OptionalItemUsageAtr.NOT_USE) {
			return this.getMonthlyAttendanceIdByNo();
		}
		return new ArrayList<>();
	}

	public OptionalItem(OptionalItemNo optionalItemNo, OptionalItemUsageAtr usageAtr) {
		super();
		this.optionalItemNo = optionalItemNo;
		this.usageAtr = usageAtr;
	}
    /**
     * 入力値が正しいか
     * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).任意項目.関数アルゴリズム.任意項目.<<Public>> 入力値が正しいか
     * @param inputValue
     * @return
     */
    public CheckValueInputCorrectOuput checkInputValueCorrect(BigDecimal inputValue) {
    	CheckValueInputCorrectOuput data = this.inputControlSetting.checkValueInputCorrect(inputValue, this.performanceAtr, this.optionalItemAtr);
		return data;
	}

	public OptionalItem(CompanyId companyId, OptionalItemNo optionalItemNo, OptionalItemName optionalItemName,
			OptionalItemUsageAtr usageAtr, EmpConditionAtr empConditionAtr, PerformanceAtr performanceAtr,
			OptionalItemAtr optionalItemAtr, InputControlSetting inputControlSetting, Optional<UnitOfOptionalItem> unit,
			CalculationClassification calcAtr, Optional<NoteOptionalItem> note,
			Optional<DescritionOptionalItem> description) {
		super();
		this.companyId = companyId;
		this.optionalItemNo = optionalItemNo;
		this.optionalItemName = optionalItemName;
		this.usageAtr = usageAtr;
		this.empConditionAtr = empConditionAtr;
		this.performanceAtr = performanceAtr;
		this.optionalItemAtr = optionalItemAtr;
		this.inputControlSetting = inputControlSetting;
		this.unit = unit;
		this.calcAtr = calcAtr;
		this.note = note;
		this.description = description;
	}
}
