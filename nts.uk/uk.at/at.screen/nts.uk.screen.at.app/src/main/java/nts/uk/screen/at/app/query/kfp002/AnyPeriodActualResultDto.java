package nts.uk.screen.at.app.query.kfp002;

import lombok.Value;
import nts.uk.screen.at.app.kdw013.a.ItemValueDto;

import java.util.List;

@Value
public class AnyPeriodActualResultDto {
    private String employeeId;
    private List<ItemValueDto> itemValues;
}
