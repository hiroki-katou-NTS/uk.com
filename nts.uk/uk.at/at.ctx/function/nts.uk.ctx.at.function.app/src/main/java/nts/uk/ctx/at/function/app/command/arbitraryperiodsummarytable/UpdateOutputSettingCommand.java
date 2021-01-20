package nts.uk.ctx.at.function.app.command.arbitraryperiodsummarytable;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;

import java.util.List;

@Data
@Builder
public class UpdateOutputSettingCommand {
    private String id;
    private String code;
    private String name;
    private int settingCategory;
    List<AttendanceItemToPrint> outputItemList;
}
