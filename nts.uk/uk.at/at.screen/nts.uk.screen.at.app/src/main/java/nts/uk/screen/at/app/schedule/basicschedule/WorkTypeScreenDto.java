package nts.uk.screen.at.app.schedule.basicschedule;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeAbbreviationName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeMemo;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSymbolicName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

@Data
@NoArgsConstructor
public class WorkTypeScreenDto {
	// 勤務種類コード
	private String workTypeCode;
	// 勤務種類名称 */
	private String name;
	// 勤務種類略名
	private String abbreviationName;
	// 勤務種類記号名
	private String symbolicName;
	// 勤務種類備考
	private String memo;
	// 勤務区分
	private int workTypeUnit;
	// 1日
	private int oneDay;
	// 午前
	private int morning;
	// 午後
	private int afternoon;

	public WorkTypeScreenDto(String workTypeCode, String name, String abbreviationName, String symbolicName,
			String memo, int workTypeUnit, int oneDay, int morning, int afternoon) {
		super();
		this.workTypeCode = workTypeCode;
		this.name = name;
		this.abbreviationName = abbreviationName;
		this.symbolicName = symbolicName;
		this.memo = memo == null ? "" : memo;
		this.workTypeUnit = workTypeUnit;
		this.oneDay = oneDay;
		this.morning = morning;
		this.afternoon = afternoon;
	}

	public WorkType convertToDomain() {
		return new WorkType(new WorkTypeCode(workTypeCode), new WorkTypeSymbolicName(symbolicName),
				new WorkTypeName(name), new WorkTypeAbbreviationName(abbreviationName), new WorkTypeMemo(memo),
				new DailyWork(EnumAdaptor.valueOf(workTypeUnit, WorkTypeUnit.class),
						EnumAdaptor.valueOf(oneDay, WorkTypeClassification.class),
						EnumAdaptor.valueOf(morning, WorkTypeClassification.class),
						EnumAdaptor.valueOf(afternoon, WorkTypeClassification.class)));
	}
}
