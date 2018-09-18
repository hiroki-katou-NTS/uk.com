package nts.uk.ctx.pr.core.infra.entity.laborinsurance;

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
* 労災保険履歴
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_OCC_ACC_IS_HIS")
public class QpbmtOccAccIsHis extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtOccAccIsHisPk occAccIsHisPk;
    
    /**
    * 年月期間
    */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;
    
    /**
    * 年月期間
    */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;
    
    @Override
    protected Object getKey()
    {
        return occAccIsHisPk;
    }
    public static QpbmtOccAccIsHis toEntity(YearMonthHistoryItem domain, String cId) {
        return new QpbmtOccAccIsHis(new QpbmtOccAccIsHisPk(cId,domain.identifier()),domain.start().v(),domain.end().v());
    }




}
