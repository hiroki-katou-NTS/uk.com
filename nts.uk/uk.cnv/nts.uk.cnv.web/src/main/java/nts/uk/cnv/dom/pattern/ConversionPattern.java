package nts.uk.cnv.dom.pattern;

import lombok.AllArgsConstructor;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.service.ConversionInfo;

/**
 * 変換パターン（基底クラス）
 * @author ai_muto
 *
 */
@AllArgsConstructor
public abstract class ConversionPattern {
	protected ConversionInfo info;
	public abstract ConversionSQL apply(ConversionSQL conversionSql);
}
