package nts.uk.ctx.at.record.dom.dailyprocess.calc;

public enum DeductionAtr {
	Deduction,
	Appropriate;
	
	/**
	 * 控除用であるか判定する
	 * @return　控除用である
	 */
	public boolean isDeduction() {
		return Deduction.equals(this);
	}
	
	/**
	 * 計上用であるか判定する
	 * @return　計上用である
	 */
	public boolean isAppropriate() {
		return Appropriate.equals(this);
	}
}

