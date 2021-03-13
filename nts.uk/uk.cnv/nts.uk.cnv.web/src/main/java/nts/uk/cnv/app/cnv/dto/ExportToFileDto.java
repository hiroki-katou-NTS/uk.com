package nts.uk.cnv.app.cnv.dto;

import lombok.Data;
import nts.arc.time.GeneralDateTime;

@Data
public class ExportToFileDto {
	String path;
	String type;
	boolean withComment;
	boolean oneFile;
	private String feature;

	public GeneralDateTime getDateTime() {
		return GeneralDateTime.now();
	}
}
