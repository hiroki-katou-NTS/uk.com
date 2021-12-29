package nts.uk.ctx.at.shared.dom.vacation.setting;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.license.option.OptionLicense;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.休暇.時間休暇の消化単位
 * @author NWS-DungDV
 *
 */
@Getter
@AllArgsConstructor
public class TimeVacationDigestUnit extends ValueObject {
	// 管理区分
	private final ManageDistinct manage;
	
	// 消化単位：時間休暇の消化単位を設定する
	private final TimeDigestiveUnit digestUnit;
	
	/**
	 * [1] 消化単位をチェックする
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.休暇.<<Class>> 時間休暇の消化単位.<<Public>>消化単位をチェックする.消化単位をチェックする
	 * @param require Require
	 * @param time 休暇使用時間
	 * @param manage 管理区分
	 */
	public boolean checkDigestUnit(Require require, AttendanceTime time, ManageDistinct manage) {
		boolean isVacationTimeManage = this.isVacationTimeManage(require, manage);

		if (!isVacationTimeManage) {
			return true;
		}

		return time.v() % TimeDigestiveUnit.toMinus(this.digestUnit) == 0;
	}
	
	/**
	 * [2] 時間休暇が管理するか
	 * @param require Require
	 * @param manage 管理区分
	 */
	public boolean isVacationTimeManage(Require require, ManageDistinct manage) {
		OptionLicense option = require.getOptionLicense();
		if (!option.attendance().hourlyPaidLeave()) {
			return false;
		}
		
		return manage == ManageDistinct.YES && this.manage == ManageDistinct.YES;
	}
	
	/**
	 * [3] 時間休暇が管理するか
	 * @param require Require
	 */
	public boolean isVacationTimeManage(Require require) {
		OptionLicense option = require.getOptionLicense();
		if (!option.attendance().hourlyPaidLeave()) {
			 return false;
		}
		
		return this.manage == ManageDistinct.YES;
	}
	
	/**
	 * [4] 消化単位をチェックする
	 * @param require Require
	 * @param time 休暇使用時間
	 */
	public boolean checkDigestUnit(Require require, AttendanceTime time) {
		boolean timeManageCls = this.isVacationTimeManage(require);
		if (!timeManageCls) {
			return true;
		}
		
		return time.v() % TimeDigestiveUnit.toMinus(this.digestUnit) == 0;
	}
	
	public static interface Require {
		/**
		 * [R-1] オプションライセンスを取得する
		 */
		public OptionLicense getOptionLicense();
	}
}
