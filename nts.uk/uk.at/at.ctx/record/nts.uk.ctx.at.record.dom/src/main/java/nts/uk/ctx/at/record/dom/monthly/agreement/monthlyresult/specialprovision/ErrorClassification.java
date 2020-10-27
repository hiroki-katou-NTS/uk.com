package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

/**
 * 超過エラー区分
 * @author quang.nh1
 */
public enum ErrorClassification {

	APPROVER_NOT_SET(0,"承認者未設定"),

	ONE_MONTH_MAX_TIME(1,"1ヶ月の上限時間超過"),

	TWO_MONTH_MAX_TIME(2,"2ヶ月平均の上限時間超過"),

	THREE_MONTH_MAX_TIME(3,"3ヶ月平均の上限時間超過"),

	FOUR_MONTH_MAX_TIME(4,"4ヶ月平均の上限時間超過"),

	FIVE_MONTH_MAX_TIME(5,"5ヶ月平均の上限時間超過"),

	SIX_MONTH_MAX_TIME(6,"6ヶ月平均の上限時間超過"),

	OVERTIME_LIMIT_ONE_YEAR(7,"1年間の上限時間超過"),

	EXCEEDING_MAXIMUM_NUMBER(8,"上限回数超過");

	public int value;

	public String nameId;

	ErrorClassification(int type, String nameId){
		this.value = type;
		this.nameId = nameId;
	}
}
