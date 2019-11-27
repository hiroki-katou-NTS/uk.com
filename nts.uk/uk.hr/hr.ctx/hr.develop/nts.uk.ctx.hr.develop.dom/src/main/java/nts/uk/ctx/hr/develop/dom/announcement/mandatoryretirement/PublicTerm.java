package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.DateSelectItem;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.PublicDateRule;

/**
 * @author thanhpv
 * 公開条件
 */
@AllArgsConstructor
@Getter
public class PublicTerm extends DomainObject{

	/** 公開日条件 */
	private PublicDateRule publicDateTerm;
	
	/** 公開日指定数 */
	private Integer publicDateSettingNum;
	
	/** 公開日指定日 */
	private Optional<DateSelectItem> publicDateSettingDate;
	
	public static PublicTerm createFromJavaType(int publicDateTerm, Integer publicDateSettingNum, Integer publicDateSettingDate) {
		return new PublicTerm(
				EnumAdaptor.valueOf(publicDateTerm, PublicDateRule.class),
				publicDateSettingNum,
				publicDateSettingDate == null ? Optional.empty(): Optional.of(EnumAdaptor.valueOf(publicDateSettingDate, DateSelectItem.class))
				);
	}
}
