package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻カード.指定社員リストの打刻カード番号を生成する.打刻カード作成方法
 * @author chungnt
 *
 */
public enum MakeEmbossedCard {

	/**
	 * 0 - 社員コード
	 */
	EMPLOYEE_CODE(0),
	/**
	 * 1 - 会社コードと社員コード
	 */
	COMPANY_CODE_AND_EMPLOYEE_CODE(1);

	public final int value;

	MakeEmbossedCard(int value) {
		this.value = value;
	}
}