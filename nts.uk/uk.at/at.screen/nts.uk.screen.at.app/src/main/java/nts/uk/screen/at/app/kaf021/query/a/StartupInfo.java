package nts.uk.screen.at.app.kaf021.query.a;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StartupInfo {

    private AgreementOperationSettingDto setting;

    /**
     * 処理年月
     */
    private int processingMonth;
}
