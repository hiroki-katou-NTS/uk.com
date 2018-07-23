/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.pub.command.mastercopy;

/**
 * The Enum GlobalCopyItemEnum.
 */
// _処理方法
public enum GlobalCopyItemEnum {

	/** The role. */
	ROLE(1, "ロール"),

	/** The Personal information category. */
	Personal_information_category(2, "個人情報カテゴリ"),

	/** The Personal information item definition. */
	Personal_information_item_definition(3, "個人情報項目定義"),

	/** The Personal information category ordering. */
	Personal_information_category_ordering(4, "個人情報カテゴリ並び順"),

	/** The Personal information item order. */
	Personal_information_item_order(5, "個人情報項目並び順"),

	/** The My page setting. */
	My_page_setting(6, "マイページ設定"),

	/** The Flow menu. */
	Flow_menu(7, "フローメニュー"),

	/** The Standard menu. */
	Standard_menu(8, "標準メニュー"),

	/** The Standard widget. */
	Standard_widget(9, "標準ウィジェット"),

	/** The Top page parts usage setting. */
	Top_page_Parts_usage_setting(10, "トップページ部品利用設定"),

	/** The Employee code edit settings. */
	Employee_code_edit_settings(11, "社員コード編集設定"),

	/** The Setting position specification. */
	Setting_position_specification(12, "職位指定の設定"),

	/** The Authorization settings. */
	Authorization_settings(13, "承認設定"),

	/** The Top page alarm setting. */
	Top_page_Alarm_setting(14, "トップページアラーム設定"),

	/** The Sixteen H super break management setting. */
	Com60HourVacation(15, "60H超休管理設定"),

	/** The Shake management setting. */
	ComSubstVacation(16, "振休管理設定"),

	/** The Substitution management setting. */
	CompensatoryLeaveComSetting(17, "代休管理設定"),

	/** The Shelt year holiday setting. */
	RetentionYearlySetting(18, "積立年休設定"),

	/** The Year holidays setting. */
	AnnualPaidLeaveSetting(19, "年休設定"),

	/** The Nursing care leave setting. */
	Nursing_care_leave_setting(20, "看護介護休暇設定"),

	/** The To tighten. */
	To_tighten(21, "締め"),

	/** The Tightening change history. */
	Tightening_change_history(22, "締め変更履歴"),

	/** The Overtime frame. */
	Overtime_frame(23, "残業枠"),

	/** The Holiday frame. */
	Holiday_frame(24, "休出枠"),

	/** The Multiple duty management. */
	Multiple_duty_management(25, "複数回勤務管理"),

	/** The Temporary work use management. */
	Temporary_work_use_management(26, "臨時勤務利用管理"),

	/** The Temporary work management. */
	Temporary_work_management(27, "臨時勤務管理"),

	/** The Entry and exit management. */
	Entry_and_exit_management(28, "入退門管理"),

	/** The Regular working hours by company. */
	Regular_working_hours_by_company(29, "会社別通常勤務労働時間"),

	/** The Transformed labor hours by company. */
	Transformed_labor_hours_by_company(30, "会社別変形労働労働時間"),

	/** The Deviation time. */
	Deviation_time(31, "乖離時間"),

	/** The How to enter the deviation reason. */
	How_to_enter_the_deviation_reason(32, "乖離理由の入力方法"),

	/** The Extra time item. */
	Extra_time_item(33, "割増時間項目"),

	/** The Personnel cost calculation setting. */
	Personnel_cost_calculation_setting(34, "人件費計算設定"),

	/** The Total count. */
	Total_count(35, "回数集計"),

	/** The Total evaluation item. */
	Total_Evaluation_Item(36, "横計集計項目"),

	/** The Weekly work setting. */
	Weekly_work_setting(37, "週間勤務設定"),

	/** The Function control to be scheduled. */
	Function_control_to_be_scheduled(38, "勤務予定の機能制御"),

	/** The Display control scheduled to work. */
	Display_control_scheduled_to_work(39, "勤務予定の表示制御"),

	/** The Displayable work type control. */
	Displayable_work_type_control(40, "表示可能勤務種類制御"),

	/** The Linking planned and time items. */
	Linking_planned_and_time_items(41, "予定項目と勤怠項目の紐付け"),

	/** The Scheduled items. */
	Scheduled_items(42, "予定項目"),

	/** The Order of planned items. */
	Order_of_planned_items(43, "予定項目の並び順"),

	/** The Editing cards. */
	Editing_cards(44, "打刻カード編集"),

	/** The Any item. */
	Any_item(45, "任意項目"),

	/** The Error in performance work alarm. */
	Error_in_performance_work_alarm(46, "勤務実績のエラーアラーム"),

	/** The Control of daily attendance items. */
	Control_of_daily_attendance_items(47, "日次の勤怠項目の制御"),

	/** The Control of monthly time items. */
	Control_of_monthly_time_items(48, "月次の勤怠項目の制御"),

	/** The Display and input control of monthly time item. */
	Display_and_input_control_of_monthly_time_item(49, "月次の勤怠項目の表示・入力制御"),

	/** The Daily attendance items. */
	Daily_attendance_items(50, "日次の勤怠項目"),

	/** The Monthly attendance item. */
	Monthly_attendance_item(51, "月次の勤怠項目"),

	/** The Set holiday addition time. */
	Set_holiday_addition_time(52, "休暇加算時間設定"),

	/** The Additional setting management of working hours. */
	Additional_setting_management_of_working_hours(53, "就業時間の加算設定管理"),

	/** The Additional setting for regular work. */
	Additional_setting_for_regular_work(54, "通常勤務の加算設定"),

