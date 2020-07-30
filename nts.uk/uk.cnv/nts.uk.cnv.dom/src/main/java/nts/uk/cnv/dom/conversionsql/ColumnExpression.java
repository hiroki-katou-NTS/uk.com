package nts.uk.cnv.dom.conversionsql;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 列の式
 * @author ai_muto
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public class ColumnExpression {
	private Optional<String> tableAlias;
	
	/***
	 * 列名 or 固定値 or 固定の関数
	 */
	private String expression;
	
	public String sql() {
		return
			tableAlias.isPresent()
				? tableAlias.get() + "." + expression
				: expression;
	}
}
