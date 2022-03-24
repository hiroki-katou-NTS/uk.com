package nts.uk.screen.at.app.dailyperformance.correction.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpAndDate {
	public String employeeId;
	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	public GeneralDate date;
}
