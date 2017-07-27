package nts.uk.ctx.bs.person.dom.person.role.auth.item;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoAuthType;

public class PersonInfoItemAuth extends AggregateRoot{
	@Getter
	private String roleId;
	@Getter
	private String personCategoryAuthId;
	@Getter
	private String personItemDefId;
	@Getter
	private PersonInfoAuthType selfAuth;
	@Getter
	private PersonInfoAuthType otherAuth;
	/**
	 * contructors
	 * @param roleId
	 * @param personCategoryAuthId
	 * @param personItemDefId
	 * @param selfAuth
	 * @param otherAuth
	 */
	public PersonInfoItemAuth(String roleId, String personCategoryAuthId,
			String personItemDefId, PersonInfoAuthType selfAuth, PersonInfoAuthType otherAuth) {
		super();
		this.roleId = roleId;
		this.personCategoryAuthId = personCategoryAuthId;
		this.personItemDefId = personItemDefId;
		this.selfAuth = selfAuth;
		this.otherAuth = otherAuth;
	}
	public static PersonInfoItemAuth createFromJavaType(String roleId, String personCategoryAuthId,
			String personItemDefId, int selfAuth, int otherAuth){
		if(roleId.isEmpty() || personCategoryAuthId.isEmpty() || personItemDefId.isEmpty()){
			throw new BusinessException(new RawErrorMessage("明細書名が入力されていません。"));
		}
		return new PersonInfoItemAuth(roleId, personCategoryAuthId,personItemDefId,
				EnumAdaptor.valueOf(selfAuth, PersonInfoAuthType.class),
				EnumAdaptor.valueOf(otherAuth, PersonInfoAuthType.class));
	}
	
}
