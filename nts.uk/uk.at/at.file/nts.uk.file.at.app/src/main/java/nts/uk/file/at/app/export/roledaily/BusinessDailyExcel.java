package nts.uk.file.at.app.export.roledaily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class BusinessDailyExcel {
	String code;
	int attItemId;
	int sheetNo;
	String sheetName;
}
