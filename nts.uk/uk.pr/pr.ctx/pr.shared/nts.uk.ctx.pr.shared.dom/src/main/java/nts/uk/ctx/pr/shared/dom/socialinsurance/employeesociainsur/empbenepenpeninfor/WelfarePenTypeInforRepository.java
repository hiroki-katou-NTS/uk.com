package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import java.util.List;
import java.util.Optional;

/**
* 厚生年金種別情報
*/
public interface WelfarePenTypeInforRepository
{

    List<WelfarePenTypeInfor> getAllWelfarePenTypeInfor();

    Optional<WelfarePenTypeInfor> getWelfarePenTypeInforById(String historyId);

    void add(WelfarePenTypeInfor domain);

    void update(WelfarePenTypeInfor domain);

    void remove(String historyId);

}
