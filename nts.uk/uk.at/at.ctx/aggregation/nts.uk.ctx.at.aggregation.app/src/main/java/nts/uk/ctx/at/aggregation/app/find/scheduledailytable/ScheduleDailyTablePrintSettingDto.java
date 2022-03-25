package nts.uk.ctx.at.aggregation.app.find.scheduledailytable;

import lombok.Value;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTablePrintSetting;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class ScheduleDailyTablePrintSettingDto {
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

    public static ScheduleDailyTablePrintSettingDto fromDomain(ScheduleDailyTablePrintSetting domain) {
        return new ScheduleDailyTablePrintSettingDto(
                domain.getCode().v(),
                domain.getName().v(),
                domain.getItemSetting().getInkanRow().getNotUseAtr().value,
                domain.getItemSetting().getInkanRow().getTitleList().stream().map(PrimitiveValueBase::v).collect(Collectors.toList()),
                domain.getItemSetting().getComment().map(PrimitiveValueBase::v).orElse(null),
                domain.getItemSetting().getPersonalCounter(),
                domain.getItemSetting().getWorkplaceCounter(),
                domain.getItemSetting().getTransferDisplay().value,
                domain.getItemSetting().getSupporterSchedulePrintMethod().value,
                domain.getItemSetting().getSupporterDailyDataPrintMethod().value
        );
    }
}
