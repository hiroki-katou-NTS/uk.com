package nts.uk.query.pub.exo.condset;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AutoExecutionResultExport {

	/**
	 * エラーの有無
	 */
	private boolean hasError;
	
	/**
	 * エラーメッセージ
	 */
	private String errorMessage;
}