	/** The Additional setting of deformed labor work. */
	Additional_setting_of_deformed_labor_work(55, "変形労働勤務の加算設定"),

	/** The Additional settings for flex work. */
	Additional_settings_for_flex_work(56, "フレックス勤務の加算設定"),

	/** The Additional hourly payment setting. */
	Additional_hourly_payment_setting(57, "時給者の加算設定"),

	/** The Zero time straddle calculation setting. */
	Zero_time_straddle_calculation_setting(58, "0時跨ぎ計算設定"),

	/** The Calculation of total constraint time. */
	Calculation_of_total_constraint_time(59, "総拘束時間の計算"),

	/** The Judgment on handling of outflow. */
	Judgment_on_handling_of_outflow(60, "流動外出の扱い判断"),

	/** The Control of upper limit of total working hours. */
	Control_of_upper_limit_of_total_working_hours(61, "総労働時間の上限値制御"),

	/** The Daily calculation setting for flex work. */
	Daily_Calculation_Setting_for_Flex_Work(62, "フレックス勤務の日別計算設定"),

	/** The Statutory overtime calculation of deformed labor. */
	Statutory_overtime_calculation_of_deformed_labor(63, "変形労働の法定内残業計算"),

	/** The Stamp imprinting management. */
	Stamp_imprinting_management(64, "打刻反映管理"),

	/** The Outing management. */
	Outing_management(65, "外出管理"),

	/** The Late night time zone. */
	Late_night_time_zone(66, "深夜時間帯"),

	/** The Flex work setting. */
	Flex_work_setting(67, "フレックス勤務の設定"),

	/** The Aggregate settings for deformed labor. */
	Aggregate_settings_for_deformed_labor(68, "変形労働の集計設定"),

	/**
	 * The Regular setting for monthly actual result by regular labor company.
	 */
	Regular_setting_for_monthly_actual_result_by_regular_labor_company(69, "通常勤務労働会社別月別実績集計設定"),

	/** The Total setting of monthly results by deforming labor companies. */
	Total_setting_of_monthly_results_by_deforming_labor_companies(70, "変形労働会社別月別実績集計設定"),

	/** The Flex calculation by monthly results by company. */
	Flex_Calculation_by_monthly_results_by_company(71, "フレックス会社別月別実績集計設定"),

	/** The Vertical method for monthly performance. */
	Vertical_method_for_monthly_performance(72, "月別実績の縦計方法"),

	/** The Legal internal transfer order setting for monthly total. */
	Legal_internal_transfer_order_setting_for_monthly_total(73, "月次集計の法定内振替順設定"),

	/** The Rounding setting of monthly performance. */
	Rounding_setting_of_monthly_performance(74, "月別実績の丸め設定"),

	/** The Reason for discrepancy. */
	Reason_for_discrepancy(75, "乖離定型理由"),

	/** The Reason for application. */
	Reason_for_application(76, "申請定型理由"),

	/** The UR L embedding setting of mail content. */
	URL_embedding_setting_of_mail_content(77, "メール内容のURL埋込設定"),

	/** The Contents of e mail with holiday instruction. */
	Contents_of_e_mail_with_holiday_instruction(78, "休出指示のメール内容"),

	/** The Contents of overtime work instruction mail. */
	Contents_of_overtime_work_instruction_mail(79, "残業指示のメール内容"),

	/** The Application approval mail template. */
	Application_approval_mail_template(80, "申請承認メールテンプレート"),

	/** The Contents of remand mail. */
	Contents_of_remand_mail(81, "差し戻しのメール内容"),

	/** The Vacation application type display name. */
	Vacation_application_type_display_name(82, "休暇申請種類表示名"),

	/** The Application display name. */
	Application_display_name(83, "申請表示名"),

	/** The Application approval setting. */
	Application_approval_setting(84, "申請承認設定"),

	/** The Vacation application setting. */
	Vacation_application_setting(85, "休暇申請設定"),

	/** The Leave reflection setting. */
	Leave_reflection_setting(86, "休暇反映設定"),

	/** The Work change change application setting. */
	Work_change_change_application_setting(87, "勤務変更申請設定"),

	/** The Embossing request setting. */
	Embossing_request_setting(88, "打刻申請設定"),

	/** The Set up for withdrawal request. */
	Set_up_for_withdrawal_request(89, "振休振出申請設定"),

	/** The Time holiday application setting. */
	Time_holiday_application_setting(90, "時間休申請設定"),

	/** The Late early withdrawal cancellation request setting. */
	Late_early_withdrawal_cancellation_request_setting(91, "遅刻早退取消申請設定"),

	/** The Setting for withdrawal application. */
	Setting_for_withdrawal_application(92, "休出申請設定"),

	/** The Overtime application setting. */
	Overtime_application_setting(93, "残業申請設定"),

	/** The Trip request setting. */
	Trip_request_setting(94, "出張申請設定"),

	/** The Direct bilateral application common setting. */
	Direct_bilateral_application_common_setting(95, "直行直帰申請共通設定"),

	/** The Company specific application approval setting. */
	Company_specific_application_approval_setting(96, "会社別申請承認設定");

	/** The value. */
	public final int value;

	/** The description. */
	public final String copyTargetString;

	/** The Constant values. */
	private final static GlobalCopyItemEnum[] values = GlobalCopyItemEnum.values();

	/**
	 * Instantiates a new global copy item enum.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private GlobalCopyItemEnum(int value, String copyTargetString) {
		this.value = value;
		this.copyTargetString = copyTargetString;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the global copy item enum
	 */
	public static GlobalCopyItemEnum valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (GlobalCopyItemEnum val : GlobalCopyItemEnum.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
