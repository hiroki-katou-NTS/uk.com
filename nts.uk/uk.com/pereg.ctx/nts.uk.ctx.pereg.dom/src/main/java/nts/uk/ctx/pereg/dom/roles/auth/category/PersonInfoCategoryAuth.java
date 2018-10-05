package nts.uk.ctx.pereg.dom.roles.auth.category;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.common.ConstantEnum;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoPermissionType;

/**
 * 個人情報カテゴリ権限
 * 
 * @author lanlt
 *
 */
@Getter
@Setter
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

	// multi type - 複数情報権限
	public PersonInfoCategoryAuth(String roleId, String categoryId,
			PersonInfoPermissionType allowPersonRef, PersonInfoPermissionType allowOtherRef,
			PersonInfoPermissionType allowOtherCompanytRef, PersonInfoPermissionType selfAllowAddMulti,
			PersonInfoPermissionType selfAllowDelMulti, PersonInfoPermissionType otherAllowAddMulti,
			PersonInfoPermissionType otherAllowDelMulti) {
		super();
		this.roleId = roleId;
		this.personInfoCategoryAuthId = categoryId;
		this.allowPersonRef = allowPersonRef;
		this.allowOtherRef = allowOtherRef;
		this.allowOtherCompanyRef = allowOtherCompanytRef;
		this.selfAllowAddMulti = selfAllowAddMulti;
		this.selfAllowDelMulti = selfAllowDelMulti;
		this.otherAllowAddMulti = otherAllowAddMulti;
		this.otherAllowDelMulti = otherAllowDelMulti;
	}

	// single info type - 単一情報
	public PersonInfoCategoryAuth(String roleId, String personInfoCategoryAuthId,
			PersonInfoPermissionType allowPersonRef, PersonInfoPermissionType allowOtherRef, PersonInfoPermissionType allowOtherCompanytRef) {

		this.roleId = roleId;
		this.personInfoCategoryAuthId = personInfoCategoryAuthId;
		this.allowPersonRef = allowPersonRef;
		this.allowOtherRef = allowOtherRef;
		this.allowOtherCompanyRef = allowOtherCompanytRef;
	}

	// history type -履歴情報
	public PersonInfoCategoryAuth(String roleId, String personInfoCategoryAuthId,
			PersonInfoPermissionType allowPersonRef, PersonInfoPermissionType allowOtherRef, PersonInfoPermissionType allowOtherCompanytRef, PersonInfoAuthType selfPastHisAuth,
			PersonInfoAuthType selfFutureHisAuth, PersonInfoPermissionType selfAllowAddHis,
			PersonInfoPermissionType selfAllowDelHis, PersonInfoAuthType otherPastHisAuth,
			PersonInfoAuthType otherFutureHisAuth, PersonInfoPermissionType otherAllowAddHis,
			PersonInfoPermissionType otherAllowDelHis) {
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
	}
	
	public static PersonInfoCategoryAuth createFromJavaType(String roleId, String personInfoCategoryAuthId,
			int allowPersonRef, int allowOtherRef, int allowOtherCompanytRef, Integer selfPastHisAuth,
			Integer selfFutureHisAuth, Integer selfAllowAddHis, Integer selfAllowDelHis, Integer otherPastHisAuth,
			Integer otherFutureHisAuth, Integer otherAllowAddHis, Integer otherAllowDelHis, Integer selfAllowAddMulti,
			Integer selfAllowDelMulti, Integer otherAllowAddMulti, Integer otherAllowDelMulti) {
		PersonInfoCategoryAuth ctg = new PersonInfoCategoryAuth();
		ctg.setRoleId(roleId);
		// single & muti
		ctg.setPersonInfoCategoryAuthId(personInfoCategoryAuthId);
		ctg.setAllowPersonRef(EnumAdaptor.valueOf(allowPersonRef, PersonInfoPermissionType.class));
		ctg.setAllowOtherRef(EnumAdaptor.valueOf(allowOtherRef, PersonInfoPermissionType.class));
		ctg.setAllowOtherCompanyRef(EnumAdaptor.valueOf(allowOtherCompanytRef, PersonInfoPermissionType.class));
		
		//lich su
		if(selfPastHisAuth != null) {
			ctg.setSelfPastHisAuth(EnumAdaptor.valueOf(ConstantEnum.getEnumValue(selfPastHisAuth.intValue(), PersonInfoAuthType.class),
						PersonInfoAuthType.class));
		}
		if(selfFutureHisAuth != null) {
			ctg.setSelfFutureHisAuth(EnumAdaptor.valueOf(ConstantEnum.getEnumValue(selfFutureHisAuth.intValue(), PersonInfoAuthType.class),
						PersonInfoAuthType.class));
		}
		if(selfAllowAddHis != null) {
			ctg.setSelfAllowAddHis(EnumAdaptor.valueOf(selfAllowAddHis, PersonInfoPermissionType.class));
		}
		if(selfAllowDelHis != null) {
			ctg.setSelfAllowDelHis(EnumAdaptor.valueOf(selfAllowDelHis, PersonInfoPermissionType.class));
		}
		if(otherPastHisAuth != null) {
			ctg.setOtherPastHisAuth(EnumAdaptor.valueOf(ConstantEnum.getEnumValue(otherPastHisAuth, PersonInfoAuthType.class),
						PersonInfoAuthType.class));
		}
		if(otherFutureHisAuth != null) {
			ctg.setOtherFutureHisAuth(EnumAdaptor.valueOf(ConstantEnum.getEnumValue(otherFutureHisAuth, PersonInfoAuthType.class),
						PersonInfoAuthType.class));
		}
		if(otherAllowAddHis != null) {
			ctg.setOtherAllowAddHis(EnumAdaptor.valueOf(otherAllowAddHis, PersonInfoPermissionType.class));
		}
		
		//multi
		if(otherAllowDelHis != null) {
			ctg.setOtherAllowDelHis(EnumAdaptor.valueOf(otherAllowDelHis, PersonInfoPermissionType.class));
		}
		
		if(selfAllowAddMulti != null) {
			ctg.setSelfAllowAddMulti(EnumAdaptor.valueOf(selfAllowAddMulti, PersonInfoPermissionType.class));
		}
		
		if(selfAllowDelMulti != null) {
			ctg.setSelfAllowDelMulti(EnumAdaptor.valueOf(selfAllowDelMulti, PersonInfoPermissionType.class));
		}
		
		if(otherAllowAddMulti != null) {
			ctg.setOtherAllowAddMulti(EnumAdaptor.valueOf(otherAllowAddMulti, PersonInfoPermissionType.class));
		}
		
		if(otherAllowDelMulti != null) {
			ctg.setOtherAllowDelMulti(EnumAdaptor.valueOf(otherAllowDelMulti, PersonInfoPermissionType.class));
		}
		return ctg;
	}

	public static PersonInfoCategoryAuth createFromJavaType(int categoryType, String roleId, String categoryId,
			int allowPersonRef, int allowOtherRef, int allowOtherCompanytRef, int selfPastHisAuth,
			int selfFutureHisAuth, int selfAllowAddHis, int selfAllowDelHis, int otherPastHisAuth,
			int otherFutureHisAuth, int otherAllowAddHis, int otherAllowDelHis, int selfAllowAddMulti,
			int selfAllowDelMulti, int otherAllowAddMulti, int otherAllowDelMulti) {
		
		CategoryType type = EnumAdaptor.valueOf(categoryType , CategoryType.class);
		switch (type) {
		case SINGLEINFO:
			return PersonInfoCategoryAuth.createSingleFromJavaType(roleId, categoryId,
					allowPersonRef, allowOtherRef, allowOtherCompanytRef);
		case MULTIINFO:
			return  PersonInfoCategoryAuth.createMultiFromJavaType(roleId, categoryId,
					allowPersonRef, allowOtherRef, allowOtherCompanytRef,
					selfAllowAddMulti, selfAllowDelMulti, otherAllowAddMulti,
					otherAllowDelMulti);
		case CONTINUOUSHISTORY:
		case NODUPLICATEHISTORY:
		case DUPLICATEHISTORY:
			return PersonInfoCategoryAuth.createHisFromJavaType(roleId, categoryId,
					allowPersonRef, allowOtherRef, allowOtherCompanytRef,
					selfPastHisAuth, selfFutureHisAuth,
					selfAllowAddHis, selfAllowDelHis, otherPastHisAuth,
					otherFutureHisAuth, otherAllowAddHis, otherAllowDelHis);
		default:
			return null;

		}
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

	public void updateFromJavaType(int ctgType, int allowPersonRef, int allowOtherRef, int allowOtherCompanyRef, int selfPastHisAuth,
			int selfFutureHisAuth, int selfAllowAddHis, int selfAllowDelHis, int otherPastHisAuth,
			int otherFutureHisAuth, int otherAllowAddHis, int otherAllowDelHis, int selfAllowAddMulti,
			int selfAllowDelMulti, int otherAllowAddMulti, int otherAllowDelMulti) {
		this.allowPersonRef = EnumAdaptor.valueOf(allowPersonRef, PersonInfoPermissionType.class);
		this.allowOtherRef = EnumAdaptor.valueOf(allowOtherRef, PersonInfoPermissionType.class);
    	this.allowOtherCompanyRef = EnumAdaptor.valueOf(allowOtherCompanyRef, PersonInfoPermissionType.class);
    	// save data theo từng trường hợp của category type
        switch(EnumAdaptor.valueOf(ctgType, CategoryType.class)) {
        	case MULTIINFO:
	     		this.selfAllowAddMulti = EnumAdaptor.valueOf(selfAllowAddMulti, PersonInfoPermissionType.class);
	    		this.selfAllowDelMulti = EnumAdaptor.valueOf(selfAllowDelMulti, PersonInfoPermissionType.class);
	    		this.otherAllowAddMulti = EnumAdaptor.valueOf(otherAllowAddMulti, PersonInfoPermissionType.class);
	    		this.otherAllowDelMulti = EnumAdaptor.valueOf(otherAllowDelMulti, PersonInfoPermissionType.class);
	    		break;
	    		
    		case CONTINUOUSHISTORY:
    			
    		case DUPLICATEHISTORY:
    			
    		case NODUPLICATEHISTORY:
	    		this.selfPastHisAuth = EnumAdaptor.valueOf(selfPastHisAuth, PersonInfoAuthType.class);
	    		this.selfFutureHisAuth = EnumAdaptor.valueOf(selfFutureHisAuth, PersonInfoAuthType.class);
	    		this.selfAllowAddHis = EnumAdaptor.valueOf(selfAllowAddHis, PersonInfoPermissionType.class);
	    		this.selfAllowDelHis = EnumAdaptor.valueOf(selfAllowDelHis, PersonInfoPermissionType.class);
	    		this.otherPastHisAuth = EnumAdaptor.valueOf(otherPastHisAuth, PersonInfoAuthType.class);
	    		this.otherFutureHisAuth = EnumAdaptor.valueOf(otherFutureHisAuth, PersonInfoAuthType.class);
	    		this.otherAllowAddHis = EnumAdaptor.valueOf(otherAllowAddHis, PersonInfoPermissionType.class);
	    		this.otherAllowDelHis = EnumAdaptor.valueOf(otherAllowDelHis, PersonInfoPermissionType.class);
	        	break;
	        	
    		case SINGLEINFO:
        	 break;
        	 
    		case CONTINUOUS_HISTORY_FOR_ENDDATE:
				this.selfPastHisAuth = EnumAdaptor.valueOf(selfPastHisAuth, PersonInfoAuthType.class);
				this.selfFutureHisAuth = EnumAdaptor.valueOf(selfFutureHisAuth, PersonInfoAuthType.class);
				this.selfAllowAddHis = EnumAdaptor.valueOf(selfAllowAddHis, PersonInfoPermissionType.class);
				this.selfAllowDelHis = EnumAdaptor.valueOf(selfAllowDelHis, PersonInfoPermissionType.class);
				this.otherPastHisAuth = EnumAdaptor.valueOf(otherPastHisAuth, PersonInfoAuthType.class);
				this.otherFutureHisAuth = EnumAdaptor.valueOf(otherFutureHisAuth, PersonInfoAuthType.class);
				this.otherAllowAddHis = EnumAdaptor.valueOf(otherAllowAddHis, PersonInfoPermissionType.class);
				this.otherAllowDelHis = EnumAdaptor.valueOf(otherAllowDelHis, PersonInfoPermissionType.class);
				break;
		default:
			break;
        }
	}

	// multi type - 複数情報権限
	public static PersonInfoCategoryAuth createMultiFromJavaType(String roleId, String personInfoCategoryAuthId,
			int allowPersonRef, int allowOtherRef, int allowOtherCompanytRef, int selfAllowAddMulti,
			int selfAllowDelMulti, int otherAllowAddMulti, int otherAllowDelMulti) {
		return new PersonInfoCategoryAuth(roleId, personInfoCategoryAuthId,
				EnumAdaptor.valueOf(allowPersonRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowOtherRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowOtherCompanytRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(selfAllowAddMulti, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(selfAllowDelMulti, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(otherAllowAddMulti, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(otherAllowDelMulti, PersonInfoPermissionType.class));
	}

	// single info type - 単一情報
	public static PersonInfoCategoryAuth createSingleFromJavaType(String roleId, String ctgAuthId, int allowPersonRef,
			int allowOtherRef, int allowOtherCompanytRef) {
		return new PersonInfoCategoryAuth(roleId, ctgAuthId,
				EnumAdaptor.valueOf(allowPersonRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowOtherRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowOtherCompanytRef, PersonInfoPermissionType.class));
	}

	// history type -履歴情報
	public static PersonInfoCategoryAuth createHisFromJavaType(String roleId, String ctgAuthId, int allowPersonRef,
			int allowOtherRef, int allowOtherCompanytRef, int selfPastHisAuth, int selfFutureHisAuth,
			int selfAllowAddHis, int selfAllowDelHis, int otherPastHisAuth, int otherFutureHisAuth,
			int otherAllowAddHis, int otherAllowDelHis) {
		return new PersonInfoCategoryAuth(roleId, ctgAuthId,
				EnumAdaptor.valueOf(allowPersonRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowOtherRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowOtherCompanytRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(selfPastHisAuth, PersonInfoAuthType.class),
				EnumAdaptor.valueOf(selfFutureHisAuth, PersonInfoAuthType.class),
				EnumAdaptor.valueOf(selfAllowAddHis, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(selfAllowDelHis, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(otherPastHisAuth, PersonInfoAuthType.class),
				EnumAdaptor.valueOf(otherFutureHisAuth, PersonInfoAuthType.class),
				EnumAdaptor.valueOf(otherAllowAddHis, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(otherAllowDelHis, PersonInfoPermissionType.class));
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

	public PersonInfoCategoryAuth() {
		super();
	}

}
