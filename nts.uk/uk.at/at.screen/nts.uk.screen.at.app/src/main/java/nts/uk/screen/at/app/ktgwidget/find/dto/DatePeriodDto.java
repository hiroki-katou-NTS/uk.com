package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
public class DatePeriodDto {

    private GeneralDate strCurrentMonth;
    
    private GeneralDate endCurrentMonth;
    
    private GeneralDate strNextMonth;
    
    private GeneralDate endNextMonth;

}
