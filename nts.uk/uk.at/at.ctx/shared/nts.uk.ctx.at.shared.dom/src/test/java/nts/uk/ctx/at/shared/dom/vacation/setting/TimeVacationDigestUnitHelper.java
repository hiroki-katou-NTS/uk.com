package nts.uk.ctx.at.shared.dom.vacation.setting;

public class TimeVacationDigestUnitHelper {

	/**
	 * Create default data with 管理区分=管理する and 消化単位=1分
	 * @return 時間休暇の消化単位
	 */
	public static TimeVacationDigestUnit createDefault() {
		ManageDistinct manage = ManageDistinct.YES;
		TimeDigestiveUnit digestUnit = TimeDigestiveUnit.OneMinute;
		return new TimeVacationDigestUnit(manage, digestUnit);
	}
}
