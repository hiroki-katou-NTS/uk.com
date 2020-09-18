package nts.uk.cnv.app.command;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class DeleteConversionRecordCommand {
	String category;
	String table;
	int recordNo;
}
