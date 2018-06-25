package nts.uk.ctx.pereg.app.find.roles.auth.category;

import lombok.Value;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;

@Value
public class PersonInfoCategoryAuthDto {
	private String roleId;

	private String personInfoCategoryAuthId;

	private int allowPersonRef;

	private int allowOtherRef;

	private Integer selfPastHisAuth;

	private Integer selfFutureHisAuth;

	private Integer selfAllowAddHis;

	private Integer selfAllowDelHis;

	private Integer otherPastHisAuth;

	private Integer otherFutureHisAuth;

	private Integer otherAllowAddHis;

	private Integer otherAllowDelHis;

	private Integer selfAllowAddMulti;

	private Integer selfAllowDelMulti;

	private Integer otherAllowAddMulti;

	private Integer otherAllowDelMulti;

	public static PersonInfoCategoryAuthDto fromDomain(PersonInfoCategoryAuth domain) {
		return new PersonInfoCategoryAuthDto(domain.getRoleId(), domain.getPersonInfoCategoryAuthId(),
				domain.getAllowPersonRef().value, domain.getAllowOtherRef().value, domain.getSelfPastHisAuth() == null? null : domain.getSelfPastHisAuth().value,
				domain.getSelfFutureHisAuth() == null? null : domain.getSelfFutureHisAuth().value, domain.getSelfAllowAddHis() == null? null: domain.getSelfAllowAddHis().value,
				domain.getSelfAllowDelHis() == null? null: domain.getSelfAllowDelHis().value, domain.getOtherPastHisAuth() == null? null: domain.getOtherPastHisAuth().value,
				domain.getOtherFutureHisAuth() == null? null:domain.getOtherFutureHisAuth().value,  domain.getOtherAllowAddHis() == null? null:domain.getOtherAllowAddHis().value,
				domain.getOtherAllowDelHis() == null? null: domain.getOtherAllowDelHis().value, domain.getSelfAllowAddMulti() == null? null: domain.getSelfAllowAddMulti().value,
				domain.getSelfAllowDelMulti() == null? null: domain.getSelfAllowDelMulti().value, domain.getOtherAllowAddMulti() == null? null: domain.getOtherAllowAddMulti().value,
				domain.getOtherAllowDelMulti() == null? null: domain.getOtherAllowDelMulti().value);

	}

}
