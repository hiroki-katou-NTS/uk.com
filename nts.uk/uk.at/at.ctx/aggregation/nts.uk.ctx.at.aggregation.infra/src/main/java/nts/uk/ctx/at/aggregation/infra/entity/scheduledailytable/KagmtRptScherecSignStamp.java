package nts.uk.ctx.at.aggregation.infra.entity.scheduledailytable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KAGMT_RPT_SCHEREC_SIGN_STAMP")
public class KagmtRptScherecSignStamp extends ContractUkJpaEntity {
    @EmbeddedId
    public KagmtRptScherecSignStampPk pk;

    @Column(name = "TITLE")
    public String title;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
            @JoinColumn(name = "CODE", referencedColumnName = "CODE", insertable = false, updatable = false)
    })
    public KagmtRptScherec kagmtRptScherec;

    @Override
    protected Object getKey() {
        return this.pk;
    }
}
