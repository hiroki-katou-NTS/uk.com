package nts.uk.screen.at.ws.cmm024.screen_f;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
