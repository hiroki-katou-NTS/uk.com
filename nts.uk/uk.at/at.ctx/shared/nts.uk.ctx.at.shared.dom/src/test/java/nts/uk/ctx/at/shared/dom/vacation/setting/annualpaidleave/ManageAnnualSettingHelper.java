package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

public class ManageAnnualSettingHelper {
	public static ManageAnnualSetting createManageAnnualSetting(HalfDayManage dayManage) {
		RemainingNumberSetting numberSetting = new RemainingNumberSetting(new RetentionYear(1));
		return new ManageAnnualSetting(dayManage, numberSetting, new YearLyOfNumberDays(0.1));
	}
}
