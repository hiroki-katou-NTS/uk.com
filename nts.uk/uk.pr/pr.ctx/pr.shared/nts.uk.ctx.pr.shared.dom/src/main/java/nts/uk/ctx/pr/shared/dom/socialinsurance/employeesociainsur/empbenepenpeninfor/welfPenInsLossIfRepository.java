package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import java.util.Optional;

/**
* 厚生年金保険喪失時情報
*/
public interface welfPenInsLossIfRepository {
    Optional<WelfPenInsLossIf> getWelfPenLossInfoById(String empId);
    void insertWelfPenInsLossIf(WelfPenInsLossIf welfPenInsLossIf);
    void updateWelfPenInsLossIf(WelfPenInsLossIf welfPenInsLossIf);
}
