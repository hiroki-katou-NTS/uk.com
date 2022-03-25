package nts.uk.ctx.at.aggregation.infra.entity.scheduledailytable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KagmtRptScherecSignStampPk {
    @Column(name = "CID")
    public String companyId;

    @Column(name = "CODE")
    public String code;

    @Column(name = "DISPORDER")
    public int dispOrder;
}
