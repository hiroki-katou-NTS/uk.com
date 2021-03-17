package nts.uk.cnv.app.cnv.dto;

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
	String contractCode;

	String filePath;
}
