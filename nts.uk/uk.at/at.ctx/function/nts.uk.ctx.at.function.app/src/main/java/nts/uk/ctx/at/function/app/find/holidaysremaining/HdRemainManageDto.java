package nts.uk.ctx.at.function.app.find.holidaysremaining;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;

/**
 * 休暇残数管理表の出力項目設定
 */
@Value
public class HdRemainManageDto {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * コード
	 */
	private String cd;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 年休の項目出力する
	 */
	private int yearlyHoliday;

	/**
	 * ★内半日年休を出力する
	 */
	private int insideHalfDay;

	/**
	 * 内時間年休残数を出力する
	 */
	private int insideHours;

	/**
	 * 積立年休の項目を出力する
	 */
	private int yearlyReserved;

	/**
	 * 代休の項目を出力する
	 */
	private int outItemSub;

	/**
	 * 代休未消化出力する
	 */
	private int representSub;

	/**
	 * 代休残数を出力する
	 */
	private int remainChargeSub;

	/**
	 * 振休の項目を出力する
	 */
	private int pauseItem;

	/**
	 * 振休未消化を出力する
	 */
	private int undigestedPause;

	/**
	 * 振休残数を出力する
	 */
	private int numRemainPause;

	/**
	 * 公休の項目を出力する
	 */
	private int outputItemsHolidays;

	/**
	 * 公休繰越数を出力する
	 */
	private int outputHolidayForward;

	/**
	 * 公休月度残を出力する
	 */
	private int monthlyPublic;

	/**
	 * 子の看護休暇の項目を出力する
	 */
	private int childCareLeave;

	/**
	 * 介護休暇の項目を出力する
	 */
	private int nursingCareLeave;

	public static HdRemainManageDto fromDomain(HolidaysRemainingManagement domain) {
		return new HdRemainManageDto(
				domain.getCompanyID(), domain.getCode(), domain.getName(),
				domain.getListItemsOutput().getAnnualHoliday().isYearlyHoliday() ? 1 : 0,
				domain.getListItemsOutput().getAnnualHoliday().isInsideHalfDay() ? 1 : 0,
				domain.getListItemsOutput().getAnnualHoliday().isInsideHours() ? 1 : 0,
				domain.getListItemsOutput().getYearlyReserved().isYearlyReserved() ? 1 : 0,
				domain.getListItemsOutput().getSubstituteHoliday().isOutputItemSubstitute() ? 1 : 0,
				domain.getListItemsOutput().getSubstituteHoliday().isRepresentSubstitute() ? 1 : 0,
				domain.getListItemsOutput().getSubstituteHoliday().isRemainingChargeSubstitute() ? 1 : 0,
				domain.getListItemsOutput().getPause().isPauseItem() ? 1 : 0,
				domain.getListItemsOutput().getPause().isUndigestedPause() ? 1 : 0,
				domain.getListItemsOutput().getPause().isNumberRemainingPause() ? 1 : 0,
				domain.getListItemsOutput().getHolidays().isOutputitemsholidays() ? 1 : 0,
				domain.getListItemsOutput().getHolidays().isOutputholidayforward() ? 1 : 0,
				domain.getListItemsOutput().getHolidays().isMonthlyPublic() ? 1 : 0,
				domain.getListItemsOutput().getChildNursingVacation().isChildNursingLeave() ? 1 : 0,
				domain.getListItemsOutput().getNursingcareLeave().isNursingLeave() ? 1 : 0);

	}

	public HdRemainManageDto(String cid, String cd, String name, int yearlyHoliday, int insideHalfDay, int insideHours,
			int yearlyReserved, int outItemSub, int representSub, int remainChargeSub, int pauseItem,
			int undigestedPause, int numRemainPause, int outputItemsHolidays, int outputHolidayForward,
			int monthlyPublic, int childCareLeave, int nursingCareLeave) {
		super();
		this.cid = cid;
		this.cd = cd;
		this.name = name;
		this.yearlyHoliday = yearlyHoliday;
		this.insideHalfDay = insideHalfDay;
		this.insideHours = insideHours;
		this.yearlyReserved = yearlyReserved;
		this.outItemSub = outItemSub;
		this.representSub = representSub;
		this.remainChargeSub = remainChargeSub;
		this.pauseItem = pauseItem;
		this.undigestedPause = undigestedPause;
		this.numRemainPause = numRemainPause;
		this.outputItemsHolidays = outputItemsHolidays;
		this.outputHolidayForward = outputHolidayForward;
		this.monthlyPublic = monthlyPublic;
		this.childCareLeave = childCareLeave;
		this.nursingCareLeave = nursingCareLeave;
	}
}
