package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

public interface BentoMenuHistRepository {
	
	public BentoMenuHistory getBentoMenu(String companyID, GeneralDate date);

	public BentoMenuHistory getBentoMenu(String companyID, GeneralDate date,Optional<WorkLocationCode> workLocationCode);

	public Bento getBento(String companyID, GeneralDate date, int frameNo);
	
	public List<Bento> getBento(String companyID, GeneralDate date);
	
	public List<BentoMenuHistory> getBentoMenuPeriod(String companyID, DatePeriod period);

	public List<BentoMenuHistory> getBentoMenu(String companyID, GeneralDate date, ReservationClosingTimeFrame reservationClosingTimeFrame);

	public BentoMenuHistory getBentoMenuByHistId(String companyID, String histId);

	public BentoMenuHistory getBentoMenuByEndDate(String companyID, GeneralDate date);

	public void delete(String companyId, String historyId);
	
	// 	[1]  取得する
	public Optional<BentoMenuHistory> findByCompanyDate(String companyID, GeneralDate date);
	
	// 	[3]  弁当を取得する
	public List<Bento> getBento(String companyID, GeneralDate date, Optional<WorkLocationCode> workLocationCode);
	
	// [5]  弁当メニュー履歴を更新する
	public void update(BentoMenuHistory bentoMenuHistory);
	
	public void updateLst(List<BentoMenuHistory> updateBentoMenuHistoryLst);
	
	// 	[6]  弁当メニューを追加する
	public void add(BentoMenuHistory bentoMenuHistory);
	
	// 	[8]  履歴IDから弁当メニューを取得する
	public Optional<BentoMenuHistory> findByHistoryID(String historyID);
	
	// 	[9] 弁当メニューを取得
	public List<BentoMenuHistory> findByCompanyPeriod(String companyID, DatePeriod period);
	
	// 	[10] 会社の弁当メニューを取得
	public List<BentoMenuHistory> findByCompany(String companyID);

}
