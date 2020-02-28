package nts.uk.ctx.pr.file.app.core.insurenamechangenoti;


import lombok.Data;
import lombok.Getter;
import nts.arc.time.GeneralDate;

import java.util.List;

@Data
@Getter
public class InsuredNameChangedNotiQuery {
    private SocialInsurNotiCreateSetDto socialInsurNotiCreateSetDto;
    private List<String> listEmpId;
    private GeneralDate date;
}
