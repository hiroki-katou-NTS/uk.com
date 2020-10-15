package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting;

/*import lombok.NoArgsConstructor;*/
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.BeforeAddCheckMethod;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppAcceptLimitDay;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * 事前の受付制限, 残業申請事前の受付制限
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class BeforehandRestriction extends DomainObject {

	/**
	 * チェック方法
	 */
	private BeforeAddCheckMethod methodCheck;

	/**
	 * 利用する
	 */
	private Boolean toUse;

	/**
	 * 日数
	 */
	private AppAcceptLimitDay dateBeforehandRestriction;

	/**
	 * 時刻（早出残業・通常残業）
	 */
	private AttendanceClock timeBeforehandRestriction;

	/**
	 * 時刻（早出残業）
	 */
	private AttendanceClock preOtTime;

	/**
	 * 時刻（通常残業）
	 */
	private AttendanceClock normalOtTime;

	/**
	 * 日数 - 残業申請事前の受付制限
	 */
	private AppAcceptLimitDay otRestrictPreDay;

	/**
	 * 利用する - 残業申請事前の受付制限
	 */
	private Boolean otToUse;

	public static BeforehandRestriction toDomain(Integer methodCheck, Integer toUse, Integer dateBeforehandRestriction,
			Integer timeBeforehandRestriction, Integer preOtTime, Integer normalOtTime, Integer otRestrictPreDay,
			Integer otToUse) {
		return new BeforehandRestriction(EnumAdaptor.valueOf(methodCheck, BeforeAddCheckMethod.class),
				toUse == 1 ? true : false, EnumAdaptor.valueOf(dateBeforehandRestriction, AppAcceptLimitDay.class),
				timeBeforehandRestriction == null ? null : new AttendanceClock(timeBeforehandRestriction),
				preOtTime == null ? null : new AttendanceClock(preOtTime),
				normalOtTime == null ? null : new AttendanceClock(normalOtTime),
				EnumAdaptor.valueOf(otRestrictPreDay, AppAcceptLimitDay.class), otToUse == 1 ? true : false);
	}

}
