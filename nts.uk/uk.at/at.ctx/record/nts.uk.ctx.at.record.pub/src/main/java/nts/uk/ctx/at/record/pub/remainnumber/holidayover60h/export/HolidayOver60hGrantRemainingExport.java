package nts.uk.ctx.at.record.pub.remainnumber.holidayover60h.export;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.empinfo.grantremainingdata.HolidayOver60hGrantRemainingData;

/**
 * 60H超休付与残数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class HolidayOver60hGrantRemainingExport{

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
	 * @param holidayOver60hGrantRemainingData
	 * @return
	 */
	static public HolidayOver60hGrantRemainingExport fromDomain(
			HolidayOver60hGrantRemainingData holidayOver60hGrantRemainingData) {

		HolidayOver60hGrantRemainingExport export = new HolidayOver60hGrantRemainingExport();

		export.leaveID = holidayOver60hGrantRemainingData.getLeaveID();
		export.employeeId = holidayOver60hGrantRemainingData.getEmployeeId();
		export.grantDate = holidayOver60hGrantRemainingData.getGrantDate();
		export.deadline = holidayOver60hGrantRemainingData.getDeadline();
		export.expirationStatus = holidayOver60hGrantRemainingData.getExpirationStatus();
		export.registerType = holidayOver60hGrantRemainingData.getRegisterType();
		export.details = holidayOver60hGrantRemainingData.getDetails().clone();

		return export;
	}

	/**
	   * ドメインへ変換
	 * @param holidayOver60hGrantRemainingExport
	 * @return
	 */
	static public HolidayOver60hGrantRemainingData toDomain(
			HolidayOver60hGrantRemainingExport holidayOver60hGrantRemainingExport) {

		HolidayOver60hGrantRemainingData domain = new HolidayOver60hGrantRemainingData();

		domain.setLeaveID(holidayOver60hGrantRemainingExport.getLeaveID());
		domain.setEmployeeId(holidayOver60hGrantRemainingExport.getEmployeeId());
		domain.setGrantDate(holidayOver60hGrantRemainingExport.getGrantDate());
		domain.setDeadline(holidayOver60hGrantRemainingExport.getDeadline());
		domain.setExpirationStatus(holidayOver60hGrantRemainingExport.getExpirationStatus());
		domain.setRegisterType(holidayOver60hGrantRemainingExport.getRegisterType());
		domain.setDetails(holidayOver60hGrantRemainingExport.getDetails());

		return domain;
	}

}
