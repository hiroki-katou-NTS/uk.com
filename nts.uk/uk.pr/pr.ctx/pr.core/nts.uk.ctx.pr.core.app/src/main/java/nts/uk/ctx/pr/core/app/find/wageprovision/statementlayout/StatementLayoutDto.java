package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class StatementLayoutDto {
    /**
     * 明細書コード
     */
    private String statementCode;

    /**
     * 明細書名称
     */
    private String statementName;
}
