package nts.uk.ctx.pr.proto.dom.personalwagename;

import java.util.List;

public interface PersonalWageNameRepository {
	List<PersonalWageNameMaster> getPersonalWageName(String companyCode, int categoryAtr, String personalWageCode,
			String personalWageName);
}
