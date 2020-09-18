package nts.uk.cnv.screen.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class Cnv001ALoadDataParam {
	String categoryName;
	String tableName;
}
