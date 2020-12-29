package nts.uk.ctx.sys.env.dom.mailnoticeset.company.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.sys.env.dom.mailnoticeset.company.ContactUsageSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.OtherContact;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInformationUseMethod;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.EmployeeInfoContactImport;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.PersonContactImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * DomainService 連絡先情報を取得
 *
 * @author Nws-DucNT
 *
 */
public class UserInformationUseMethodService {

	private UserInformationUseMethodService() {
	}

	private ContactInformationDTO get(Require require, String employeeId, String personalId) {
		String companyId = AppContexts.user().companyId();
		// $設定 = require.ユーザー情報の使用方法を取得する(ログイン会社ID)
		Optional<UserInformationUseMethod> setting = require.getUserInfoByCid(companyId);
		if (!setting.isPresent() || setting.get().getUseOfProfile() == NotUseAtr.NOT_USE) {
			return new ContactInformationDTO();
		}
		// $個人連絡先 = require. 個人連絡先を取得する(個人ID)
		Optional<PersonContactImport> personalContact = require.getByPersonalId(personalId);

		// $社員連絡先 = require. 社員連絡先を取得する(社員ID)
		Optional<EmployeeInfoContactImport> employeeInfoContact = require.getByContactInformation(employeeId);

		// create 連絡先情報DTO
		// $連絡先　＝　new　連絡先情報DTO()
		ContactInformationDTO dto = new ContactInformationDTO();

		if (employeeInfoContact.isPresent()) {
			//$連絡先.会社の携帯電話番号　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.携帯電話（会社用）.連絡先利用設定、$社員連絡先.携帯電話番号が在席照会に表示するか)　==　true　？	$社員連絡先.携帯電話番号　：　Optional.Empty	
			String companyMobilePhoneNumber = this.checkUsage(setting.get().getSettingContactInformation().get().getCompanyMobilePhone().getContactUsageSetting(), 
					Optional.of(employeeInfoContact.get().getCellPhoneNo().isEmpty())) == true ? employeeInfoContact.get().getCellPhoneNo() : "";
					
			// $連絡先.座席ダイヤルイン　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.ダイヤルイン番号.連絡先利用設定、$社員連絡先.座席ダイヤルインが在席照会に表示するか)　==　true　？	$社員連絡先.座席ダイヤルイン　：　Optional.Empty
			String seatDialIn = this.checkUsage(setting.get().getSettingContactInformation().get().getDialInNumber().getContactUsageSetting(),
					Optional.of(employeeInfoContact.get().getSeatDialIn().isEmpty())) == true ? employeeInfoContact.get().getSeatDialIn() : "";
			
			// $連絡先.座席内線番号　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.内線番号.連絡先利用設定、$社員連絡先.座席内線番号が在席照会に表示するか)　==　true　？ $社員連絡先.座席内線番号　：　Optional.Empty												
			String seatExtensionNumber = this.checkUsage(setting.get().getSettingContactInformation().get().getExtensionNumber().getContactUsageSetting(),
					Optional.of(employeeInfoContact.get().getSeatExtensionNumber().isEmpty())) == true ? employeeInfoContact.get().getSeatExtensionNumber() : "";
					
			// $連絡先.会社のメールアドレス　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.会社メールアドレス.連絡先利用設定、$社員連絡先.メールアドレスが在席照会に表示するか)　==　true　？ $社員連絡先.メールアドレス　：　Optional.Empty												
			String companyEmailAddress = this.checkUsage(setting.get().getSettingContactInformation().get().getCompanyEmailAddress().getContactUsageSetting(),
					Optional.of(employeeInfoContact.get().getMailAddress().isEmpty())) == true ? employeeInfoContact.get().getMailAddress() : "";
					
			// $連絡先.会社の携帯メールアドレス　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.会社携帯メールアドレス.連絡先利用設定、$社員連絡先.携帯メールアドレスが在席照会に表示するか)　==　true　？	$社員連絡先.携帯メールアドレス　：　Optional.Empty																
			String companyMobileEmailAddress = this.checkUsage(setting.get().getSettingContactInformation().get().getCompanyMobileEmailAddress().getContactUsageSetting(),
					Optional.of(employeeInfoContact.get().getMobileMailAddress().isEmpty())) == true ? employeeInfoContact.get().getMobileMailAddress() : "";
			
			dto.setCompanyMobilePhoneNumber(companyMobilePhoneNumber);
			dto.setSeatDialIn(seatDialIn);
			dto.setSeatExtensionNumber(seatExtensionNumber);
			dto.setCompanyEmailAddress(Optional.of(companyEmailAddress));
			dto.setCompanyMobileEmailAddress(Optional.of(companyMobileEmailAddress));
		}

		if (personalContact.isPresent()) {
			// $連絡先.個人の携帯電話番号　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.携帯電話（個人用）.連絡先利用設定、$個人連絡先.携帯電話番号が在席照会に表示するか)　==　true　？$個人連絡先.携帯電話番号　：　Optional.Empty														
			String personalMobilePhoneNumber = this.checkUsage(setting.get().getSettingContactInformation().get().getPersonalMobilePhone().getContactUsageSetting(),
					Optional.of(personalContact.get().getCellPhoneNo().isEmpty())) == true ? personalContact.get().getCellPhoneNo() : "";
					
			// $連絡先.個人のメールアドレス　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.個人メールアドレス.連絡先利用設定、$個人連絡先.メールアドレスが在席照会に表示するか)　==　true　？$個人連絡先.メールアドレス　：　Optional.Empty												
			String personalEmailAddress = this.checkUsage(setting.get().getSettingContactInformation().get().getPersonalEmailAddress().getContactUsageSetting(),
					Optional.of(personalContact.get().getMailAddress().isEmpty())) == true ? personalContact.get().getMailAddress() : "";
			
			// $連絡先.個人の携帯メールアドレス　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.個人携帯メールアドレス.連絡先利用設定、$個人連絡先.携帯メールアドレスが在席照会に表示するか)　==　true　？	$個人連絡先.携帯メールアドレス　：　Optional.Empty													
			String personalMobileEmailAddress = this.checkUsage(setting.get().getSettingContactInformation().get().getPersonalMobileEmailAddress().getContactUsageSetting(),
					Optional.of(personalContact.get().getMobileMailAddress().isEmpty())) == true ? personalContact.get().getMobileMailAddress() : "";
					
			// $連絡先.緊急連絡先１　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.緊急電話番号1.連絡先利用設定、$個人連絡先.緊急連絡先１が在席照会に表示するか)　==　true　？$個人連絡先.緊急連絡先１.電話番号　：　Optional.Empty																
			String emergencyNumber1 = this.checkUsage(setting.get().getSettingContactInformation().get().getEmergencyNumber1().getContactUsageSetting(),
					Optional.of(personalContact.get().getEmergencyNumber1().isEmpty())) == true ? personalContact.get().getEmergencyNumber1() : "";
			// $連絡先.緊急連絡先２　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.緊急電話番号2.連絡先利用設定、$個人連絡先.緊急連絡先２が在席照会に表示するか)　==　true　？$個人連絡先.緊急連絡先２.電話番号　：　Optional.Empty																
			String emergencyNumber2 = this.checkUsage(setting.get().getSettingContactInformation().get().getEmergencyNumber2().getContactUsageSetting(),
							Optional.of(personalContact.get().getEmergencyNumber2().isEmpty())) == true ? personalContact.get().getEmergencyNumber2() : "";
			// $連絡先.他の連絡先　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.他の連絡先.連絡先利用設定、$個人連絡先.他の連絡先.在席照会に表示するか)　==　true　？new　他の連絡先情報（$個人連絡先.他の連絡先.NO、$設定.連絡先情報の設定.他の連絡先.連絡先名、$個人連絡先.他の連絡先.連絡先のアドレス）　：　Optional.Empty						
			// ※他の連絡先がListです（1→５）	
			List<OtherContact> otherContacts = new ArrayList<OtherContact>();
			
//			for (OtherContact contact : setting.get().getSettingContactInformation().get().getOtherContacts()) {
//				this.checkUsage(contact.getContactUsageSetting(), true ) == true ? personalContact.get().getOtherContacts() : 
//			}
//			List<OtherContact> otherContacts = this.checkUsage(setting.get().getSettingContactInformation().get().getOtherContacts(),
//					Optional.of(personalContact.get().getEmergencyNumber2().isEmpty())) == true ? personalContact.get().getEmergencyNumber2 : "";
			
			dto.setPersonalMobilePhoneNumber(personalMobilePhoneNumber);
			dto.setPersonalEmailAddress(Optional.of(personalEmailAddress));
			dto.setPersonalMobileEmailAddress(Optional.of(personalMobileEmailAddress));
			dto.setEmergencyNumber1(emergencyNumber1);
			dto.setEmergencyNumber2(emergencyNumber2);
			dto.setOtherContacts(otherContacts);
			
		}
		return dto;
	}

