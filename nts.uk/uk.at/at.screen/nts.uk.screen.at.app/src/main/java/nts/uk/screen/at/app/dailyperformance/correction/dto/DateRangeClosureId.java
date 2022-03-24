package nts.uk.screen.at.app.dailyperformance.correction.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DateRangeClosureId {
	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate startDate;
	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate endDate;
	private int closureId;
}
