package nts.uk.file.com.app.person.matrix.datasource;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorWarningInfoOfRowOrderDataSource{
	private String itemName;
	private int errorType;
	private String message;
}
