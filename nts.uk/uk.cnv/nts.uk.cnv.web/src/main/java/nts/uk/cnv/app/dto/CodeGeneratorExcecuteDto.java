package nts.uk.cnv.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CodeGeneratorExcecuteDto {
	String dbtype;
	String sourceDbName;
	String sourceSchema;
	String targetDbName;
	String targetSchema;
	String workDbName;
	String workSchema;
	String contractCode;

	String filePath;
}
