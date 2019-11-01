package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
/**家族社会保険情報*/
public class EmpFamilySocialInsCtgInfo {
    private String empId;
    private Integer familyId;
    private String cid;
    private GeneralDate startDate;
    private GeneralDate endDate;
    private String fmBsPenNum;
    private Integer dependent;
}
