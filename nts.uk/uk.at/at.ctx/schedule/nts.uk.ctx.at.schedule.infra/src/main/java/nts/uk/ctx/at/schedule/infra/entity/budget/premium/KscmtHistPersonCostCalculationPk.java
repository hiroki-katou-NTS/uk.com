package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KscmtHistPersonCostCalculationPk {
    @Column(name = "CID")
    public String companyID;

    @Column(name = "HIST_ID")
    public String histID;
}
