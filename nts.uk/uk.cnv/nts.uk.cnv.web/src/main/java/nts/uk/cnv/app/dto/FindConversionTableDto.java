package nts.uk.cnv.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@AllArgsConstructor
public class FindConversionTableDto {
	String category;
	String table;
	int recordNo;
	String ukColumn;
}
