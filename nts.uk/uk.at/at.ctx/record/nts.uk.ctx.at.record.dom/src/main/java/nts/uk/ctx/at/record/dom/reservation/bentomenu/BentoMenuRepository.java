package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import nts.arc.time.GeneralDate;

public interface BentoMenuRepository {
	
	public BentoMenu getBentoMenu(String companyID, GeneralDate date);
	
	public Bento getBento(String companyID, GeneralDate date, Integer frameNo);
	
}
