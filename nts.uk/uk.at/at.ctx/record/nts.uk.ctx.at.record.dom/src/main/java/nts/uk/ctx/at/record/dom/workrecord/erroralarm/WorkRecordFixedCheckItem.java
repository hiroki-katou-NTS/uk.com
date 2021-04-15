package nts.uk.ctx.at.record.dom.workrecord.erroralarm;
/**
 * 日次の抽出するデータの条件
 * @author tutk
 *
 */
public enum WorkRecordFixedCheckItem {
	/**1:勤務種類未登録	 */
	WORK_TYPE_NOT_REGISTERED(1,"勤務種類未登録"),
	/**2: 就業時間帯未登録	 */
	WORKING_HOURS_NOT_REGISTERED(2,"就業時間帯未登録"),
	/**3:本人未確認	 */
	UNIDENTIFIED_PERSON(3,"本人未確認"),
	/**4:管理者未確認	 */
	DATA_CHECK(4,"管理者未確認"),
	/**5: データのチェック	 */
	ADMINISTRATOR_NOT_CONFIRMED(5,"データのチェック"),
	/**6: 連続休暇チェック	 */
	CONTINUOUS_VATATION_CHECK(6,"連続休暇チェック"),
	/**7: 打刻漏れ(入退門)	 */
	GATE_MISS_STAMP(7, "打刻漏れ(入退門)"),
	/**8: 打刻順序不正	 */
	MISS_ORDER_STAMP(8,"打刻順序不正");
	public int value;
	
	public String nameId;
	
	private WorkRecordFixedCheckItem (int value,String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
