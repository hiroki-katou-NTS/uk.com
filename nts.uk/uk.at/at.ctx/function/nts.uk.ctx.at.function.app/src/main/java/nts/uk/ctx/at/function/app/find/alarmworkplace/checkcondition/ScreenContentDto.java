package nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreenContentDto {

    private List<AlarmWkpCheckCdtDto> alarmWkpCheckCdt;

    private List<FixedExtractionItemDto> fixedItems;

}
