package nts.uk.ctx.at.function.app.command.arbitraryperiodsummarytable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;

import java.util.List;

@AllArgsConstructor
@Getter
public class UpdateOutputSettingCommand {
    private String id;
    private String code;
    private String name;
    private int settingCategory;
    List<AttendanceItemToPrint> outputItemList;
}
