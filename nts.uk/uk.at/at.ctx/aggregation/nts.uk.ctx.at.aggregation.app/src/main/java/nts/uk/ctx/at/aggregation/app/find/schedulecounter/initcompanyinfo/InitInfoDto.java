package nts.uk.ctx.at.aggregation.app.find.schedulecounter.initcompanyinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitInfoDto {
	
	private EsimatedInfoDto esimatedInfoDto;
	
	private Boolean useSetting;
}
