package nts.uk.ctx.at.shared.dom.adapter.application.reflect;

/**
 * 予定と実績を同じに変更する設定
 * @author thanh_nx
 *
 */
public enum SHClassifyScheAchieveAtr {
	/**
	 * 自動変更しない
	 */
	DO_NOT_CHANGE_AUTO(0),
	/**
	 * 常に自動変更する
	 */
	ALWAYS_CHANGE_AUTO(1),
	/**
	 * 流動勤務のみ自動変更する
	 */
	AUTO_CHANGE_ONLY_WORK(2);
	public final Integer value;
	SHClassifyScheAchieveAtr(Integer value){
		this.value = value;
	}
}
