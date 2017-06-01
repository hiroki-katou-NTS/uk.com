package nts.uk.ctx.pr.report.dom.payment.comparing.masterpage;

import java.util.List;

public interface PersonInfoRepository {
	List<PersonInfo> getPersonInfo(String companyCode);
}
