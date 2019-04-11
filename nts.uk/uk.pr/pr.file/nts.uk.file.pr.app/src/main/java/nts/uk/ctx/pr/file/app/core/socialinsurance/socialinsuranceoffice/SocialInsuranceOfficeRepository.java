package nts.uk.ctx.pr.file.app.core.socialinsurance.socialinsuranceoffice;

import java.util.List;

public interface SocialInsuranceOfficeRepository {

    List<Object[]> getHeathyInsurance(String cid);

    List<Object[]> getWelfarepensionInsurance(String cid);

    List<Object[]> getContributionRate(String cid);

    List<Object[]> getSocialInsuranceoffice(String cid);

    List<Object[]> getSalaryHealth(String cid);

}
