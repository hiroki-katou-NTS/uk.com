package nts.uk.ctx.at.aggregation.infra.entity.scheduledailytable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KAGMT_RPT_SCHEREC_TALLY_BYPERSON")
public class KagmtRptScherecTallyByperson extends ContractUkJpaEntity {
    @EmbeddedId
    public KagmtRptScherecSignStampPk pk;

    @Column(name = "TOTAL_TIMES_NO")
    public int totalTimesNo;

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
