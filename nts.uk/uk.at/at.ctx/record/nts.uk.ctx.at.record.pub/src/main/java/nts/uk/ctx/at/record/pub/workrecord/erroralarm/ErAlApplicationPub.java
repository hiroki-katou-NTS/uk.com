package nts.uk.ctx.at.record.pub.workrecord.erroralarm;

import java.util.Optional;


public interface ErAlApplicationPub {
	Optional<ErAlApplicationPubExport> getAllErAlAppByEralCode(String companyID,String errorAlarmCode);
}
