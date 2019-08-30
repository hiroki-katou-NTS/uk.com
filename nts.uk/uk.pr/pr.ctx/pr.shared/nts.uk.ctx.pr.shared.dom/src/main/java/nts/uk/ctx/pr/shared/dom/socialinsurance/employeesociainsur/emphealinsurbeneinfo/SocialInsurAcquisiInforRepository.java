package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import java.util.Optional;

/**
* 社会保険取得時情報
*/
public interface SocialInsurAcquisiInforRepository
{

    Optional<SocialInsurAcquisiInfor> getSocialInsurAcquisiInforById(String cid, String employeeId);

    void add(SocialInsurAcquisiInfor domain);

    void update(SocialInsurAcquisiInfor domain);

    void remove(String cid,String employeeId);

}
