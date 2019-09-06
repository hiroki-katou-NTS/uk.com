package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.time.GeneralDate;

import java.util.Optional;

/**
* 社会保険取得時情報
*/
public interface SocialInsurAcquisiInforRepository {

    void add(SocialInsurAcquisiInfor domain);

    void update(SocialInsurAcquisiInfor domain);

    void remove(String employeeId, String companyId);

    Optional<SocialInsurAcquisiInfor> getSocialInsurAcquisiInforByCIdEmpId(String companyId, String employeeId );

    void  updated(SocialInsurAcquisiInfor domain);

    void updated(String employeeId, int continReemAfterRetirement);

    Optional<GeneralDate> getPersonInfo(String employeeId);


}
