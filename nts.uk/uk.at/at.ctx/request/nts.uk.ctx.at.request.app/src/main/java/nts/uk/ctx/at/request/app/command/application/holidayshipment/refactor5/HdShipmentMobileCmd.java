package nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command.AbsenceLeaveAppCmd;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command.RecruitmentAppCmd;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.DisplayInforWhenStarting;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class HdShipmentMobileCmd {
	/**
	 * 画面モード
	 */
	private boolean newMode;
	
	/**
	 * 振出申請
	 */
	public RecruitmentAppCmd rec;
	
	/**
	 * 振休申請
	 */
	public AbsenceLeaveAppCmd abs;
	
	/**
	 * 振休振出申請起動時の表示情報
	 */
	private DisplayInforWhenStarting displayInforWhenStarting;
	
	/**
	 * 振出_休出代休紐付け管理<List>
	 */
	private List<LeaveComDayOffManaDto> recHolidayMngLst = new ArrayList<LeaveComDayOffManaDto>();
	
	/**
	 * 振休_休出代休紐付け管理<List>
	 */
	private List<LeaveComDayOffManaDto> absHolidayMngLst = new ArrayList<LeaveComDayOffManaDto>();
	
	/**
	 * 振休_振出振休紐付け管理<List>
	 */
	private List<PayoutSubofHDManagementDto> absWorkMngLst = new ArrayList<PayoutSubofHDManagementDto>();
	
	private boolean checkFlag = true;
}
