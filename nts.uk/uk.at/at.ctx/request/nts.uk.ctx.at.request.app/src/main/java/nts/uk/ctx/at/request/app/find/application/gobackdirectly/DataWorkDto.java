package nts.uk.ctx.at.request.app.find.application.gobackdirectly;



import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.DataWork;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforWorkTime;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforWorkType;
//勤務情報
@Data
@AllArgsConstructor
public class DataWorkDto {
//	勤務種類コード
	private InforWorkType workType;
//	就業時間帯コード
	private InforWorkTime workTime;
	
	public static DataWorkDto fromDomain(DataWork value) {
		return new DataWorkDto(
				value.getWorkType(),
				value.getWorkTime().isPresent() ? value.getWorkTime().get(): null);
	}
	public DataWork toDomain() {
		Optional<InforWorkTime> optionalWorkTime = Optional.ofNullable(null);
		if (workTime != null) {
			optionalWorkTime = Optional.of(new InforWorkTime(workTime.getWorkTime(), workTime.getNameWorkTime()));
		}
		return new DataWork(
				new InforWorkType(workType.getWorkType(), workType.getNameWorkType()),
				optionalWorkTime);
	}
}
