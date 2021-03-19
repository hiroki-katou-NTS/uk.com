package nts.uk.ctx.at.shared.dom.worktime.common.childcareset;

/**
 * 短時間勤務取得範囲
 * @author shuichi_ishida
 */
public enum ShortTimeWorkGetRange {
	/** 取得しない */
	NOT_GET(0),
	/** そのまま取得する */
	NORMAL_GET(1),
	/** 出退勤と重複する時間帯を除く */
	WITHOUT_ATTENDANCE_LEAVE(2);
	
	public int value;
	private ShortTimeWorkGetRange(int value){
		this.value = value;
	}
}
