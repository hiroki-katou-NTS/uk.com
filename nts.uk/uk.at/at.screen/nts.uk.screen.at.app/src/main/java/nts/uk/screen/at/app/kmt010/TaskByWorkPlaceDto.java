package nts.uk.screen.at.app.kmt010;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.screen.at.app.query.kmt.kmt005.TaskFrameSettingDto;

import java.util.List;

@AllArgsConstructor
@Getter
public class TaskByWorkPlaceDto {
    private List<NarrowingDownTaskByWorkplaceDto> taskByWorkplaceList;
    private List<TaskFrameSettingDto> taskOperationSetting;
}
