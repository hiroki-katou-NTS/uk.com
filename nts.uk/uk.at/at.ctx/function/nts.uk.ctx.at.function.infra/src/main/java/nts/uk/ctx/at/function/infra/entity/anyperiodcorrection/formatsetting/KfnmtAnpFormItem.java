package nts.uk.ctx.at.function.infra.entity.anyperiodcorrection.formatsetting;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;

@Entity
@Table(name = "KFNMT_ANP_FORM_ITEM")
@NoArgsConstructor
public class KfnmtAnpFormItem extends ContractUkJpaEntity {
    @EmbeddedId
    public KfnmtAnpFormItemPk pk;

    @Column(name = "DISPORDER")
    public int displayOrder;

    @Column(name = "COLUMN_WIDTH")
    public Integer columnWidth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
            @JoinColumn(name = "CODE", referencedColumnName = "CODE", insertable = false, updatable = false),
            @JoinColumn(name = "SHEET_NO", referencedColumnName = "SHEET_NO", insertable = false, updatable = false)
    })
    public KfnmtAnpFormSheet anyPeriodSheet;

    public KfnmtAnpFormItem(KfnmtAnpFormItemPk pk, int displayOrder, Integer columnWidth) {
        this.pk = pk;
        this.displayOrder = displayOrder;
        this.columnWidth = columnWidth;
    }

    @Override
    protected Object getKey() {
        return pk;
    }
}
