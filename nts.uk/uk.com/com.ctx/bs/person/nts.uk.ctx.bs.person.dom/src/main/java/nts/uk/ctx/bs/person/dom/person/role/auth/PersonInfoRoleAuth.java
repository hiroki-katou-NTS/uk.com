package nts.uk.ctx.bs.person.dom.person.role.auth;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.dom.AggregateRoot;

public class PersonInfoRoleAuth extends AggregateRoot{
	@Getter
	private String roleId;
	@Getter
	private String companyId;
	@Getter
	private PersonInfoPermissionType allowMapUpload;
	@Getter
	private PersonInfoPermissionType allowMapBrowse;
	@Getter
	private PersonInfoPermissionType allowDocUpload;
	@Getter
	private PersonInfoPermissionType allowDocRef;
	@Getter
	private PersonInfoPermissionType allowAvatarUpload;
	@Getter
	private PersonInfoPermissionType allowAvatarRef;
	
	/**
	 * contructors
	 * @param roleId
	 * @param companyId
	 * @param allowMapUpload
	 * @param allowMapBrowse
	 * @param allowDocUpload
	 * @param allowDocRef
	 * @param allowAvatarUpload
	 * @param allowAvatarRef
	 */
	public PersonInfoRoleAuth(String roleId, String companyId, PersonInfoPermissionType allowMapUpload,
			PersonInfoPermissionType allowMapBrowse, PersonInfoPermissionType allowDocUpload, PersonInfoPermissionType allowDocRef,
			PersonInfoPermissionType allowAvatarUpload, PersonInfoPermissionType allowAvatarRef) {
		super();
		this.roleId = roleId;
		this.companyId = companyId;
		this.allowMapUpload = allowMapUpload;
		this.allowMapBrowse = allowMapBrowse;
		this.allowDocUpload = allowDocUpload;
		this.allowDocRef = allowDocRef;
		this.allowAvatarUpload = allowAvatarUpload;
		this.allowAvatarRef = allowAvatarRef;
	}
	
	public static PersonInfoRoleAuth createFromJavaType(String roleId, String companyId, int allowMapUpload,
			int allowMapBrowse, int allowDocUpload, int allowDocRef, int allowAvatarUpload, int allowAvatarRef){
		if(roleId.isEmpty()){
			throw new BusinessException(new RawErrorMessage("明細書名が入力されていません。"));
		}
		return new PersonInfoRoleAuth(roleId, companyId,
				EnumAdaptor.valueOf(allowMapUpload, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowMapBrowse, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowDocUpload, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowMapUpload, PersonInfoPermissionType.class), 
				EnumAdaptor.valueOf(allowAvatarUpload, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowAvatarRef, PersonInfoPermissionType.class));
	}
	
}
