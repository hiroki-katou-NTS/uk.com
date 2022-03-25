package nts.uk.ctx.at.function.app.command.dailyperformanceformat;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DuplicateMobileAuthorityDailyFormatCommand {

    private String currentCode;

    private int isDefaultInitial;

    private String dailyPerformanceFormatCode;

    private String dailyPerformanceFormatName;
}
