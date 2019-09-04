package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class SocialInsurAcquisiInforService {

    @Inject
    SocialInsurAcquisiInforRepository repository;

    void add(SocialInsurAcquisiInfor domain){
        repository.add(domain);
    }

    void update(SocialInsurAcquisiInfor domain){
        repository.update(domain);
    }

    Optional<SocialInsurAcquisiInfor> getSocialInsurAcquisiInforById(String employeeId){

        return repository.getSocialInsurAcquisiInforByCIdEmpId(AppContexts.user().companyId(), employeeId);
    }
}
