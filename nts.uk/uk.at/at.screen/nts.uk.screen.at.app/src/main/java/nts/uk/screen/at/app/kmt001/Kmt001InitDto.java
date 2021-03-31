package nts.uk.screen.at.app.kmt001;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationMethod;
import nts.uk.screen.at.app.query.kmt.kmt005.TaskFrameSettingDto;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Kmt001InitDto {
    private TaskOperationMethod operationMethod;
    private List<TaskFrameSettingDto> taskFrameSettings;
}
