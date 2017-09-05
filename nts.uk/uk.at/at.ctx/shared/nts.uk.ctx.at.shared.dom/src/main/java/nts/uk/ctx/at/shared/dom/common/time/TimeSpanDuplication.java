package nts.uk.ctx.at.shared.dom.common.time;

import nts.uk.ctx.at.shared.dom.common.RangeDuplication;

/**
 * 時間帯の重複状態
 * @author keisuke_hoshina
 *
 */
public enum TimeSpanDuplication {

	SAME_SPAN,
	CONNOTATE_BEGINTIME,
	CONNOTATE_ENDTIME,
	CONTAINED,
	CONTAINS,
	NOT_DUPLICATE
	;
	
	public boolean isDuplicated() {
		return !this.equals(NOT_DUPLICATE);
	}
	
	/**
	 * ２つの時間帯の位置関係→時間帯の重複状態への変換
	 * @param range
	 * @return　時間帯の重複状況
	 */
	public static TimeSpanDuplication createFrom(RangeDuplication range) {
		switch (range) {
		default:
			throw new RuntimeException("unknown value: " + range);
			
		case SAME_SPAN:
			return SAME_SPAN;
			
		case COMPARED_START_BETWEEN_BASE:
			return CONNOTATE_BEGINTIME;
			
		case COMPARED_END_BETWEEN_BASE:
			return CONNOTATE_ENDTIME;

		case BASE_CONTAINS_COMPLETE:
		case BASE_CONTAINS_SAME_START:
		case BASE_CONTAINS_SAME_END:
			return CONTAINS;
			
		case BASE_CONTAINED_COMPLETE:
		case BASE_CONTAINED_SAME_START:
		case BASE_CONTAINED_SAME_END:
			return CONTAINED;
			
		case CONTINUOUS_AFTER_BASE:
		case CONTINUOUS_BEFORE_BASE:
		case BASE_BEFORE_COMPARED:
		case BASE_AFTER_COMPARED:
			return NOT_DUPLICATE;
		}
	}
}
