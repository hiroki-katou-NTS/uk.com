package nts.uk.cnv.dom.conversiontable;

import java.util.Optional;

import lombok.Getter;
import nts.uk.cnv.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversiontable.pattern.ConversionPattern;
import nts.uk.cnv.dom.conversiontable.pattern.ReferencedParentPattern;

/**
 * １列分の変換表
 * @author ai_muto
 *
 *１列分の変換
 * 変換先の情報をINSERT句に追加する
 */
@Getter
public class OneColumnConversion {
	private String targetColumn;
	private String conversionType;
	private ConversionPattern pattern;

	private boolean isReferenced;
	private ReferencedParentPattern referencedPattern;

	public OneColumnConversion(String targetColumn, String conversionType, ConversionPattern pattern) {
		super();
		this.targetColumn = targetColumn;
		this.conversionType = conversionType;
		this.pattern = pattern;

		this.isReferenced = false;
	}

	public ConversionSQL apply(ConversionSQL conversionSql) {

		if (this.isReferenced) {
			conversionSql = this.referencedPattern.apply(conversionSql);
		}
		else {
			conversionSql = this.pattern.apply(conversionSql);
		}

		conversionSql.getInsert().addExpression(new ColumnExpression(Optional.empty(), targetColumn));

		return conversionSql;
	}

	public void setReferenced(boolean isReferenced, ReferencedParentPattern referencedPattern) {
		this.isReferenced = isReferenced;
		this.referencedPattern = referencedPattern;
	}
}
