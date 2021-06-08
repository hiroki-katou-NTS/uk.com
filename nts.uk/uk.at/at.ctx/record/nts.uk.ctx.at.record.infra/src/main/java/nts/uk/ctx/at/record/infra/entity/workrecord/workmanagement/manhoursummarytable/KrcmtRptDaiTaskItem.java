package nts.uk.ctx.at.record.infra.entity.workrecord.workmanagement.manhoursummarytable;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.SummaryItem;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.SummaryItemType;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Entity
@Table(name = "KRCMT_RPT_DAI_TASK_ITEM")
public class KrcmtRptDaiTaskItem extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KrcmtRptDaiTaskItemPk pk;

    /**
     * 階層順番
     */
    @Column(name = "DISPORDER")
    public int displayOrder;

    @ManyToOne
    @JoinColumns(
            {@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
                    @JoinColumn(name = "CODE", referencedColumnName = "CODE", insertable = false, updatable = false)})
    public KrcmtRptDaiTask rptDaiTask;

    public KrcmtRptDaiTaskItem(KrcmtRptDaiTaskItemPk pk, int displayOrder) {
        super();
        this.pk = pk;
        this.displayOrder = displayOrder;
    }

    @Override
    protected Object getKey() {
        return pk;
    }

    public SummaryItem toDomain(KrcmtRptDaiTaskItem entity) {
        return new SummaryItem(
                entity.displayOrder,
                SummaryItemType.of(entity.pk.summaryType)
        );
    }

    public void fromEntity(KrcmtRptDaiTaskItem entity) {
        this.pk.summaryType = entity.pk.summaryType;
        this.displayOrder = entity.displayOrder;
    }
}
