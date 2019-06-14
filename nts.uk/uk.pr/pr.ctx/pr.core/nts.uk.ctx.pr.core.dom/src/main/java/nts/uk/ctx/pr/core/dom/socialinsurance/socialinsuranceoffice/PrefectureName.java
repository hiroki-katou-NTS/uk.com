package nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 都道府県名称
 */
@StringMaxLength(7)
public class PrefectureName extends StringPrimitiveValue<PrefectureName> {
    public PrefectureName(String rawValue) {
        super(rawValue);
    }
}
