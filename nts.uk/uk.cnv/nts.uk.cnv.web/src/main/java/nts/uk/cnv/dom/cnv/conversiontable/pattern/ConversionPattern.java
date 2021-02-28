package nts.uk.cnv.dom.cnv.conversiontable.pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.cnv.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.cnv.service.ConversionInfo;

/**
 * 変換パターン（基底クラス）
 * @author ai_muto
 *
 */
@Getter
@AllArgsConstructor
public abstract class ConversionPattern {
	protected ConversionInfo info;
	public abstract ConversionSQL apply(ConversionSQL conversionSql);
}
