package nts.uk.ctx.pr.shared.infra.entity.payrollgeneralpurposeparameters;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 給与汎用パラメータ年月履歴
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SAL_GEN_PARA_YM_HIS")
public class QpbmtSalGenParaYmHis extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtSalGenParaYmHisPk salGenParaYmHisPk;
    
    /**
    * 開始日
    */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;
    
    /**
    * 終了日
    */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;
    
    @Override
    protected Object getKey()
    {
        return salGenParaYmHisPk;
    }


    public static QpbmtSalGenParaYmHis toEntity(YearMonthHistoryItem domain, String cId,String paraNo) {
        return new QpbmtSalGenParaYmHis(new QpbmtSalGenParaYmHisPk(paraNo, cId, domain.identifier()),domain.start().v(), domain.end().v());
    }

}
