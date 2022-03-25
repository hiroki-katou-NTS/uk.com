package nts.uk.screen.at.app.query.kfp002;

import lombok.Value;

import java.util.List;

@Value
public class AnyPeriodResultDataDto {
    private List<AnyPeriodActualResultDto> actualResults;
    private List<AnyPeriodEditStateDto> editStates;
}
