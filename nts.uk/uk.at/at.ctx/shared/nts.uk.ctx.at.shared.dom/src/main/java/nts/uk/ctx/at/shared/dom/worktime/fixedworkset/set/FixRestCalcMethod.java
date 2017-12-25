package nts.uk.ctx.at.shared.dom.worktime.fixedworkset.set;

/**
 * 固定休憩の計算方法
 * @author keisuke_hoshina
 *
 */
public enum FixRestCalcMethod {
	ReferToMaster,
	ReferToSchedule,
	;
	
	/**
	 * マスタ参照か判定する
	 * @return　マスタ参照である
	 */
	public boolean isReferToMaster() {
		return this.equals(ReferToMaster);
	}
}
