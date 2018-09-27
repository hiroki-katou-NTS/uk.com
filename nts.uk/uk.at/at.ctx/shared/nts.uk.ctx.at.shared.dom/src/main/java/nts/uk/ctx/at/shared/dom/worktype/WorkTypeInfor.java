package nts.uk.ctx.at.shared.dom.worktype;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkTypeInfor {

	/* 勤務種類コード */
	private String workTypeCode;
	/* 勤務種類名称 */
	private String name;
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

	private Integer dispOrder;
	
	private List<WorkTypeSetDto> workTypeSets;
	
	
	public void setWorkTypeSet(List<WorkTypeSetDto> workTypeList) {
		this.workTypeSets = workTypeList;
	}

	public WorkTypeInfor(String workTypeCode, String name, String abbreviationName, String symbolicName, int abolishAtr,
			String memo, int workAtr, int oneDayCls, int morningCls, int afternoonCls, int calculatorMethod,
			Integer dispOrder) {
		super();
		this.workTypeCode = workTypeCode;
		this.name = name;
		this.abbreviationName = abbreviationName;
		this.symbolicName = symbolicName;
		this.abolishAtr = abolishAtr;
		this.memo = memo;
		this.workAtr = workAtr;
		this.oneDayCls = oneDayCls;
		this.morningCls = morningCls;
		this.afternoonCls = afternoonCls;
		this.calculatorMethod = calculatorMethod;
		this.dispOrder = dispOrder;
	}

}
