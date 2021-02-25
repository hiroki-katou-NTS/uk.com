package nts.uk.ctx.at.record.pub.workrecord.actuallock;

import nts.arc.time.GeneralDate;

public interface DetermineActualResultLockPub {
	
	public boolean getDetermineActualLocked(String companyId, GeneralDate baseDate, Integer closureId, boolean typeDaily) ;

}
