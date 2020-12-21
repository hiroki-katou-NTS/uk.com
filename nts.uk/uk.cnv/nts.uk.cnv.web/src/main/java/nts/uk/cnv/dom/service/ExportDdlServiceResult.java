package nts.uk.cnv.dom.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExportDdlServiceResult {
	String ddl;
	String branch;
	String date;
}
