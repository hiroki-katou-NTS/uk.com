package nts.uk.ctx.at.aggregation.app.command.scheduledailytable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.*;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ScheduleDailyTablePrintSettingCommand {
    private String code;
    private String name;
    private int signStampUseAtr;
    private List<String> titles;
    private String comment;
    private List<Integer> personalCounter;
    private List<Integer> workplaceCounter;
    private int transferDisplay;
    private int supporterSchedulePrintMethod;
    private int supporterRecordPrintMethod;
    private boolean newMode;

    public ScheduleDailyTablePrintSetting toDomain() {
        return new ScheduleDailyTablePrintSetting(
                new ScheduleDailyTableCode(code),
                new ScheduleDailyTableName(name),
                ScheduleDailyTableItemSetting.create(
                        ScheduleDailyTableInkanRow.create(
                                EnumAdaptor.valueOf(signStampUseAtr, NotUseAtr.class),
                                titles.stream().map(i -> new ScheduleDailyTableInkanTitle(i)).collect(Collectors.toList())
                        ),
                        comment == null ? Optional.empty() : Optional.of(new ScheduleDailyTableComment(comment)),
                        personalCounter,
                        workplaceCounter,
                        EnumAdaptor.valueOf(transferDisplay, NotUseAtr.class),
                        EnumAdaptor.valueOf(supporterSchedulePrintMethod, SupporterPrintMethod.class),
                        EnumAdaptor.valueOf(supporterRecordPrintMethod, SupporterPrintMethod.class)
                )
        );
    }
}
