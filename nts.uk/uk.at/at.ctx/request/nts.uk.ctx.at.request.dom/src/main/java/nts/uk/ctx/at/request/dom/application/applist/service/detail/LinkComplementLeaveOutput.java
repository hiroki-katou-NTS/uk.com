package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.holidayshipment.TypeApplicationHolidays;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class LinkComplementLeaveOutput {
	/**
	 * 申請ID
	 */
	private String appID;
	
	/**
	 * 振休振出フラグ
	 */
	private TypeApplicationHolidays complementLeaveFlg;
	
	/**
	 * 振休振出同時申請管理
	 */
	private AppHdsubRec appHdsubRec;
	
	/**
	 * 振休申請データ
	 */
	private AbsenceLeaveApp absenceLeaveApp;
	
	/**
	 * 振出申請データ
	 */
	private RecruitmentApp recruitmentApp;
}
