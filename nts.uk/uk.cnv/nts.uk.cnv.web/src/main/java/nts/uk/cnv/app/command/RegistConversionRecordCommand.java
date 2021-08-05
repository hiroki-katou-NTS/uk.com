package nts.uk.cnv.app.command;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class RegistConversionRecordCommand {

	String category;
	String table;
	int recordNo;
	String sourceId;
	String explanation;
	boolean removeDuplicate;
}
