package nts.uk.cnv.core.dom.conversionsql;

import java.util.Optional;

import lombok.Getter;

/**
 * 列の式
 * @author ai_muto
 *
 */
@Getter
public class ColumnExpression {
	private Optional<String> tableAlias;

	/***
	 * 列名 or 固定値 or 固定の関数
	 */
	private String expression;

	public ColumnExpression(String alias, String expression) {
		this.tableAlias = alias.isEmpty() ? Optional.empty() : Optional.of(alias);
		this.expression = expression;
	}

	public ColumnExpression(String expression) {
		this.tableAlias = Optional.empty();
		this.expression = expression;
	}
	
	public static ColumnExpression stringLiteral(String value) {
		return new ColumnExpression("'" + value + "'");
	}

	public String sql() {
		return
			tableAlias.isPresent()
				? tableAlias.get() + "." + expression
				: expression;
	}
}
