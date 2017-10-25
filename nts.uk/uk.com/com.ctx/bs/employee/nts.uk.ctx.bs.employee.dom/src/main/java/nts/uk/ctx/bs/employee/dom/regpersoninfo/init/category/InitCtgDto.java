package nts.uk.ctx.bs.employee.dom.regpersoninfo.init.category;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.InitValSettingCtg;


/**
 * @author sonnlb
 *
 */
@Value
public class InitCtgDto {

	private String categoryCd;
	private String categoryName;

	public static InitCtgDto fromDomain(InitValSettingCtg domain) {
		return new InitCtgDto(domain.getCategoryCd(), domain.getCategoryName());
	}

}
