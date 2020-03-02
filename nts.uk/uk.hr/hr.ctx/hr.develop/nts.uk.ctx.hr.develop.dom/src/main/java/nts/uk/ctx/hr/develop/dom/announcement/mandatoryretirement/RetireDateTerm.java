package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.RetireDateRule;
import nts.uk.ctx.hr.shared.dom.enumeration.DateSelectItem;

/**
 * @author thanhpv
 * 退職日条件
 */
@AllArgsConstructor
@Getter
public class RetireDateTerm extends DomainObject{

	/** 退職日条件 */
	private RetireDateRule retireDateTerm;
	
	/** 退職日指定日 */
	private Optional<DateSelectItem> retireDateSettingDate;
	
	public static RetireDateTerm createFromJavaType(int retireDateTerm, Integer retireDateSettingDate) {
		return new RetireDateTerm(
				EnumAdaptor.valueOf(retireDateTerm, RetireDateRule.class),
				retireDateSettingDate == null ? Optional.empty(): Optional.of(EnumAdaptor.valueOf(retireDateSettingDate, DateSelectItem.class))
				);
	}
}
