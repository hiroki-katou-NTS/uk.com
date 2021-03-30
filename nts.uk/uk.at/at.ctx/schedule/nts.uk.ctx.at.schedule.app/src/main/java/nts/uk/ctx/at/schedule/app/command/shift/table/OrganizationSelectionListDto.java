package nts.uk.ctx.at.schedule.app.command.shift.table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrganizationSelectionListDto {
    /**
     * 選択してる単位
     */
    private int UnitSelected;

    /**
     * 選択してる組織ID
     */
    private String OrganizationIdSelected;
}