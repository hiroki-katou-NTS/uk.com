package nts.uk.ctx.at.function.dom.workledgeroutputitem;

/**
 * 集計可能な月次の勤怠項目を取得する
 *
 * @author khai.dh
 */
public interface GetAggregableMonthlyAttendanceItemAdapter {
	// 集計可能勤怠項目 ID
	int  getAggregableMonthlyAttId(String cid);
	// TODO ...QA
}
