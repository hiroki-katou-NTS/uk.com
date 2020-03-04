package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.Data;

import java.util.List;

@Data
public class StatementItemCustomDataDto {
    private int categoryAtr;
    private String itemNameCdSelected;
    private List<String> itemNameCdExcludeList;
}
