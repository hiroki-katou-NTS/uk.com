package nts.uk.ctx.at.request.app.find.application.gobackdirectly;




import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
//勤務情報
@Data
@AllArgsConstructor
public class WorkInformationDto {
//	勤務種類コード
	private String workType;
//	就業時間帯コード
	private String workTime;
	
	public static WorkInformationDto fromDomain(WorkInformation value) {
		return new WorkInformationDto(
				value.getWorkTypeCode().v(),
				value.getWorkTimeCode() != null ? value.getWorkTimeCode().v() : null);
	}
	public WorkInformation toDomain() {
		WorkInformation workInformation = new WorkInformation(null, "");
		if (workTime != null) {
			workInformation.setWorkTimeCode(new WorkTimeCode(workTime));
		}
		workInformation.setWorkTypeCode(new WorkTypeCode(workType));
		return workInformation;
	}
}
