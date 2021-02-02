package nts.uk.ctx.at.schedule.app.find.budget.premium.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
@Setter
public class HistPersonCostCalculationDto {
    private GeneralDate startDate;
    private GeneralDate endDate;
    private String companyID;
    private String historyID;
}
