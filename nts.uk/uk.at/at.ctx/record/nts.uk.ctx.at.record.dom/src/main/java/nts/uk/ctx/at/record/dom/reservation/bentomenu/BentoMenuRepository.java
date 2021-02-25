package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

public interface BentoMenuRepository {
	
	public BentoMenu getBentoMenu(String companyID, GeneralDate date);

	public BentoMenu getBentoMenu(String companyID, GeneralDate date,Optional<WorkLocationCode> workLocationCode);

	public Bento getBento(String companyID, GeneralDate date, int frameNo);
	
	public List<Bento> getBento(String companyID, GeneralDate date);
	
	public List<BentoMenu> getBentoMenuPeriod(String companyID, DatePeriod period);

	public List<BentoMenu> getBentoMenu(String companyID, GeneralDate date, ReservationClosingTimeFrame reservationClosingTimeFrame);

	public BentoMenu getBentoMenuByHistId(String companyID, String histId);

	public BentoMenu getBentoMenuByEndDate(String companyID, GeneralDate date);

	public void add(BentoMenu bentoMenu);

	public void update(BentoMenu bentoMenu);

	public void delete(String companyId, String historyId);

}
