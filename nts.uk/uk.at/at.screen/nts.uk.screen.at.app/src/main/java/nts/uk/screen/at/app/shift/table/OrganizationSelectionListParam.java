package nts.uk.screen.at.app.shift.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationSelectionListParam {
    /**
     * 選択してる単位
     */
    private int UnitSelected;

    /**
     * 選択してる組織ID
     */
    private String OrganizationIdSelected;
}
