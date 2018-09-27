package nts.uk.screen.at.app.dailyperformance.correction.monthflex;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.dailyperformance.correction.agreement.AgreementInfomationDto;
import nts.uk.screen.at.app.dailyperformance.correction.flex.change.FlexShortageDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.FormatDailyDto;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyResult;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DPMonthResult {
   private FlexShortageDto flexShortage;
   private List<MonthlyModifyResult> results;
   private boolean hasItem;
   private Integer month;
   private List<FormatDailyDto> formatDaily;
   private AgreementInfomationDto agreementInfo;
}
