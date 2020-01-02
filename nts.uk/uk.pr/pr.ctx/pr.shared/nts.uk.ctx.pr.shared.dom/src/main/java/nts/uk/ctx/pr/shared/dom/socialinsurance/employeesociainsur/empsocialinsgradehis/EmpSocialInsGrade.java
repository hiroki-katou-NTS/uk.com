package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

import java.util.List;

@Getter
@AllArgsConstructor
public class EmpSocialInsGrade extends DomainObject {
    private EmpSocialInsGradeHis history;
    private List<EmpSocialInsGradeInfo> infos;
}
