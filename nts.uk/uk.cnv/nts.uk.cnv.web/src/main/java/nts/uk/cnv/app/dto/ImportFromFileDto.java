package nts.uk.cnv.app.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class ImportFromFileDto {
	String path;
	String type;
	String branch;
	GeneralDate date;
}
