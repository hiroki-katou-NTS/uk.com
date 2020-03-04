package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.ItemNameCode;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableCode;
import org.apache.commons.lang3.StringUtils;

/**
* 控除項目明細設定
*/
@Getter
@Setter
public class DeductionItemDetailSet extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String histId;

    /**
     * 会社ID
     */
    private String cid;

    /**
     * 明細書コード
     */
    private StatementCode statementCode;

    /**
     * 項目名コード
     */
    private String itemNameCd;
    
    /**
    * 合計対象
    */
    private DeductionTotalObjAtr totalObj;
    
    /**
    * 按分区分
    */
    private DeductionProportionalAtr proportionalAtr;
    
    /**
    * 按分方法
    */
    private Optional<ProportionalMethodAtr> proportionalMethod;
    
    /**
    * 計算方法
    */
    private DeductionCaclMethodAtr calcMethod;
    
    /**
    * 計算式コード
    */
    private Optional<FormulaCode> calcFormulaCd;
    
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
    private Optional<WageTableCode> wageTblCd;
    
    /**
    * 支給相殺
    */
    private Optional<String> supplyOffset;
    
    public DeductionItemDetailSet(String histId, String cid, String statementCode, String itemNameCd, int totalObj, int proportionalAtr, Integer proportionalMethod, int calcMethod, String calcFormulaCd, String personAmountCd, Long commonAmount, String wageTblCd, String supplyOffset) {
        this.totalObj = EnumAdaptor.valueOf(totalObj, DeductionTotalObjAtr.class);
        this.calcMethod = EnumAdaptor.valueOf(calcMethod, DeductionCaclMethodAtr.class);
        this.proportionalAtr = EnumAdaptor.valueOf(proportionalAtr, DeductionProportionalAtr.class);
        this.personAmountCd = Optional.ofNullable(StringUtils.isEmpty(personAmountCd)? null : new PersonalValueCode(personAmountCd));
        this.calcFormulaCd = Optional.ofNullable(StringUtils.isEmpty(calcFormulaCd)? null : new FormulaCode(calcFormulaCd));
        this.wageTblCd = Optional.ofNullable(StringUtils.isEmpty(wageTblCd)? null : new WageTableCode(wageTblCd));
        this.commonAmount = Optional.ofNullable(commonAmount == null ? null : new CommonAmount(commonAmount));
        this.proportionalMethod = Optional.ofNullable(proportionalMethod == null? null : EnumAdaptor.valueOf(proportionalMethod, ProportionalMethodAtr.class));
        this.supplyOffset = Optional.ofNullable(supplyOffset);
        this.cid = cid;
        this.statementCode = new StatementCode(statementCode);
        this.itemNameCd = itemNameCd;
        this.histId = histId;
    }
    
}
