package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaCode;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableCode;
import org.apache.commons.lang3.StringUtils;

/**
* 支給項目明細設定
*/
@Getter
public class PaymentItemDetailSet extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String histId;
    
    /**
    * 給与項目ID
    */
    private String salaryItemId;
    
    /**
    * 合計対象
    */
    private PaymentTotalObjAtr totalObj;
    
    /**
    * 按分区分
    */
    private PaymentProportionalAtr proportionalAtr;
    
    /**
    * 按分方法
    */
    private Optional<ProportionalMethodAtr> proportionalMethod;
    
    /**
    * 計算方法
    */
    private PaymentCaclMethodAtr calcMethod;
    
    /**
    * 計算式コード
    */
    private Optional<FormulaCode> calcFomulaCd;
    
    /**
    * 個人金額コード
    */
    private Optional<PersonalValueCode> personAmountCd;
    
    /**
    * 共通金額
    */
    private Optional<CommonAmount> commonAmount;
    
    /**
    * 賃金テーブルコード
    */
    private Optional<WageTableCode> wageTblCode;
    
    /**
    * 通勤区分
    */
    private Optional<WorkingAtr> workingAtr;
    
    public PaymentItemDetailSet(String histId, String salaryItemId, int totalObj, int proportionalAtr, Integer proportionalMethod, int calcMethod, String calcFormulaCd, String personAmountCd, Long commonAmount, String wageTblCd, Integer workingAtr) {
        this.totalObj = EnumAdaptor.valueOf(totalObj, PaymentTotalObjAtr.class);
        this.calcMethod = EnumAdaptor.valueOf(calcMethod, PaymentCaclMethodAtr.class);
        this.personAmountCd = Optional.ofNullable(StringUtils.isEmpty(personAmountCd)? null: new PersonalValueCode(personAmountCd));
        this.calcFomulaCd = Optional.ofNullable(StringUtils.isEmpty(calcFormulaCd)? null : new FormulaCode(calcFormulaCd));
        this.wageTblCode =  Optional.ofNullable(StringUtils.isEmpty(wageTblCd)? null : new WageTableCode(wageTblCd));
        this.commonAmount = Optional.ofNullable(commonAmount == null ? null : new CommonAmount(commonAmount));
        this.proportionalAtr = EnumAdaptor.valueOf(proportionalAtr, PaymentProportionalAtr.class);
        this.proportionalMethod =  Optional.ofNullable( proportionalMethod == null? null : EnumAdaptor.valueOf(proportionalMethod, ProportionalMethodAtr.class));
        this.salaryItemId = salaryItemId;
        this.histId = histId;
        this.workingAtr = Optional.ofNullable(workingAtr == null? null :EnumAdaptor.valueOf(workingAtr, WorkingAtr.class));
    }
    
}
