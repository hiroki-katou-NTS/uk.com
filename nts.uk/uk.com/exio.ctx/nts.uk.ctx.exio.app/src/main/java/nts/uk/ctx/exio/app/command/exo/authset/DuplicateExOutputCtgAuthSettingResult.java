package nts.uk.ctx.exio.app.command.exo.authset;

import lombok.Data;

@Data
public class DuplicateExOutputCtgAuthSettingResult {
    /** 結果（OK/NG) */
    boolean isSuccess;

    /** 複写先ロールID */
    String copyDestinationRoleId;

    /** 上書き（する/しない） */
    boolean isOverwrite;
}
