package nts.uk.ctx.exio.app.command.exi.csvimport;

import lombok.Data;

@Data
public class CsvImportDataCommand {
	private String processId;
	private int csvLine;
	private int currentLine;
	private int errorCount;
	private String stopMode;
	private String stateBehavior;
}
