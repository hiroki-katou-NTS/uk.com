package nts.uk.ctx.exio.dom.input.validation;

import lombok.val;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.gul.text.KatakanaConverter;
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
        
        val charTypeAnno = pvClass.getAnnotation(StringCharType.class);
        if (charTypeAnno != null) {
        	val charType = charTypeAnno.value();
        	if (charType == CharType.KANA || charType == CharType.KATAKANA) {
        		return kana(item);
        	}
        }

        return item;
    }

    private static DataItem padZero(Class<?> pvClass, DataItem item) {

        val length = pvClass.getAnnotation(StringMaxLength.class);
        String padded = StringUtils.leftPad(item.getString(), length.value(), '0');
        return DataItem.of(item.getItemNo(), padded);
    }
    
    private static DataItem kana(DataItem item) {
    	
    	String source = item.getString();
    	String corrected = KatakanaConverter.halfToFull(source);
    	return DataItem.of(item.getItemNo(), corrected);
    }

    public interface Require extends CorrectEmployeeCode.EmployeeCodeValidateRequire{

        ImportableItem getImportableItem(ImportingDomainId domainId, int itemNo);
    }
}
