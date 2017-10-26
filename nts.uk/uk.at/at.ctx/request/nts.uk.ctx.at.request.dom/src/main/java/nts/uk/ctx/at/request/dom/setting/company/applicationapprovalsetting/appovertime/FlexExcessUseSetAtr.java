package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime;

/**
 * @author loivt
 * フレックス超過時間表示区分
 */
public enum FlexExcessUseSetAtr {
	/**
	 * 表示しない
	 */
	NOTDISPLAY(0),
	/**
	 * 表示する（フレックス勤務者のみ）
	 */
	DISPLAY(1),
	/**
	 * 常に表示する
	 */
	ALWAYSDISPLAY(2);
	
	public final int value;
	
	FlexExcessUseSetAtr(int value){
		this.value = value;
	}

}
