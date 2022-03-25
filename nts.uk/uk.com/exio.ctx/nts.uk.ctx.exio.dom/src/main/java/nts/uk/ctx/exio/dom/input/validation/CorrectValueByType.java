package nts.uk.ctx.exio.dom.input.validation;

import lombok.val;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.CheckMethod;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.validation.system.CorrectEmployeeCode;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
import org.apache.commons.lang3.StringUtils;

/**
 * 型により値を補正する
 */
public class CorrectValueByType {

    public static DataItem correct(Require require, ExecutionContext context, DataItem item) {

        item = CorrectEmployeeCode.correct(require, context, item);

        item = correct(require.getImportableItem(context.getDomainId(), item.getItemNo()), item);

        return item;
    }

    private static DataItem correct(ImportableItem importableItem, DataItem item) {

        return importableItem.getDomainConstraint()
                .filter(dc -> dc.getCheckMethod() == CheckMethod.PRIMITIVE_VALUE)
                .map(dc -> correct(dc.getConstraintClass(), item))
                .orElse(item);
    }

    private static DataItem correct(Class<?> pvClass, DataItem item) {

        val zeroPadded = pvClass.getAnnotation(ZeroPaddedCode.class);
        if (zeroPadded != null) {
            return padZero(pvClass, item);
        }

        return item;
    }

    private static DataItem padZero(Class<?> pvClass, DataItem item) {

        val length = pvClass.getAnnotation(StringMaxLength.class);
        String padded = StringUtils.leftPad(item.getString(), length.value(), '0');
        return DataItem.of(item.getItemNo(), padded);
    }

    public interface Require extends CorrectEmployeeCode.EmployeeCodeValidateRequire{

        ImportableItem getImportableItem(ImportingDomainId domainId, int itemNo);
    }
}
