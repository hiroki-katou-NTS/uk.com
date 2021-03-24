package nts.uk.screen.at.app.kwr004;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnnualWorkLedgerInitScreenDto {
    // 期首月
    private int startMonth;

    // 自由設定使用区分
    private boolean hasAuthority;
}
