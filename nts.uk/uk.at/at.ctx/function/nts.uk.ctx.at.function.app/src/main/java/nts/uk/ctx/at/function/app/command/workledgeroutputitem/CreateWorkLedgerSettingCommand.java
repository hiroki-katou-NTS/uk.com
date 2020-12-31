package nts.uk.ctx.at.function.app.command.workledgeroutputitem;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;

import javax.ejb.Stateless;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CreateWorkLedgerSettingCommand {
   private String code;
   private String name;
   private int settingCategory;
   List<AttendanceItemToPrint> outputItemList;
}
