package nts.uk.ctx.bs.person.dom.person.role.auth.category;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.role.auth.PersonInfoPermissionType;

public class PersonInfoCategoryAuth extends AggregateRoot {
	@Getter
	private String roleId;
	@Getter
	private String personInfoCategoryAuthId;
	@Getter
	private PersonInfoPermissionType allowPersonRef;
	@Getter
	private PersonInfoPermissionType  allowOtherRef;
	@Getter
	private PersonInfoPermissionType  allowOtherCompanyRef;
	@Getter
	private PersonInfoAuthType selfPastHisAuth;
	@Getter
	private PersonInfoAuthType selfFutureHisAuth;
	@Getter
	private PersonInfoPermissionType selfAllowAddHis;
	@Getter
	private PersonInfoPermissionType selfAllowDelHis;
	@Getter
	private PersonInfoAuthType otherPastHisAuth;
	@Getter
	private PersonInfoAuthType otherFutureHisAuth;
	@Getter
	private PersonInfoPermissionType otherAllowAddHis;
	@Getter
	private PersonInfoPermissionType otherAllowDelHis;
	@Getter
	private PersonInfoPermissionType selfAllowAddMulti;
	@Getter
	private PersonInfoPermissionType selfAllowDelMulti;
	@Getter
	private PersonInfoPermissionType otherAllowAddMulti;
	@Getter
	private PersonInfoPermissionType otherAllowDelMulti;
	
	/**
	 * contructor
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
			PersonInfoPermissionType allowOtherCompanytRef,
			PersonInfoAuthType selfPastHisAuth,	PersonInfoAuthType selfFutureHisAuth,
			PersonInfoPermissionType selfAllowAddHis,PersonInfoPermissionType selfAllowDelHis,
			PersonInfoAuthType otherPastHisAuth,PersonInfoAuthType otherFutureHisAuth,
			PersonInfoPermissionType otherAllowAddHis,PersonInfoPermissionType otherAllowDelHis,
			PersonInfoPermissionType selfAllowAddMulti,PersonInfoPermissionType selfAllowDelMulti,
			PersonInfoPermissionType otherAllowAddMulti,PersonInfoPermissionType otherAllowDelMulti) {
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
			int allowPersonRef, int allowOtherRef,int allowOtherCompanytRef,
			int selfPastHisAuth,int selfFutureHisAuth,
			int selfAllowAddHis,int selfAllowDelHis,
			int otherPastHisAuth,int otherFutureHisAuth,
			int otherAllowAddHis,int otherAllowDelHis, 
			int selfAllowAddMulti,int selfAllowDelMulti, 
			int otherAllowAddMulti,int otherAllowDelMulti ){
		if(roleId.isEmpty() || personInfoCategoryAuthId.isEmpty()){
			throw new BusinessException(new RawErrorMessage("明細書名が入力されていません。"));
		}
		return new PersonInfoCategoryAuth(roleId, personInfoCategoryAuthId,
				EnumAdaptor.valueOf(allowPersonRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowOtherRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowOtherCompanytRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(selfPastHisAuth,PersonInfoAuthType.class ),
				EnumAdaptor.valueOf(selfFutureHisAuth, PersonInfoAuthType.class),
				EnumAdaptor.valueOf(selfAllowAddHis, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(selfAllowDelHis, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(otherPastHisAuth,PersonInfoAuthType.class ),
				EnumAdaptor.valueOf(otherFutureHisAuth, PersonInfoAuthType.class),
				EnumAdaptor.valueOf(otherAllowAddHis, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(otherAllowDelHis, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(selfAllowAddMulti, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(selfAllowDelMulti, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(otherAllowAddMulti, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(otherAllowDelMulti, PersonInfoPermissionType.class));
	}
	
	
	public static PersonInfoCategoryAuth createFromDefaulValue(String roleId, String personInfoCategoryAuthId,
			int allowPersonRef, int allowOtherRef ){
		if(roleId.isEmpty() || personInfoCategoryAuthId.isEmpty()){
			throw new BusinessException(new RawErrorMessage("明細書名が入力されていません。"));
		}
		return new PersonInfoCategoryAuth(roleId, personInfoCategoryAuthId,
				EnumAdaptor.valueOf(allowPersonRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowOtherRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(1,PersonInfoAuthType.class ),
				EnumAdaptor.valueOf(1, PersonInfoAuthType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(1,PersonInfoAuthType.class ),
				EnumAdaptor.valueOf(1, PersonInfoAuthType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class));
	}


}
