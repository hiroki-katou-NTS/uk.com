package nts.uk.ctx.at.function.app.command.holidaysremaining;

import java.util.List;

import lombok.Value;

@Value
public class HdRemainManageCommand {

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
	 * 介護休暇の項目を出力する
	 */
	private boolean nursingLeave;

	/**
	 * 代休残数を出力する
	 */
	private boolean remainingChargeSubstitute;

	/**
	 * 代休未消化出力する
	 */
	private boolean representSubstitute;
	/**
	 * 代休の項目を出力する
	 */
	private boolean outputItemSubstitute;

	/**
	 * 公休繰越数を出力する
	 */
	private boolean outputholidayforward;
	/**
	 * 公休月度残を出力する
	 */
	private boolean monthlyPublic;
	/**
	 * 公休の項目を出力する
	 */
	private boolean outputitemsholidays;

	/**
	 * childNursingLeave
	 */
	private boolean ChildNursingLeave;

	/**
	 * 年休の項目出力する
	 */
	private boolean yearlyHoliday;

	/**
	 * 内時間年休残数を出力する
	 */
	private boolean insideHours;

	/**
	 * ★内半日年休を出力する
	 */
	private boolean insideHalfDay;

	/**
	 * 振休残数を出力する
	 */
	private boolean numberRemainingPause;
	/**
	 * 振休未消化を出力する
	 */
	private boolean undigestedPause;
	/**
	 * 振休の項目を出力する
	 */
	private boolean pauseItem;

	/**
	 * 積立年休の項目を出力する
	 */
	private boolean yearlyReserved;
	List<String> specialHolidays;
	public HdRemainManageCommand(String cid, String cd, String name, boolean nursingLeave,
			boolean remainingChargeSubstitute, boolean representSubstitute, boolean outputItemSubstitute,
			boolean outputholidayforward, boolean monthlyPublic, boolean outputitemsholidays, boolean childNursingLeave,
			boolean yearlyHoliday, boolean insideHours, boolean insideHalfDay, boolean numberRemainingPause,
			boolean undigestedPause, boolean pauseItem, boolean yearlyReserved, List<String> specialHolidays ) {
		super();
		this.cid = cid;
		this.cd = cd;
		this.name = name;
		this.nursingLeave = nursingLeave;
		this.remainingChargeSubstitute = remainingChargeSubstitute;
		this.representSubstitute = representSubstitute;
		this.outputItemSubstitute = outputItemSubstitute;
		this.outputholidayforward = outputholidayforward;
		this.monthlyPublic = monthlyPublic;
		this.outputitemsholidays = outputitemsholidays;
		ChildNursingLeave = childNursingLeave;
		this.yearlyHoliday = yearlyHoliday;
		this.insideHours = insideHours;
		this.insideHalfDay = insideHalfDay;
		this.numberRemainingPause = numberRemainingPause;
		this.undigestedPause = undigestedPause;
		this.pauseItem = pauseItem;
		this.yearlyReserved = yearlyReserved;
		this.specialHolidays = specialHolidays;
	}

}
