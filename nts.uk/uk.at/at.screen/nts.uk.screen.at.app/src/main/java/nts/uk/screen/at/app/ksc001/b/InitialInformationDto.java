package nts.uk.screen.at.app.ksc001.b;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InitialInformationDto {
    public GeneralDate startDate;
    public GeneralDate endDate;
}
