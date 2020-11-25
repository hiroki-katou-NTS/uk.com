package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import lombok.Data;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.MonthNo;
import nts.uk.ctx.at.function.dom.alarmworkplace.ExtractionPeriodMonthly;
import nts.uk.ctx.at.function.dom.alarmworkplace.month.EndMonth;
import nts.uk.ctx.at.function.dom.alarmworkplace.month.StartMonth;

import java.util.Optional;

@Data
@Getter
public class ExtractionPeriodMonthlyCommand {

    //Start month

    private int strSpecify;

    private Integer strMonth;

    private Integer strPreviousAtr;

    //End Month

    private int endSpecify;

    private Integer endMonth;

    private Integer endPreviousAtr;

    public static ExtractionPeriodMonthly toDomain(ExtractionPeriodMonthlyCommand command) {

        StartMonth startMonth = new StartMonth(command.strSpecify, Optional.of(new MonthNo(EnumAdaptor.valueOf(command.strPreviousAtr, PreviousClassification.class), command.strMonth, command.strMonth == 0)));

        EndMonth endMonth = new EndMonth(command.endSpecify, Optional.of(new MonthNo(EnumAdaptor.valueOf(command.endPreviousAtr, PreviousClassification.class), command.endMonth, command.endMonth == 0)));

        return new ExtractionPeriodMonthly(startMonth, endMonth);
    }
}
