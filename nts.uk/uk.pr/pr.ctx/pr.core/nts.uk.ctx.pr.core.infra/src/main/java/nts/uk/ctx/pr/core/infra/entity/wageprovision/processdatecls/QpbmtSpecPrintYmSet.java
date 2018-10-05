package nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SpecPrintYmSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 明細書印字年月設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SPEC_PRINT_YM_SET")
public class QpbmtSpecPrintYmSet extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtSpecPrintYmSetPk specPrintYmSetPk;


    /**
     * 印字年月
     */
    @Basic(optional = false)
    @Column(name = "PRINT_DATE")
    public int printDate;

    @Override
    protected Object getKey() {
        return specPrintYmSetPk;
    }

    public SpecPrintYmSet toDomain() {
        return new SpecPrintYmSet(this.specPrintYmSetPk.cid, this.specPrintYmSetPk.processCateNo, this.specPrintYmSetPk.processDate,
                this.printDate);
    }

    public static QpbmtSpecPrintYmSet toEntity(SpecPrintYmSet domain) {
        return new QpbmtSpecPrintYmSet(new QpbmtSpecPrintYmSetPk(domain.getCid(), domain.getProcessCateNo(), domain.getProcessDate()),
                domain.getPrintDate());
    }

}
