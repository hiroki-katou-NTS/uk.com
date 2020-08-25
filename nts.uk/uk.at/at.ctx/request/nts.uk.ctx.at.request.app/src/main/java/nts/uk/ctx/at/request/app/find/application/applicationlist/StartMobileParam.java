package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartMobileParam {
	private List<ListOfAppTypesDto> param;
	private AppListExtractConditionDto appListExtractConditionDto;
}
