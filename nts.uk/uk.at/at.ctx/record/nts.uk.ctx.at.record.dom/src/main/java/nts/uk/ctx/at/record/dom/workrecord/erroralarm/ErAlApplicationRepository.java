package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import java.util.Optional;

public interface ErAlApplicationRepository {
	Optional<ErAlApplication> getAllErAlAppByEralCode(String companyID,String errorAlarmCode);
}
