package nts.uk.cnv.dom.conversionsql;

import lombok.Getter;

/**
 * 関係演算子
 * @author ai_muto
 */
public enum RelationalOperator {
	Equal("="),
	NotEqual("<>"),
	GreaterThan(">"),
	LessThan("<"),
	GreagerThanOrEqual(">="),
	LessThanOrEqual("<="),
	IsNull("IS NULL"),
	IsNotNull("IS NOT NULL");
	
	@Getter
	private final String sign;
	
	private RelationalOperator( final String sign) {
		this.sign = sign;
	}
}
