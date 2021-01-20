package nts.uk.ctx.at.function.app.command.arbitraryperiodsummarytable;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CreateOutputSettingCommand {
    private String code;
    private String name;
    private int settingCategory;
    List<AttendanceItemToPrint> outputItemList;
}
