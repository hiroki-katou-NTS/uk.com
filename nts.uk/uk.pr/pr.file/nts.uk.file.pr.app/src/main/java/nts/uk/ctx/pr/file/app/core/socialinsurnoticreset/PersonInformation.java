package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonInformation {
    private String personNameKana;
    private String todokedeNameKana;
    private String personName;
    private String todokedeName;
    private String personId;
    private GeneralDate birthDate;
}
