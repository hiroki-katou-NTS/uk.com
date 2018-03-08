package nts.uk.ctx.exio.app.command.exi.csvimport;

import lombok.Data;

@Data
public class CsvImportDataCommand {
	private String test;
	private int csvLine;
	private int currentLine;
	private int errorCount;
	private int stateBehavior;
}
