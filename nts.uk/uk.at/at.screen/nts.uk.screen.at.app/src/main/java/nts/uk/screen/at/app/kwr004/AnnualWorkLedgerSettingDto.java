package nts.uk.screen.at.app.kwr004;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;

/**
 * 年間勤務台帳の出力設定DTO
 */
@Data
@AllArgsConstructor
public class AnnualWorkLedgerSettingDto {
    // ID
    private String settingId;

    // コード
    private String code;

    // 名称
    private String name;

    public static AnnualWorkLedgerSettingDto fromDomain(AnnualWorkLedgerOutputSetting setting) {
        return new AnnualWorkLedgerSettingDto(setting.getID(), setting.getCode().v(), setting.getName().v());
    }
}
