package nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FrameCategory {
	
	/* 残業 */
	OverTime(0),
	/* 残業振替 */
	OverTimeTranfer(1),
	/* 休出 */
	Rest(2),
	/* 休出振替 */
	RestTranfer(3),
	/* 割増項目 */
	ExtraItem(4),
	/* 加給時間項目 */
	AddtionTimeItem(5),
	/* 特定加給時間項目 */
	SpecificAddtionTimeItem(6),
	/* 乖離時間項目 */
	DivergenceTimeItem(7),
	/* 任意項目 */
	AnyItem(8),
	/* 外出 */
	GoOut(9),
	/* 特定日 */
	SpecificDate(10);
	
	public final int value;

}
