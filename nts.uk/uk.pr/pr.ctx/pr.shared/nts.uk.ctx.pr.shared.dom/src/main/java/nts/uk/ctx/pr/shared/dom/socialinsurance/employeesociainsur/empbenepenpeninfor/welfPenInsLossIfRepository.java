package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import java.util.Optional;

/**
* 厚生年金保険喪失時情報
*/
public interface WelfPenInsLossIfRepository {
    Optional<WelfPenInsLossIf> getWelfPenLossInfoById(String empId);
    void insert(WelfPenInsLossIf welfPenInsLossIf);
    void update(WelfPenInsLossIf welfPenInsLossIf);
}
