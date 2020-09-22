package nts.uk.screen.at.ws.cmm024.screen_f;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class ShowEmployeesDto {
    /**
     * 職場ID
     */
    private String workplaceId;
    /**
     *基準日期間
     */
    private GeneralDate baseDate;
}
