package nts.uk.ctx.pereg.app.find.roles.auth.category;

import lombok.Value;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;

@Value
public class PersonInfoCategoryAuthDto {
	private String roleId;

	private String personInfoCategoryAuthId;

	private int allowPersonRef;

	private int allowOtherRef;

	private int allowOtherCompanyRef;

	private int selfPastHisAuth;

	private int selfFutureHisAuth;

	private int selfAllowAddHis;

	private int selfAllowDelHis;

	private int otherPastHisAuth;

	private int otherFutureHisAuth;

	private int otherAllowAddHis;

	private int otherAllowDelHis;

	private int selfAllowAddMulti;

	private int selfAllowDelMulti;

	private int otherAllowAddMulti;

	private int otherAllowDelMulti;

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
