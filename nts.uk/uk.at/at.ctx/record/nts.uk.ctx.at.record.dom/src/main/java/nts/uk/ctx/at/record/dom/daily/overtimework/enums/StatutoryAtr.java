package nts.uk.ctx.at.record.dom.daily.overtimework.enums;
/**
 * 法定内区分
 * @author keisuke_hoshina
 *
 */
public enum StatutoryAtr {
	Statutory,
	Excess,
	DeformationCriterion;
	
	/**
	 * 法定内か判定する
	 * @return 法定内である
	 */
	public boolean isStatutory() {
		return this.equals(Statutory);
	}
	
	/**
	 * 法定外か判定する
	 * @return 法定外である
	 */
	public boolean isExcess() {
		return this.equals(Excess);
	}
	
	/**
	 * 基準内か判定する
	 * @return 基準内である
	 */
	public boolean isDeformationCriterion() {
		return this.equals(DeformationCriterion);
	}
}
