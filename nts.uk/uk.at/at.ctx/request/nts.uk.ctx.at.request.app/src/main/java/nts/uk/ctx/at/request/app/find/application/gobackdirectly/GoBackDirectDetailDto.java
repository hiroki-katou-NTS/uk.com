package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class GoBackDirectDetailDto {
	GoBackDirectlyDto goBackDirectlyDto;
	int prePostAtr;
	String workLocationName1;
	String workLocationName2;
	String workTypeName;
	String workTimeName;
}
