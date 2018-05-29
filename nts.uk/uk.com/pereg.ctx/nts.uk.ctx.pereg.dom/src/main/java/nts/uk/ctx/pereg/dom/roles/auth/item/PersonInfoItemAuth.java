package nts.uk.ctx.pereg.dom.roles.auth.item;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;

@Getter
public class PersonInfoItemAuth extends AggregateRoot {

	private String roleId;

	private String personCategoryAuthId;

	private String personItemDefId;

	private PersonInfoAuthType selfAuth;

	private PersonInfoAuthType otherAuth;

	/**
	 * contructors
	 * 
	 * @param roleId
	 * @param personCategoryAuthId
	 * @param personItemDefId
	 * @param selfAuth
	 * @param otherAuth
	 */
	public PersonInfoItemAuth(String roleId, String personCategoryAuthId, String personItemDefId,
			PersonInfoAuthType selfAuth, PersonInfoAuthType otherAuth) {
		super();
		this.roleId = roleId;
		this.personCategoryAuthId = personCategoryAuthId;
		this.personItemDefId = personItemDefId;
		this.selfAuth = selfAuth;
		this.otherAuth = otherAuth;
	}

	public static PersonInfoItemAuth createFromJavaType(String roleId, String personCategoryAuthId,
			String personItemDefId, int selfAuth, int otherAuth){
		return new PersonInfoItemAuth(roleId, personCategoryAuthId, personItemDefId,
				EnumAdaptor.valueOf(selfAuth, PersonInfoAuthType.class),
				EnumAdaptor.valueOf(otherAuth, PersonInfoAuthType.class));
	}

	public void updateFromJavaType(int selfAuth, int otherAuth) {
		this.selfAuth = EnumAdaptor.valueOf(selfAuth, PersonInfoAuthType.class);
		this.otherAuth = EnumAdaptor.valueOf(otherAuth, PersonInfoAuthType.class);

	}
	
	public boolean seflDiplayItem() {
		return !(this.selfAuth == PersonInfoAuthType.HIDE);
	}
	
	public boolean otherDiplayItem() {
		return !(this.otherAuth == PersonInfoAuthType.HIDE);
	}

}
