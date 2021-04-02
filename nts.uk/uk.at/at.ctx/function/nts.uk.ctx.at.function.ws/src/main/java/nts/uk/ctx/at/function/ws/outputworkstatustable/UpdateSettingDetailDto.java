package nts.uk.ctx.at.function.ws.outputworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class UpdateSettingDetailDto {
    private String settingId;
    private String code;
    private String name;
    private int settingCategory;
    private List<OutputItemDto> outputItemList;

}

