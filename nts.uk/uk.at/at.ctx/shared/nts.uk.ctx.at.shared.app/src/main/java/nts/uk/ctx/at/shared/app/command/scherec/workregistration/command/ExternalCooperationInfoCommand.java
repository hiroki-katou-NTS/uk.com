package nts.uk.ctx.at.shared.app.command.scherec.workregistration.command;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExternalCooperationInfoCommand {
    /** 外部コード1 */
    private String externalCode1;

    /** 外部コード2 */
    private String externalCode2;

    /** 外部コード3 */
    private String externalCode3;

    /** 外部コード4 */
    private String externalCode4;

    /** 外部コード5 */
    private String externalCode5;
}
