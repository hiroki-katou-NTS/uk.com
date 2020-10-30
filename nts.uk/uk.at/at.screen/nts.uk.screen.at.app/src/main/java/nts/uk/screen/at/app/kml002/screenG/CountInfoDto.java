package nts.uk.screen.at.app.kml002.screenG;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.screen.at.app.ksm008.sceenD.WorkingHoursDto;

import java.util.List;

@Data
@AllArgsConstructor
public class CountInfoDto {

    private List<CountNumberOfTimeDto> countNumberOfTimeDtos;

    private List<NumberOfTimeTotalDto> numberOfTimeTotalDtos;

}

