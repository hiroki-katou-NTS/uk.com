package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 
 * @author HungTT - 年休付与残数履歴データ テスト用！！！
 *
 */
@Getter
@Setter
public class AnnualLeaveRemainingHistoryTest extends AggregateRoot implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cid;
	/**
	 * 社員ID
	 */
	private String employeeId = "社員ID";

	/**
	 * 付与日
	 */
	//@JsonDeserialize(using = GeneralDateDeserializer.class)
	private GeneralDate grantDate = GeneralDate.ymd(2020, 4, 30);
	
	// 年月
	private YearMonth yearMonth = YearMonth.of(2020, 4);

	// 締めID
	private ClosureId closureId = ClosureId.valueOf(20);

	// 締め日
	private ClosureDate closureDate = new ClosureDate(20, true);

	/**
	 * 期限日
	 */
	//@JsonDeserialize(using = GeneralDateDeserializer.class)
	private GeneralDate deadline;// = GeneralDate.localDate(grantDate);

	/**
	 * 期限切れ状態
	 */
	private LeaveExpirationStatus expirationStatus;

	/**
	 * 登録種別
	 */
	private GrantRemainRegisterType registerType;

	/**
	 * 明細
	 */
	private AnnualLeaveNumberInfo details;

	/**
	 * 年休付与条件情報
	 */
	private Optional<AnnualLeaveConditionInfo> annualLeaveConditionInfo;


	public AnnualLeaveRemainingHistoryTest(){}

	public AnnualLeaveRemainingHistoryTest(String cID, String employeeId, Integer yearMonth,
			Integer clousureId, Integer closureDay, Boolean isLastDayOfMonth, GeneralDate grantDate,
			GeneralDate deadline, int expirationStatus, int registerType, double grantDays, Integer grantMinutes,
			double usedDays, Integer usedMinutes, Double stowageDays, double remainDays, Integer remainMinutes,
			double usedPercent, Double prescribedDays, Double deductedDays, Double workingDays) {
		this.cid = cID;
		this.employeeId = employeeId;
		this.yearMonth = new YearMonth(yearMonth);
		this.closureId = EnumAdaptor.valueOf(clousureId, ClosureId.class);
		this.closureDate = new ClosureDate(closureDay, isLastDayOfMonth);
		this.grantDate = grantDate;
		this.deadline = deadline;
		this.expirationStatus = EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class);
		this.registerType = EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class);

		this.details = new AnnualLeaveNumberInfo(grantDays, grantMinutes, usedDays, usedMinutes, stowageDays,
				remainDays, remainMinutes, usedPercent);

		if (prescribedDays != null && deductedDays != null && workingDays != null) {
			this.annualLeaveConditionInfo = Optional
					.of(AnnualLeaveConditionInfo.createFromJavaType(prescribedDays, deductedDays, workingDays));
		} else {
			this.annualLeaveConditionInfo = Optional.empty();
		}
		
	}

	public AnnualLeaveRemainingHistoryTest(AnnualLeaveGrantRemainingData data, YearMonth yearMonth, ClosureId clousureId,
			ClosureDate closureDate) {
		this.cid = data.getCid();
		this.employeeId = data.getEmployeeId();
		this.yearMonth = yearMonth;
		this.closureId = clousureId;
		this.closureDate = closureDate;
		this.grantDate = data.getGrantDate();
		this.deadline = data.getDeadline();
		this.expirationStatus = data.getExpirationStatus();
		this.registerType = data.getRegisterType();
		this.details = (AnnualLeaveNumberInfo) data.getDetails();
		this.annualLeaveConditionInfo = data.getAnnualLeaveConditionInfo();
	}

}