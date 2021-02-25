package nts.uk.ctx.at.record.pub.reservation.bento;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface BentoMenuPub {

	public List<BentoMenuExport> getBentoMenu(String companyID, GeneralDate date);
}
