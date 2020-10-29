package nts.uk.ctx.at.function.dom.scheduletable;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * 1行の出力項目
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.スケジュール表.1行の出力項目
 * @author dan_pv
 *
 */
@AllArgsConstructor
@Getter
public class OneRowOutputItem implements DomainValue {
	
	/**
	 * 個人情報
	 */
	private final Optional<ScheduleTablePersonalInfoItem> personalInfo;
	
	/**
	 * 追加列情報
	 */
	private final Optional<ScheduleTablePersonalInfoItem> additionalInfo;
	
	/**
	 * 勤怠項目
	 */
	private final Optional<ScheduleTableAttendanceItem> attendanceItem;
	
	/**
	 * 作る
	 * @param personalInfo　個人情報
	 * @param additionalInfo　追加列情報
	 * @param attendanceItem　勤怠項目
	 * @return
	 */
	public static OneRowOutputItem create(
			Optional<ScheduleTablePersonalInfoItem> personalInfo, 
			Optional<ScheduleTablePersonalInfoItem> additionalInfo, 
			Optional<ScheduleTableAttendanceItem> attendanceItem
			) {
		
		if ( !personalInfo.isPresent() && !additionalInfo.isPresent() && !attendanceItem.isPresent()) {
			throw new BusinessException("Msg_1971");
		}
		
		return new OneRowOutputItem(personalInfo, additionalInfo, attendanceItem);
	}
	
	/**
	 * 指定する個人情報項目があるか
	 * @param personalInfoItem
	 * @return
	 */
	public boolean hasThisPersonalItem( ScheduleTablePersonalInfoItem personalInfoItem ) {
		return ( this.personalInfo.isPresent() && this.personalInfo.get() == personalInfoItem )
				|| ( this.additionalInfo.isPresent() && this.additionalInfo.get() == personalInfoItem);
	}
	
	/**
	 * 指定する勤怠項目があるか
	 * @param attendanceItem
	 * @return
	 */
	public boolean hasThisAttendanceItem( ScheduleTableAttendanceItem attendanceItem ) {
		return this.attendanceItem.isPresent() && this.attendanceItem.get() == attendanceItem;
	}
	

}
