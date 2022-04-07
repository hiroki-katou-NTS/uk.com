package nts.uk.ctx.exio.dom.input.setting.assembly.mapping.fetch;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 外部受入の読み取り文字位位置
 */
@IntegerRange(min = 1, max = 999999)
public class ExternalImportFetchPosition extends IntegerPrimitiveValue<ExternalImportFetchPosition> {

    /**
     * Constructs.
     * @param rawValue raw value
     */
    public ExternalImportFetchPosition(Integer rawValue) {
        super(rawValue);
    }
}
