package nts.uk.ctx.at.function.app.command.outputworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;

import java.util.List;
@AllArgsConstructor
@Getter
@Setter
public class CreateConfigdetailCommand {
    private String code;
    private String name;
    private int settingCategory;
    private List<OutputItem> outputItemList;

}

