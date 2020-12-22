package nts.uk.screen.at.app.kaf021.query.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@AllArgsConstructor
@Data
public class StartupInfo {
    private AgreementOperationSettingDto setting;
    private List<CurrentClosurePeriodDto> closurePeriods;
}
