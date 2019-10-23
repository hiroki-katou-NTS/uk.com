package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 家族情報
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyInformation {
    private GeneralDate birthDate;
    private String nameKana;
    private String reportNameKana;
    private String reportName;
    private String name;
    private String familyId;
}
