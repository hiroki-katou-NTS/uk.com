package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

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

    Optional<SocialInsurAcquisiInfor> getSocialInsurAcquisiInforById(String cid, String employeeId){
        return repository.getSocialInsurAcquisiInforById(cid,employeeId);
    }
}
