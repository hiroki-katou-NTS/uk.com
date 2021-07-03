package nts.uk.ctx.at.function.app.find.alarm.alarmlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

import java.util.List;

/**
 * アラームリスト抽出従業員情報
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ValueExtractAlarmDto {
    private String guid;
    private String workplaceID;
    private String workplaceCode;
    private String workplaceName;
    private String employeeID;
    private String employeeCode;
    private String employeeName;
    private String alarmValueDate;
    private int category;
    private String categoryName;
    private String alarmItem;
    private String alarmValueMessage;
    private String comment;
    private String checkedValue;
    private List<WebMenuInfoDto> menuItems;
}
