package nts.uk.ctx.pr.file.app.core.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WageTableExportData {
    private String wageTableCode;

    private String wageTableName;

    private String wageElementSet;

    private String optAddElement_1;

    private String optAddElement_2;

    private String optAddElement_3;

    private String wageFixElement_1;

    private String wageFixElement_2;

    private String wageFixElement_3;

    private String wageHisTableCode;

    private String wageHistoryId;

    private String WageHisStartYm;

    private String WageHisEndYm;
}
