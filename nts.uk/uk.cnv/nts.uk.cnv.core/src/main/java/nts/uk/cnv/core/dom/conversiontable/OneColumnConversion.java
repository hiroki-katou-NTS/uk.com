package nts.uk.cnv.core.dom.conversiontable;

import lombok.Getter;
import lombok.val;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversiontable.pattern.ConversionPattern;
import nts.uk.cnv.core.dom.conversiontable.pattern.ReferencedParentPattern;

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

	public ConversionSQL apply(String targetTableAlias, ConversionSQL conversionSql, boolean removeDuplicate) {

		val column = new ColumnName(targetTableAlias, this.targetColumn);
		if (this.isReferenced) {
			conversionSql = this.referencedPattern.apply(column, conversionSql, removeDuplicate);
		}
		else {
			conversionSql = this.pattern.apply(column, conversionSql, removeDuplicate);
		}

		return conversionSql;
	}

	public void setReferenced(boolean isReferenced, ReferencedParentPattern referencedPattern) {
		this.isReferenced = isReferenced;
		this.referencedPattern = referencedPattern;
	}
}
