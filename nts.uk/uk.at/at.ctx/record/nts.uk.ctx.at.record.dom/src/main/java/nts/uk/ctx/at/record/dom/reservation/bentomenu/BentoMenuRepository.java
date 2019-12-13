package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.period.DatePeriod;

public interface BentoMenuRepository {
	
	public BentoMenu getBentoMenu(String companyID, GeneralDate date);
	
	public Bento getBento(String companyID, GeneralDate date, Integer frameNo);
	
	public List<BentoMenu> getBentoMenuPeriod(String companyID, DatePeriod period);
	
}
