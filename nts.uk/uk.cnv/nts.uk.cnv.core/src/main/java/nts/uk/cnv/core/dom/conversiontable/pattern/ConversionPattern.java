package nts.uk.cnv.core.dom.conversiontable.pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;

/**
 * 変換パターン（基底クラス）
 * @author ai_muto
 *
 */
@Getter
@AllArgsConstructor
public abstract class ConversionPattern {
	public abstract ConversionSQL apply(ColumnName columnName, ConversionSQL conversionSql, boolean removeDuplicate);
}
