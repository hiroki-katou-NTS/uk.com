package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfo {
    private String birthday;
    private String personNameRomaji;
    private String personNameKana;
    private String personId;
    private Integer gender;

}
