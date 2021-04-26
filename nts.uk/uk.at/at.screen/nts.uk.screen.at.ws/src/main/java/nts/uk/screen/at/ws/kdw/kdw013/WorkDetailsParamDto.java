package nts.uk.screen.at.ws.kdw.kdw013;

import java.util.Optional;

import lombok.Setter;
import nts.uk.ctx.at.record.dom.daily.ouen.SupportFrameNo;
import nts.uk.ctx.at.record.dom.daily.ouen.WorkDetailsParam;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkinputRemarks;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;

@Setter
public class WorkDetailsParamDto {

	// 応援勤務枠No: 応援勤務枠No
	private int supportFrameNo;

	// 時間帯: 時間帯
	private TimeZoneDto timeZone;

	// 作業グループ
	private WorkGroupDto workGroup;

	// 備考: 作業入力備考
	private String remarks;

	// 勤務場所: 勤務場所コード
	private String workLocationCD;

	public static WorkDetailsParamDto toDto(WorkDetailsParam param) {
		WorkDetailsParamDto result = new WorkDetailsParamDto();

		result.setSupportFrameNo(param.getSupportFrameNo().v());
		result.setTimeZone(param.getTimeZone() != null ? TimeZoneDto.toDto(param.getTimeZone()) : null);
		result.setWorkGroup(param.getWorkGroup().isPresent() ? WorkGroupDto.toDto(param.getWorkGroup().get()) : null);
		result.setRemarks(param.getRemarks().map(m -> m.v()).orElse(null));
		result.setWorkLocationCD(param.getWorkLocationCD().map(m -> m.v()).orElse(null));

		return result;
	}

	public WorkDetailsParam toDomain() {
		return new WorkDetailsParam(new SupportFrameNo(this.supportFrameNo), new TimeZoneDto().toDomain(),
				Optional.of(WorkGroup.create(workGroup.getWorkCD1(), workGroup.getWorkCD2(), workGroup.getWorkCD3(),
						workGroup.getWorkCD4(), workGroup.getWorkCD5())),
				Optional.of(new WorkinputRemarks(this.remarks)), Optional.of(new WorkLocationCD(this.workLocationCD)));
	}
}
