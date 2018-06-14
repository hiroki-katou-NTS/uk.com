package nts.uk.ctx.sys.assist.dom.recoverystorage;

import java.util.List;

import lombok.Value;

@Value
public class DataRecoveryTable {
	
	List<List<String>> dataRecovery;
	String fileNameCsv;
	
}
