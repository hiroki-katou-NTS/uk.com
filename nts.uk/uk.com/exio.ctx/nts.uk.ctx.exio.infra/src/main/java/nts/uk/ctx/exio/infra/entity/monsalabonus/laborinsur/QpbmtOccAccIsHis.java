package nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccIsHis;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccIsHisPk;
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
    * 
    */
    @Basic(optional = false)
    @Column(name = "START_DATE")
    public int startDate;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "END_DATE")
    public int endDate;
    
    @Override
    protected Object getKey()
    {
        return occAccIsHisPk;
    }

    public OccAccIsHis toDomain() {
        return new OccAccIsHis(this.occAccIsHisPk.cid, this.occAccIsHisPk.hisId, this.startDate, this.endDate);
    }
    public static QpbmtOccAccIsHis toEntity(OccAccIsHis domain) {
        return new QpbmtOccAccIsHis(new QpbmtOccAccIsHisPk(domain.getCid(), domain.getHisId()), domain.getStartDate(), domain.getEndDate());
    }

}
