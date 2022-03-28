package nts.uk.screen.at.app.dailyperformance.correction.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class SidDateErrorCalcDto {
	
	private String sid;
	
    @JsonDeserialize(using = CustomGeneralDateSerializer.class)
    private GeneralDate date;
}
