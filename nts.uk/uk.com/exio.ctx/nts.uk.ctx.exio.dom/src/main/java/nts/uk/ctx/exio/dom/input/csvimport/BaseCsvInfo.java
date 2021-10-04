package nts.uk.ctx.exio.dom.input.csvimport;

import java.util.List;

import lombok.Value;

@Value
public class BaseCsvInfo {
	private String csvFileId;
	
	private List<String> columns;
	
}
