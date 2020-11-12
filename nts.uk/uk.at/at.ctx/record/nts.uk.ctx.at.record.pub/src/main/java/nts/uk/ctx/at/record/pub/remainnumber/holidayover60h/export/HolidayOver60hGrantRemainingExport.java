package nts.uk.ctx.at.record.pub.remainnumber.holidayover60h.export;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hGrantRemaining;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;

/**
 * 60H超休付与残数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class HolidayOver60hGrantRemainingExport{

	/** 60H超休不足ダミーフラグ */
	@Setter
	private Boolean dummyAtr = false;

	/**
	 * ID
	 */
	protected String leaveID;

	/**
	 * 会社ID
	 */
	protected String cid;

	/**
	 * 社員ID
	 */
	protected String employeeId;

	/**
	 * 付与日
	 */
	protected GeneralDate grantDate;

	/**
	 * 期限日
	 */
	protected GeneralDate deadline;

	/**
	 * 期限切れ状態
	 */
	protected LeaveExpirationStatus expirationStatus;

	/**
	 * 登録種別
	 */
	protected GrantRemainRegisterType registerType;

	/**
	 * 明細
	 */
	protected LeaveNumberInfo details;


	/**
	   * ドメインから変換
	 * @param holidayOver60hGrantRemaining
	 * @return
	 */
	static public HolidayOver60hGrantRemainingExport of(
			HolidayOver60hGrantRemaining holidayOver60hGrantRemaining) {

		HolidayOver60hGrantRemainingExport export = new HolidayOver60hGrantRemainingExport();

		export.leaveID = holidayOver60hGrantRemaining.getLeaveID();
		export.cid = holidayOver60hGrantRemaining.getCid();
		export.employeeId = holidayOver60hGrantRemaining.getEmployeeId();
		export.grantDate = holidayOver60hGrantRemaining.getGrantDate();
		export.deadline = holidayOver60hGrantRemaining.getDeadline();
		export.expirationStatus = holidayOver60hGrantRemaining.getExpirationStatus();
		export.registerType = holidayOver60hGrantRemaining.getRegisterType();
		export.details = holidayOver60hGrantRemaining.getDetails().clone();

		return export;
	}

	/**
	   * ドメインへ変換
	 * @param holidayOver60hGrantRemainingExport
	 * @return
	 */
	static public HolidayOver60hGrantRemaining toDomain(
			HolidayOver60hGrantRemainingExport holidayOver60hGrantRemainingExport) {

		HolidayOver60hGrantRemaining domain = new HolidayOver60hGrantRemaining();

		domain.setLeaveID(holidayOver60hGrantRemainingExport.getLeaveID());
		domain.setCid(holidayOver60hGrantRemainingExport.getCid());
		domain.setEmployeeId(holidayOver60hGrantRemainingExport.getEmployeeId());
		domain.setGrantDate(holidayOver60hGrantRemainingExport.getGrantDate());
		domain.setDeadline(holidayOver60hGrantRemainingExport.getDeadline());
		domain.setExpirationStatus(holidayOver60hGrantRemainingExport.getExpirationStatus());
		domain.setRegisterType(holidayOver60hGrantRemainingExport.getRegisterType());
		domain.setDetails(holidayOver60hGrantRemainingExport.getDetails());

		return domain;
	}

}
