package nts.uk.screen.at.app.command.kdl.kdl016;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployee;

@AllArgsConstructor
@Data
public class SupportableEmpCannotRegisterDto {
    //エラーがあるか
    private boolean isError;

    // 応援可能な社員
    private SupportableEmployee supportableEmployee;

    //エラーメッセージ
    private String errorInfo;

    public static SupportableEmpCannotRegisterDto createWithError(
            SupportableEmployee supportableEmployee, String errorMessage) {

        return new SupportableEmpCannotRegisterDto(
                true,
                supportableEmployee,
                errorMessage);
    }
}
