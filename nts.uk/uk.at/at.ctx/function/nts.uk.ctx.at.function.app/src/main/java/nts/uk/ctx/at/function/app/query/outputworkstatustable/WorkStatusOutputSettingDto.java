package nts.uk.ctx.at.function.app.query.outputworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkStatusOutputSettingDto {
    // 	設定ID 	(GUID)
    private String settingId;

    //  設定表示コード ->(出力項目設定コード)
    private String settingDisplayCode;

    // 	設定名称 ->(出力項目設定名称)
    private String settingName;

    // 	社員ID
    private String employeeId;

    // 	定型自由区分
    private int standardFreeDivision;
    private String standardFreeDivName;
    private List<ItemDto> outputItemList;

}
