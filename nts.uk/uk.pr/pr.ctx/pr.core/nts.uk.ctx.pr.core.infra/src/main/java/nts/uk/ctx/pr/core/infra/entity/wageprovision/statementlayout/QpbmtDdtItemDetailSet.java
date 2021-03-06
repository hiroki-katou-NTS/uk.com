package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.DeductionItemDetailSet;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 控除項目明細設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_DDT_ITEM_DETAIL_SET")
public class QpbmtDdtItemDetailSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtDdtItemDetailSetPk ddtItemDetailSetPk;
    
    /**
    * 合計対象
    */
    @Basic(optional = false)
    @Column(name = "TOTAL_OBJ")
    public int totalObj;
    
    /**
    * 按分区分
    */
    @Basic(optional = false)
    @Column(name = "PROPORTIONAL_ATR")
    public int proportionalAtr;
    
    /**
    * 按分方法
    */
    @Basic(optional = false)
    @Column(name = "PROPORTIONAL_METHOD")
    public Integer proportionalMethod;
    
    /**
    * 計算方法
    */
    @Basic(optional = false)
    @Column(name = "CALC_METHOD")
    public int calcMethod;
    
    /**
    * 計算式コード
    */
    @Basic(optional = true)
    @Column(name = "CALC_FORMULA_CD")
    public String calcFormulaCd;
    
    /**
    * 個人金額コード
    */
    @Basic(optional = true)
    @Column(name = "PERSON_AMOUNT_CD")
    public String personAmountCd;
    
    /**
    * 共通金額
    */
    @Basic(optional = false)
    @Column(name = "COMMON_AMOUNT")
    public Long commonAmount;
    
    /**
    * 賃金テーブルコード
    */
    @Basic(optional = true)
    @Column(name = "WAGE_TABLE_CD")
    public String wageTblCd;
    
    /**
    * 支給相殺
    */
    @Basic(optional = true)
    @Column(name = "SUPPLY_OFFSET")
    public String supplyOffset;
    
    @Override
    protected Object getKey()
    {
        return ddtItemDetailSetPk;
    }

    public DeductionItemDetailSet toDomain(){
        return  new DeductionItemDetailSet(this.ddtItemDetailSetPk.histId,this.ddtItemDetailSetPk.cid, this.ddtItemDetailSetPk.statementCd, this.ddtItemDetailSetPk.itemNameCd,this.totalObj,this.proportionalAtr,this.proportionalMethod,this.calcMethod,this.calcFormulaCd,this.personAmountCd,this.commonAmount,this.wageTblCd,this.supplyOffset);
    }

    public static QpbmtDdtItemDetailSet toEntity(DeductionItemDetailSet domain){
        String cid = AppContexts.user().companyId();

        QpbmtDdtItemDetailSet entiy = new QpbmtDdtItemDetailSet();
        entiy.ddtItemDetailSetPk = new QpbmtDdtItemDetailSetPk(cid, domain.getStatementCode().v(), domain.getHistId(), domain.getItemNameCd());
        entiy.totalObj = domain.getTotalObj().value;
        entiy.proportionalAtr = domain.getProportionalAtr().value;
        entiy.proportionalMethod = domain.getProportionalMethod().map(i->i.value).orElse(0);
        entiy.calcMethod = domain.getCalcMethod().value;
        entiy.calcFormulaCd = domain.getCalcFormulaCd().map(i->i.v()).orElse(null);
        entiy.personAmountCd = domain.getPersonAmountCd().map(i->i.v()).orElse(null);
        entiy.commonAmount = domain.getCommonAmount().map(i->i.v()).orElse(0L);
        entiy.wageTblCd = domain.getWageTblCd().map(i->i.v()).orElse(null);
        entiy.supplyOffset = domain.getSupplyOffset().orElse(null);
        return entiy;
    }
}
