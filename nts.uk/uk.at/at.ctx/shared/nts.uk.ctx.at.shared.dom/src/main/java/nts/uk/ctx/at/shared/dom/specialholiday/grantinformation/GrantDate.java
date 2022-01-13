package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.specialholiday.NextSpecialHolidayGrantParameter;

/**
 * 付与基準日
 * 
 * @author tanlv
 *
 */
public enum GrantDate {

	/**
	 * 入社日を付与基準日とする
	 */
	EMP_GRANT_DATE(0),
	/**
	 * 年休付与基準日を付与基準日とする
	 */
	GRANT_BASE_HOLIDAY(1),
	/**
	 * 特別休暇付与基準日を付与基準日とする
	 */
	SPECIAL_LEAVE_DATE(2);
	
	public int value;
	
	GrantDate(int type){
		this.value = type;
	}
	
	public static GrantDate toEnum(int value){
		return EnumAdaptor.valueOf(value, GrantDate.class);
	}
	
	/**
	 * 付与基準日を取得する
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 付与基準日
	 */
	public Optional<GeneralDate> getSpecialLeaveGrantDate(
			Require require,
			CacheCarrier cacheCarrier,
			NextSpecialHolidayGrantParameter parameter) {


		// ドメインモデル「特別休暇．付与情報．付与基準日」をチェックする
		switch (this) {
		case EMP_GRANT_DATE: // 入社日を付与基準日とする
			return parameter.getEntryDate(require, cacheCarrier);
			
		case GRANT_BASE_HOLIDAY:// 年休付与基準日を付与基準日とする
			return parameter.getAnnualLeaveGrantDate(require, cacheCarrier);
			
		case SPECIAL_LEAVE_DATE:// 特別休暇付与基準日を付与基準日とする
			return parameter.getSpecialHolidayGrantDate();
			
		default:
			return Optional.empty();
		}
	}
	public  static interface Require extends NextSpecialHolidayGrantParameter.Require{
		
	}
	
	
}