	/**
	 * [pvt-1]利用チェックする
	 * 
	 * @param contactUsageSetting
	 * @param isDisplayAttendance
	 * @return
	 */
	private boolean checkUsage(ContactUsageSetting contactUsageSetting, Optional<Boolean> isDisplayAttendance) {
		if (contactUsageSetting == ContactUsageSetting.DO_NOT_USE)
			return false;
		if (contactUsageSetting == ContactUsageSetting.USE)
			return true;
		return isDisplayAttendance.isPresent();
	}

	public static interface Require {

		/**
		 * [R-1] ユーザー情報の使用方法を取得する ユーザー情報の使用方法Repository.取得する(会社ID)
		 * 
		 * @param cid
		 * @return
		 */
		Optional<UserInformationUseMethod> getUserInfoByCid(String cid);

		/**
		 * [R-2] 個人連絡先を取得する 個人連絡先Repository.取得する(個人ID)
		 * 
		 * @param personalId
		 * @return
		 */
		Optional<PersonContactImport> getByPersonalId(String personalId);

		/**
		 * [R-3] 社員連絡先を取得する 社員連絡先Repository.取得する(個人ID)
		 * 
		 * @param employeeId
		 * @return
		 */
		Optional<EmployeeInfoContactImport> getByContactInformation(String employeeId);
	}
}
