package nts.uk.ctx.pereg.dom.roles.auth;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class PersonInfoRoleAuth extends AggregateRoot {

	private String roleId;

	private String companyId;

	private PersonInfoPermissionType allowMapUpload;

	private PersonInfoPermissionType allowMapBrowse;

	private PersonInfoPermissionType allowDocUpload;

	private PersonInfoPermissionType allowDocRef;

	private PersonInfoPermissionType allowAvatarUpload;

	private PersonInfoPermissionType allowAvatarRef;

	/**
	 * contructors
	 * 
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
			PersonInfoPermissionType allowMapBrowse, PersonInfoPermissionType allowDocUpload,
			PersonInfoPermissionType allowDocRef, PersonInfoPermissionType allowAvatarUpload,
			PersonInfoPermissionType allowAvatarRef) {
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
			int allowMapBrowse, int allowDocUpload, int allowDocRef, int allowAvatarUpload, int allowAvatarRef) {
		if (roleId.isEmpty()) {
			throw new BusinessException(new RawErrorMessage("明細書名が入力されていません。"));
		}
		return new PersonInfoRoleAuth(roleId, companyId,
				EnumAdaptor.valueOf(allowMapUpload, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowMapBrowse, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowDocUpload, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowDocRef, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowAvatarUpload, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(allowAvatarRef, PersonInfoPermissionType.class));
	}

	public static PersonInfoRoleAuth createFromDefaultValue(String roleId, String companyId) {
		return new PersonInfoRoleAuth(roleId, companyId, EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class),
				EnumAdaptor.valueOf(0, PersonInfoPermissionType.class));
	}

	public void updateFromJavaType(int allowMapUpload, int allowMapBrowse, int allowDocUpload, int allowDocRef,
			int allowAvatarUpload, int allowAvatarRef) {
		this.allowMapUpload = EnumAdaptor.valueOf(allowMapUpload, PersonInfoPermissionType.class);
		this.allowMapBrowse = EnumAdaptor.valueOf(allowMapBrowse, PersonInfoPermissionType.class);
		this.allowDocUpload = EnumAdaptor.valueOf(allowDocUpload, PersonInfoPermissionType.class);
		this.allowDocRef = EnumAdaptor.valueOf(allowDocRef, PersonInfoPermissionType.class);
		this.allowAvatarUpload = EnumAdaptor.valueOf(allowAvatarUpload, PersonInfoPermissionType.class);
		this.allowAvatarRef = EnumAdaptor.valueOf(allowAvatarRef, PersonInfoPermissionType.class);
	}

}
