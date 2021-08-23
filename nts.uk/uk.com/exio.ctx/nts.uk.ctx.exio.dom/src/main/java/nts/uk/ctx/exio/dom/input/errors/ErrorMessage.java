package nts.uk.ctx.exio.dom.input.errors;

import lombok.Value;

/**
 * エラーメッセージ単体で扱うときにString型だと何だか分からないので、分かるようにクラス作った
 */
@Value
public class ErrorMessage {

	private final String text;
	
	public static ErrorMessage of(Exception ex) {
		return new ErrorMessage(ex.getMessage());
	}
}
