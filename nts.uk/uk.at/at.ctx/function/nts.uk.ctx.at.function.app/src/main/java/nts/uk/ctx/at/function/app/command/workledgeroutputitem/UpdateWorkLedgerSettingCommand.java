package nts.uk.ctx.at.function.app.command.workledgeroutputitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UpdateWorkLedgerSettingCommand {
    private String id;
    private String code;
    private String name;
    private int settingCategory;
    private List<Integer> rankingList;
    private List<Integer> attendanceIdList;

}
