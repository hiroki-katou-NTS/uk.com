package nts.uk.ctx.exio.app.find.exi.csvimport;

import lombok.Value;

@Value
public class GettingCsvDataDto {
	private String fileId;

	private int[] columns;

	private int index;
	
	private int endCoding; 
}
