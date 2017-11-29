package nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset;

/**
 * 流動休憩の計算方法
 * @author keisuke_hoshina
 *
 */
public enum FlowRestCalcMethod{
	ReferToMaster,
	ConbineMasterWithStamp,
	;
	
	/**
	 * マスタを参照するであるか判定する
	 * @return　マスタ参照である
	 */
	public boolean isReferToMaster() {
		return ReferToMaster.equals(this);
	}
}
