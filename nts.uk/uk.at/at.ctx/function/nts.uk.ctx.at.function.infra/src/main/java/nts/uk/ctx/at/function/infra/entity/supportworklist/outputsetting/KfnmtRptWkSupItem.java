package nts.uk.ctx.at.function.infra.entity.supportworklist.outputsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;

@Entity
@Table(name="KFNMT_RPT_WK_SUP_ITEM")
@NoArgsConstructor
@AllArgsConstructor
public class KfnmtRptWkSupItem extends ContractUkJpaEntity {
    @EmbeddedId
    public KfnmtRptWkSupItemPk pk;

    @Column(name = "DISPORDER")
    public int displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
            @JoinColumn(name = "CODE", referencedColumnName = "CODE", insertable = false, updatable = false)
    })
    public KfnmtRptWkSup reportSetting;

    @Override
    protected Object getKey() {
        return pk;
    }

}
