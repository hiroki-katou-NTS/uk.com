package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkTypeShiftDto {
	/* 勤務種類コード */
	private String workTypeCd;
	/* 勤務種類名称 */
	private String workTypeName;
	/* 勤務種類略名 */
	private String abbreviationName;
	/* 勤務種類記号名 */
	private String symbolicName;
	/* 廃止区分 */
	private int abolishAtr;
	/* 勤務種類備考 */
	private String memo;
	/* 勤務の単位 */
	private int workAtr;
	/* 1日 */
	private int oneDayCls;
	/* 午前 */
	private int morningCls;
	/* 午後 */
	private int afternoonCls;
	/* 出勤率の計算方法 */
	private int calculatorMethod;

	private List<WorkTypeSetDto> workTypeSets;
	
	private Integer dispOrder;
}
