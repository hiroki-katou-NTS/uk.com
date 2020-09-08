package nts.uk.cnv.dom.conversiontable;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.pattern.ConversionPattern;

/**
 * １列分の変換表
 * @author ai_muto
 *
 *１列分の変換
 * 変換先の情報をINSERT句に追加する
 */
@AllArgsConstructor
@Getter
public class OneColumnConversion {
	private String targetColumn;
	private String conversionType;

	private ConversionPattern pattern;

	public ConversionSQL apply(ConversionSQL conversionSql) {
		conversionSql = this.pattern.apply(conversionSql);

		conversionSql.getInsert().addExpression(new ColumnExpression(Optional.empty(), targetColumn));

		return conversionSql;
	}
}
