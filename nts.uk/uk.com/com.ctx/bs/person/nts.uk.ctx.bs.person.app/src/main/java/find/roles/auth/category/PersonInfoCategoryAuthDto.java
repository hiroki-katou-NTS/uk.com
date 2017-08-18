package find.roles.auth.category;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuth;

@Value
public class PersonInfoCategoryAuthDto {
	String roleId;

	String personInfoCategoryAuthId;

	int allowPersonRef;

	int allowOtherRef;

	int allowOtherCompanyRef;

	int selfPastHisAuth;

	int selfFutureHisAuth;

	int selfAllowAddHis;

	int selfAllowDelHis;

	int otherPastHisAuth;

	int otherFutureHisAuth;

	int otherAllowAddHis;

	int otherAllowDelHis;

	int selfAllowAddMulti;

	int selfAllowDelMulti;

	int otherAllowAddMulti;

	int otherAllowDelMulti;

	public static PersonInfoCategoryAuthDto fromDomain(PersonInfoCategoryAuth domain) {
		return new PersonInfoCategoryAuthDto(domain.getRoleId(), domain.getPersonInfoCategoryAuthId(),
				domain.getAllowPersonRef().value, domain.getAllowOtherRef().value,
				domain.getAllowOtherCompanyRef().value, domain.getSelfPastHisAuth().value,
				domain.getSelfFutureHisAuth().value, domain.getSelfAllowAddHis().value,
				domain.getSelfAllowDelHis().value, domain.getOtherPastHisAuth().value,
				domain.getOtherFutureHisAuth().value, domain.getOtherAllowAddHis().value,
				domain.getOtherAllowDelHis().value, domain.getSelfAllowAddMulti().value,
				domain.getSelfAllowDelMulti().value, domain.getOtherAllowAddMulti().value,
				domain.getOtherAllowDelMulti().value);

	}

}
