package nts.uk.ctx.at.record.app.command.dailyperform.snapshot;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.snapshot.SnapshotDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;

public class SnapshotOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<SnapShot> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null || !item.isHaveData() ? Optional.empty() 
				: Optional.of(((SnapshotDto) item).toDomain(getEmployeeId(), getWorkDate()));
	}

	@Override
	public void updateData(Object data) {
		if(data == null){ return; }
		this.data = Optional.of((SnapShot) data);
	}
	
	@Override
	public Optional<SnapShot> toDomain() {
		return data;
	}

	@Override
	public Optional<SnapshotDto> toDto() {
		return getData().map(b -> SnapshotDto.from(getEmployeeId(), getWorkDate(), b));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateDataO(Optional<?> data) {
		this.data = (Optional<SnapShot>) data;
	}
}
