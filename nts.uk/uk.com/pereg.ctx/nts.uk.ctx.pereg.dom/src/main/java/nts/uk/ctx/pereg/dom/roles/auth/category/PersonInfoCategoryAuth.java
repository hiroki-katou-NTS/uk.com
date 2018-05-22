package nts.uk.ctx.pereg.dom.roles.auth.category;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.common.ConstantEnum;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoPermissionType;

@Getter
public class PersonInfoCategoryAuth extends AggregateRoot {

	private String roleId;

	private String personInfoCategoryAuthId;

	private PersonInfoPermissionType allowPersonRef;

	private PersonInfoPermissionType allowOtherRef;

	private PersonInfoPermissionType allowOtherCompanyRef;

	private PersonInfoAuthType selfPastHisAuth;

	private PersonInfoAuthType selfFutureHisAuth;

	private PersonInfoPermissionType selfAllowAddHis;

	private PersonInfoPermissionType selfAllowDelHis;

	private PersonInfoAuthType otherPastHisAuth;

	private PersonInfoAuthType otherFutureHisAuth;

	private PersonInfoPermissionType otherAllowAddHis;

	private PersonInfoPermissionType otherAllowDelHis;

	private PersonInfoPermissionType selfAllowAddMulti;

	private PersonInfoPermissionType selfAllowDelMulti;

	private PersonInfoPermissionType otherAllowAddMulti;

	private PersonInfoPermissionType otherAllowDelMulti;

	/**
	 * contructor
	 * 
	 * @param roleId
	 * @param personInfoCategoryAuthId
	 * @param allowPersonRef
	 * @param allowOtherRef
	 * @param allowOtherCompanytRef
	 * @param selfPastHisAuth
	 * @param selfFutureHisAuth
	 * @param selfAllowAddHis
	 * @param selfAllowDelHis
	 * @param otherPastHisAuth
	 * @param otherFutureHisAuth
	 * @param otherAllowAddHis
	 * @param otherAllowDelHis
	 * @param selfAllowAddMulti
	 * @param selfAllowDelMulti
	 * @param otherAllowAddMulti
	 * @param otherAllowDelMulti
	 */
	public PersonInfoCategoryAuth(String roleId, String personInfoCategoryAuthId,
			PersonInfoPermissionType allowPersonRef, PersonInfoPermissionType allowOtherRef,
			PersonInfoPermissionType allowOtherCompanytRef, PersonInfoAuthType selfPastHisAuth,
			PersonInfoAuthType selfFutureHisAuth, PersonInfoPermissionType selfAllowAddHis,
			PersonInfoPermissionType selfAllowDelHis, PersonInfoAuthType otherPastHisAuth,
			PersonInfoAuthType otherFutureHisAuth, PersonInfoPermissionType otherAllowAddHis,
			PersonInfoPermissionType otherAllowDelHis, PersonInfoPermissionType selfAllowAddMulti,
			PersonInfoPermissionType selfAllowDelMulti, PersonInfoPermissionType otherAllowAddMulti,
			PersonInfoPermissionType otherAllowDelMulti) {
		super();
		this.roleId = roleId;
		this.personInfoCategoryAuthId = personInfoCategoryAuthId;
		this.allowPersonRef = allowPersonRef;
		this.allowOtherRef = allowOtherRef;
		this.allowOtherCompanyRef = allowOtherCompanytRef;
		this.selfPastHisAuth = selfPastHisAuth;
		this.selfFutureHisAuth = selfFutureHisAuth;
		this.selfAllowAddHis = selfAllowAddHis;
		this.selfAllowDelHis = selfAllowDelHis;
		this.otherPastHisAuth = otherPastHisAuth;
		this.otherFutureHisAuth = otherFutureHisAuth;
		this.otherAllowAddHis = otherAllowAddHis;
		this.otherAllowDelHis = otherAllowDelHis;
		this.selfAllowAddMulti = selfAllowAddMulti;
		this.selfAllowDelMulti = selfAllowDelMulti;
		this.otherAllowAddMulti = otherAllowAddMulti;
		this.otherAllowDelMulti = otherAllowDelMulti;
	}

