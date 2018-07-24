package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.SpecialVacationCD;
/**
 *  特別休暇付与残数データ
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialLeaveGrantRemainingData extends AggregateRoot {

	// 特別休暇ID
	private String specialId;

	private String cId;
	// 社員ID
	private String employeeId;
	// 特別休暇コード
	private SpecialVacationCD specialLeaveCode;
	// 付与日
	private GeneralDate grantDate;
	// 期限日
	private GeneralDate deadlineDate;
	// 期限切れ状態
	private LeaveExpirationStatus expirationStatus;
	// 登録種別
	private GrantRemainRegisterType registerType;
	// 明細
	private SpecialLeaveNumberInfo details;

	public static SpecialLeaveGrantRemainingData createFromJavaType(String specialId, String cid, String employeeId,
			int specialLeaveCode, GeneralDate grantDate, GeneralDate deadlineDate, int expirationStatus,
			int registerType, BigDecimal dayNumberOfGrant, Integer timeOfGrant, BigDecimal dayNumberOfUse,
			Integer timeOfUse, BigDecimal useSavingDays, BigDecimal numberOverdays, Integer timeOver,
			BigDecimal dayNumberOfRemain, Integer timeOfRemain) {
		if (grantDate == null && deadlineDate == null && dayNumberOfGrant == null && timeOfGrant == null
				&& dayNumberOfUse == null && timeOfUse == null && useSavingDays == null && numberOverdays == null
				&& timeOver == null && dayNumberOfRemain == null && timeOfRemain == null)
			return null;
		SpecialLeaveGrantRemainingData domain = new SpecialLeaveGrantRemainingData();

		if (dayNumberOfGrant != null || dayNumberOfUse != null || dayNumberOfRemain != null) {
			if (deadlineDate == null && grantDate == null) {
				throw new BusinessException("Msg_925", "付与日, 期限日");
			}
		}

		if (grantDate != null && deadlineDate != null) {
			// 付与日＞使用期限の場合はエラー #Msg_1023
			if (grantDate.compareTo(deadlineDate) > 0) {
				throw new BusinessException("Msg_1023");
			}
		}

		if (grantDate == null && deadlineDate != null) {
			throw new BusinessException("Msg_925", "付与日");
		}
		if (deadlineDate == null && grantDate != null) {
			throw new BusinessException("Msg_925", "期限日");
		}

		domain.specialId = specialId;
		domain.cId = cid;
		domain.employeeId = employeeId;
		domain.specialLeaveCode = new SpecialVacationCD(specialLeaveCode);
		domain.grantDate = grantDate;
		domain.deadlineDate = deadlineDate;
		domain.expirationStatus = EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class);
		domain.registerType = EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class);

		domain.details = new SpecialLeaveNumberInfo(dayNumberOfGrant, timeOfGrant, dayNumberOfUse, timeOfUse,
				useSavingDays, numberOverdays, timeOver, dayNumberOfRemain, timeOfRemain);

		return domain;
	}

	public static SpecialLeaveGrantRemainingData createFromJavaType(String specialId, String cid, String employeeId,
			int specialLeaveCode, GeneralDate grantDate, GeneralDate deadlineDate, int expirationStatus,
			int registerType, double dayNumberOfGrant, Integer timeOfGrant, double dayNumberOfUse, Integer timeOfUse,
			Double useSavingDays, double numberOverdays, Integer timeOver, double dayNumberOfRemain,
			Integer timeOfRemain) {
		SpecialLeaveGrantRemainingData domain = new SpecialLeaveGrantRemainingData();
		domain.specialId = specialId;
		domain.cId = cid;
		domain.employeeId = employeeId;
		domain.specialLeaveCode = new SpecialVacationCD(specialLeaveCode);
		domain.grantDate = grantDate;
		domain.deadlineDate = deadlineDate;
		domain.expirationStatus = EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class);
		domain.registerType = EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class);

		domain.details = new SpecialLeaveNumberInfo(dayNumberOfGrant, timeOfGrant, dayNumberOfUse, timeOfUse,
				useSavingDays, numberOverdays, timeOver, dayNumberOfRemain, timeOfRemain);

		return domain;
	}

}
