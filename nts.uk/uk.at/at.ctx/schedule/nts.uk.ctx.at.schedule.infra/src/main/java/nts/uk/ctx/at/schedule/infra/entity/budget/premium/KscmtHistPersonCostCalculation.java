package nts.uk.ctx.at.schedule.infra.entity.budget.premium;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.premium.HistPersonCostCalculation;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * TODO Đang QA.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_PERSON_COST_CALCULATION_HIST")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtHistPersonCostCalculation extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KscmtHistPersonCostCalculationPk pk;

    @Column(name = "CONTRACT_CD")
    public String contractCD;

    @Column(name = "START_YMD")
    public GeneralDate startDate;

    @Column(name = "END_YMD")
    public GeneralDate endDate;

    @Override
    protected Object getKey() {
        return pk;
    }
    public static List<KscmtHistPersonCostCalculation> toEntity(HistPersonCostCalculation domain){
        List<KscmtHistPersonCostCalculation> result = new ArrayList<>();
        domain.getHistoryItems().forEach((item) -> {
            result.add((new KscmtHistPersonCostCalculation(new KscmtHistPersonCostCalculationPk(domain.getCompanyId(),item.identifier()),
                    AppContexts.user().contractCode(),item.start(),item.end())));
        });
        return result;
    }

    public KscmtHistPersonCostCalculation update(DateHistoryItem domain){
        this.endDate = domain.end();
        this.startDate = domain.start();
        return this;
    }
}
