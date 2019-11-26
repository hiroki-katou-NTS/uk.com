package nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.DetailPrintingMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 明細印字月
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_DETAIL_PRINTING_MON")
public class QpbmtDetailPrintingMon extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtDetailPrintingMonPk detailPrintingMonPk;
    
    /**
    * 印字月
    */
    @Basic(optional = false)
    @Column(name = "PRINTING_MONTH")
    public int printingMonth;
    
    @Override
    protected Object getKey()
    {
        return detailPrintingMonPk;
    }

    public DetailPrintingMonth toDomain() {
        return new DetailPrintingMonth( this.printingMonth);
    }
    public static QpbmtDetailPrintingMon toEntity(DetailPrintingMonth domain) {
        return new QpbmtDetailPrintingMon(new QpbmtDetailPrintingMonPk(), domain.getPrintingMonth().value);
    }

}
