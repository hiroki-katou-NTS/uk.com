package nts.uk.ctx.pr.file.app.core.socialinsurance;

import java.util.List;

public interface SocialInsuranceRepository {

    List<Object[]> getHeathyInsurance(String cid);

    List<Object[]> getWelfarepensionInsurance(String cid);

    List<Object[]> getContributionRate(String cid);

    List<Object[]> getSocialInsuranceoffice(String cid);

    List<Object[]> getSalaryHealth(String cid);

}