	public static PersonInfoCategoryAuth createFromJavaType(String roleId, String personInfoCategoryAuthId,
			int allowPersonRef, int allowOtherRef, int allowOtherCompanytRef, int selfPastHisAuth,
			int selfFutureHisAuth, int selfAllowAddHis, int selfAllowDelHis, int otherPastHisAuth,
			int otherFutureHisAuth, int otherAllowAddHis, int otherAllowDelHis, int selfAllowAddMulti,
			int selfAllowDelMulti, int otherAllowAddMulti, int otherAllowDelMulti) {
		return new PersonInfoCategoryAuth(roleId, personInfoCategoryAuthId,
				EnumAdaptor.valueOf(allowPersonRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowOtherRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowOtherCompanytRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(ConstantEnum.getEnumValue(selfPastHisAuth, PersonInfoAuthType.class),
						PersonInfoAuthType.class),
				EnumAdaptor.valueOf(ConstantEnum.getEnumValue(selfFutureHisAuth, PersonInfoAuthType.class),
						PersonInfoAuthType.class),
				EnumAdaptor.valueOf(selfAllowAddHis, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(selfAllowDelHis, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(ConstantEnum.getEnumValue(otherPastHisAuth, PersonInfoAuthType.class),
						PersonInfoAuthType.class),
				EnumAdaptor.valueOf(ConstantEnum.getEnumValue(otherFutureHisAuth, PersonInfoAuthType.class),
						PersonInfoAuthType.class),
				EnumAdaptor.valueOf(otherAllowAddHis, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(otherAllowDelHis, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(selfAllowAddMulti, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(selfAllowDelMulti, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(otherAllowAddMulti, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(otherAllowDelMulti, PersonInfoPermissionType.class));
	}

	public static PersonInfoCategoryAuth createFromDefaulValue(String roleId, String personInfoCategoryAuthId,
			int allowPersonRef, int allowOtherRef) {
		if (roleId.isEmpty() || personInfoCategoryAuthId.isEmpty()) {
			throw new BusinessException(new RawErrorMessage("明細書名が入力されていません。"));
		}
		return new PersonInfoCategoryAuth(roleId, personInfoCategoryAuthId,
				EnumAdaptor.valueOf(allowPersonRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowOtherRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(1, PersonInfoAuthType.class), EnumAdaptor.valueOf(1, PersonInfoAuthType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(1, PersonInfoAuthType.class), EnumAdaptor.valueOf(1, PersonInfoAuthType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class));
	}

	public void updateFromJavaType(int allowPersonRef, int allowOtherRef, int allowOtherCompanyRef, int selfPastHisAuth,
			int selfFutureHisAuth, int selfAllowAddHis, int selfAllowDelHis, int otherPastHisAuth,
			int otherFutureHisAuth, int otherAllowAddHis, int otherAllowDelHis, int selfAllowAddMulti,
			int selfAllowDelMulti, int otherAllowAddMulti, int otherAllowDelMulti) {

		this.allowPersonRef = EnumAdaptor.valueOf(allowPersonRef, PersonInfoPermissionType.class);
		this.allowOtherRef = EnumAdaptor.valueOf(allowOtherRef, PersonInfoPermissionType.class);
		this.allowOtherCompanyRef = EnumAdaptor.valueOf(allowOtherCompanyRef, PersonInfoPermissionType.class);
		this.selfPastHisAuth = EnumAdaptor.valueOf(selfPastHisAuth, PersonInfoAuthType.class);
		this.selfFutureHisAuth = EnumAdaptor.valueOf(selfFutureHisAuth, PersonInfoAuthType.class);
		this.selfAllowAddHis = EnumAdaptor.valueOf(selfAllowAddHis, PersonInfoPermissionType.class);
		this.selfAllowDelHis = EnumAdaptor.valueOf(selfAllowDelHis, PersonInfoPermissionType.class);
		this.otherPastHisAuth = EnumAdaptor.valueOf(otherPastHisAuth, PersonInfoAuthType.class);
		this.otherFutureHisAuth = EnumAdaptor.valueOf(otherFutureHisAuth, PersonInfoAuthType.class);
		this.otherAllowAddHis = EnumAdaptor.valueOf(otherAllowAddHis, PersonInfoPermissionType.class);
		this.otherAllowDelHis = EnumAdaptor.valueOf(otherAllowDelHis, PersonInfoPermissionType.class);
		this.selfAllowAddMulti = EnumAdaptor.valueOf(selfAllowAddMulti, PersonInfoPermissionType.class);
		this.selfAllowDelMulti = EnumAdaptor.valueOf(selfAllowDelMulti, PersonInfoPermissionType.class);
		this.otherAllowAddMulti = EnumAdaptor.valueOf(otherAllowAddMulti, PersonInfoPermissionType.class);
		this.otherAllowDelMulti = EnumAdaptor.valueOf(otherAllowDelMulti, PersonInfoPermissionType.class);

	}
	
	public void updateFromJavaType(int allowPersonRef, int allowOtherRef) {

		this.allowPersonRef = EnumAdaptor.valueOf(allowPersonRef, PersonInfoPermissionType.class);
		this.allowOtherRef = EnumAdaptor.valueOf(allowOtherRef, PersonInfoPermissionType.class);

	}
	
	public boolean allowPersonRef() {
		return allowPersonRef == PersonInfoPermissionType.YES;
	}
	
	public boolean allowOtherRef() {
		return allowOtherRef == PersonInfoPermissionType.YES;
	}

}
