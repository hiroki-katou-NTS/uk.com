package nts.uk.ctx.bs.person.dom.person.info.setting.user;

public interface UserSettingRepository {
	
	boolean checkUserSettingExist(String employeeId);
	
	void createUserSetting(UserSetting userSetting);
	
	void updateUserSetting(UserSetting userSetting);
}
