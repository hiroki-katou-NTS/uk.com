package nts.uk.screen.at.app.ksu003.start.dto;




import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
//勤務情報
@Data
@AllArgsConstructor
public class WorkInforDto {
//	勤務種類コード
	private String workTypeCode;
//	就業時間帯コード
	private String workTimeCode;
	
	public static WorkInforDto fromDomain(WorkInformation value) {
		return new WorkInforDto(
				value.getWorkTypeCode().v(),
				value.getWorkTimeCode() != null ? value.getWorkTimeCode().v() : null);
	}
	public WorkInformation toDomain() {
		WorkInformation workInformation = new WorkInformation(null, "");
		if (workTimeCode != null) {
			workInformation.setWorkTimeCode(new WorkTimeCode(workTimeCode));
		}
		workInformation.setWorkTypeCode(new WorkTypeCode(workTypeCode));
		return workInformation;
	}
}
