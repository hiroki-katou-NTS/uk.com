package nts.uk.ctx.hr.shared.dom.dateTerm;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.hr.shared.dom.enumeration.DateSelectItem;
import nts.uk.ctx.hr.shared.dom.enumeration.DateRule;

/**
 * @author thanhpv
 * 日付算出条件
 */
@AllArgsConstructor
@Getter
public class DateCaculationTerm extends DomainObject{

	/** 算出条件 */
	private DateRule dateTerm;
	
	/** 指定数 */
	private Integer dateSettingNum;
	
	/** 指定日 */
	private Optional<DateSelectItem> dateSettingDate;
	
	public static DateCaculationTerm createFromJavaType(int dateTerm, Integer dateSettingNum, Integer dateSettingDate) {
		return new DateCaculationTerm(
				EnumAdaptor.valueOf(dateTerm, DateRule.class),
				dateSettingNum,
				dateSettingDate == null ? Optional.empty(): Optional.of(EnumAdaptor.valueOf(dateSettingDate, DateSelectItem.class))
				);
	}
}
