package nts.uk.ctx.sys.env.dom.mailnoticeset.company.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.sys.env.dom.mailnoticeset.company.ContactName;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.ContactUsageSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.OtherContact;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SettingContactInformation;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInformationUseMethod;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.EmployeeInfoContactImport;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.OtherContactDTO;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.PersonContactImport;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * DomainService 連絡先情報を取得
 *
 * @author Nws-DucNT
 *
 */
public class UserInformationUseMethodService {

	private UserInformationUseMethodService() {}

	public static ContactInformation get(Require require, String companyId, String employeeId, String personalId) {
		// $設定 = require.ユーザー情報の使用方法を取得する(ログイン会社ID)
		Optional<UserInformationUseMethod> setting = require.getUserInfoByCid(companyId);
		// if　$設定.isEmpty() || $設定.プロフィールの利用==しない
		if (!setting.isPresent() || setting.get().getUseOfProfile() == NotUseAtr.NOT_USE) {
			// return  new 連絡先情報DTO()
			return new ContactInformation();
		}
		// $個人連絡先 = require. 個人連絡先を取得する(個人ID)
		Optional<PersonContactImport> personalContact = require.getByPersonalId(personalId);

		// $社員連絡先 = require. 社員連絡先を取得する(社員ID)
		Optional<EmployeeInfoContactImport> employeeInfoContact = require.getByContactInformation(employeeId);

		// create 連絡先情報DTO
		// $連絡先　＝　new　連絡先情報DTO()
		ContactInformation contactInfo = new ContactInformation();

		// $設定.連絡先情報の設定
		SettingContactInformation settingContactInfo = SettingContactInformation.builder().build();
		if (setting.get().getSettingContactInformation().isPresent()) {
			settingContactInfo = setting.get().getSettingContactInformation().get();
		}
		// if　$社員連絡先　NOT　Empty
		if (employeeInfoContact.isPresent()) {
			EmployeeInfoContactImport employeeContact = employeeInfoContact.get();
			String companyMobilePhoneNumber = "";
			String seatDialIn = "";
			String seatExtensionNumber = "";
			String companyEmailAddress = "";
			String companyMobileEmailAddress = "";
			//$連絡先.会社の携帯電話番号　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.携帯電話（会社用）.連絡先利用設定、$社員連絡先.携帯電話番号が在席照会に表示するか)　==　true　？	$社員連絡先.携帯電話番号　：　Optional.Empty	
			if (settingContactInfo.getCompanyMobilePhone() != null) {
				companyMobilePhoneNumber = checkUsage(settingContactInfo.getCompanyMobilePhone().getContactUsageSetting(), employeeContact.getIsCellPhoneNumberDisplay())
						? employeeInfoContact.get().getCellPhoneNo() : "";
			}

			// $連絡先.座席ダイヤルイン　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.ダイヤルイン番号.連絡先利用設定、$社員連絡先.座席ダイヤルインが在席照会に表示するか)　==　true　？	$社員連絡先.座席ダイヤルイン　：　Optional.Empty
			if (settingContactInfo.getDialInNumber() != null) {
				seatDialIn = checkUsage(settingContactInfo.getDialInNumber().getContactUsageSetting(), employeeContact.getIsSeatDialInDisplay()) 
						? employeeInfoContact.get().getSeatDialIn() : "";
			}

			// $連絡先.座席内線番号　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.内線番号.連絡先利用設定、$社員連絡先.座席内線番号が在席照会に表示するか)　==　true　？ $社員連絡先.座席内線番号　：　Optional.Empty												
			if (settingContactInfo.getExtensionNumber() != null) {
				seatExtensionNumber = checkUsage(settingContactInfo.getExtensionNumber().getContactUsageSetting(), employeeContact.getIsSeatExtensionNumberDisplay()) 
						? employeeInfoContact.get().getSeatExtensionNumber() : "";
			}

			// $連絡先.会社のメールアドレス　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.会社メールアドレス.連絡先利用設定、$社員連絡先.メールアドレスが在席照会に表示するか)　==　true　？ $社員連絡先.メールアドレス　：　Optional.Empty												
			if (settingContactInfo.getCompanyEmailAddress() != null) {
				companyEmailAddress = checkUsage(settingContactInfo.getCompanyEmailAddress().getContactUsageSetting(), employeeContact.getIsMailAddressDisplay()) 
						? employeeInfoContact.get().getMailAddress() : "";
			}
					
			// $連絡先.会社の携帯メールアドレス　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.会社携帯メールアドレス.連絡先利用設定、$社員連絡先.携帯メールアドレスが在席照会に表示するか)　==　true　？	$社員連絡先.携帯メールアドレス　：　Optional.Empty																
			if (settingContactInfo.getCompanyMobileEmailAddress() != null) {
				companyMobileEmailAddress = checkUsage(settingContactInfo.getCompanyMobileEmailAddress().getContactUsageSetting(), employeeContact.getIsMobileMailAddressDisplay()) 
						? employeeInfoContact.get().getMobileMailAddress() : "";
			}
			
			contactInfo.setCompanyMobilePhoneNumber(companyMobilePhoneNumber);
			contactInfo.setSeatDialIn(new ContactName(seatDialIn));
			contactInfo.setSeatExtensionNumber(new ContactName(seatExtensionNumber));
			contactInfo.setCompanyEmailAddress(Optional.ofNullable(companyEmailAddress));
			contactInfo.setCompanyMobileEmailAddress(Optional.ofNullable(companyMobileEmailAddress));
		}

		// if　$個人連絡先　NOT　Empty
		if (personalContact.isPresent()) {
			PersonContactImport personContact = personalContact.get();
			String personalMobilePhoneNumber = "";
			String personalEmailAddress = "";
			String personalMobileEmailAddress = "";
			String emergencyNumber1 = "";
			String emergencyNumber2 = "";
			// $連絡先.個人の携帯電話番号　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.携帯電話（個人用）.連絡先利用設定、$個人連絡先.携帯電話番号が在席照会に表示するか)　==　true　？$個人連絡先.携帯電話番号　：　Optional.Empty														
			if (settingContactInfo.getPersonalMobilePhone() != null) {
				personalMobilePhoneNumber = checkUsage(settingContactInfo.getPersonalMobilePhone().getContactUsageSetting(), personContact.getIsPhoneNumberDisplay())
						? personalContact.get().getCellPhoneNo() : "";
			}

			// $連絡先.個人のメールアドレス　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.個人メールアドレス.連絡先利用設定、$個人連絡先.メールアドレスが在席照会に表示するか)　==　true　？$個人連絡先.メールアドレス　：　Optional.Empty												
			if (settingContactInfo.getPersonalEmailAddress() != null) {
				personalEmailAddress = checkUsage(settingContactInfo.getPersonalEmailAddress().getContactUsageSetting(), personContact.getIsMailAddressDisplay())
						? personalContact.get().getMailAddress() : "";
			}

			// $連絡先.個人の携帯メールアドレス　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.個人携帯メールアドレス.連絡先利用設定、$個人連絡先.携帯メールアドレスが在席照会に表示するか)　==　true　？	$個人連絡先.携帯メールアドレス　：　Optional.Empty													
			if (settingContactInfo.getPersonalMobileEmailAddress() != null) {
				personalMobileEmailAddress = checkUsage(settingContactInfo.getPersonalMobileEmailAddress().getContactUsageSetting(), personContact.getIsMobileEmailAddressDisplay())
						? personalContact.get().getMobileMailAddress() : "";
			}

			// $連絡先.緊急連絡先１　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.緊急電話番号1.連絡先利用設定、$個人連絡先.緊急連絡先１が在席照会に表示するか)　==　true　？$個人連絡先.緊急連絡先１.電話番号　：　Optional.Empty																
			if (settingContactInfo.getEmergencyNumber1() != null) {
				emergencyNumber1 = checkUsage(settingContactInfo.getEmergencyNumber1().getContactUsageSetting(), personContact.getIsEmergencyContact1Display())
						? personalContact.get().getEmergencyNumber1() : "";
			}

			// $連絡先.緊急連絡先２　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.緊急電話番号2.連絡先利用設定、$個人連絡先.緊急連絡先２が在席照会に表示するか)　==　true　？$個人連絡先.緊急連絡先２.電話番号　：　Optional.Empty																
			if (settingContactInfo.getEmergencyNumber2() != null) {
				emergencyNumber2 = checkUsage(settingContactInfo.getEmergencyNumber2().getContactUsageSetting(), personContact.getIsEmergencyContact2Display())
						? personalContact.get().getEmergencyNumber2() : "";
			}
			// $連絡先.他の連絡先　＝　pvt-1.利用チェックする($設定.連絡先情報の設定.他の連絡先.連絡先利用設定、$個人連絡先.他の連絡先.在席照会に表示するか)　==　true　？new　他の連絡先情報（$個人連絡先.他の連絡先.NO、$設定.連絡先情報の設定.他の連絡先.連絡先名、$個人連絡先.他の連絡先.連絡先のアドレス）　：　Optional.Empty						
			// ※他の連絡先がListです（1→５）

			// Map<No, $設定.連絡先情報の設定.他の連絡先.連絡先利用設定>
			Map<Integer, OtherContact> otherContacts = settingContactInfo.getOtherContacts() == null
					? new HashMap<Integer, OtherContact>()
					: settingContactInfo.getOtherContacts().stream()
						.collect(Collectors.toMap(OtherContact::getNo, valueMapper -> valueMapper));
			// Map<No, $個人連絡先.他の連絡先.在席照会に表示するか>
			Map<Integer, OtherContactDTO> otherContactDtos = personContact.getOtherContacts() == null
					? new HashMap<Integer, OtherContactDTO>()
					: personContact.getOtherContacts().stream()
						.collect(Collectors.toMap(OtherContactDTO::getNo, valueMapper -> valueMapper));
			
			List<OtherContactInfomation> otherContactInfors = personContact.getOtherContacts() == null
					? new ArrayList<>()
					: personContact.getOtherContacts().stream().map(mapper -> {
						return checkUsage(otherContacts.get(mapper.getNo()).getContactUsageSetting(), Optional.of(otherContactDtos.get(mapper.getNo()).isDisplay()))
							? new OtherContactInfomation(mapper.getNo(), otherContactDtos.get(mapper.getNo()).getAddress(), otherContacts.get(mapper.getNo()).getContactName().v())
							: null;
			}).collect(Collectors.toList());
			
			contactInfo.setPersonalMobilePhoneNumber(personalMobilePhoneNumber);
			contactInfo.setPersonalEmailAddress(Optional.ofNullable(personalEmailAddress));
			contactInfo.setPersonalMobileEmailAddress(Optional.ofNullable(personalMobileEmailAddress));
			contactInfo.setEmergencyNumber1(emergencyNumber1);
			contactInfo.setEmergencyNumber2(emergencyNumber2);
			contactInfo.setOtherContactsInfomation(otherContactInfors);
		}
		return contactInfo;
	}

	/**
	 * [pvt-1]利用チェックする
	 * 
	 * @param contactUsageSetting
	 * @param isDisplayAttendance
	 * @return
	 */
	public static boolean checkUsage(ContactUsageSetting contactUsageSetting, Optional<Boolean> isDisplayAttendance) {
		if (contactUsageSetting == ContactUsageSetting.DO_NOT_USE) {
			return false;
		}
		if (contactUsageSetting == ContactUsageSetting.USE) {
			return true;
		}
		return isDisplayAttendance.orElse(false);
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
		 * [R-3] 社員連絡先を取得する 社員連絡先Repository.取得する(社員ID)
		 * 
		 * @param employeeId
		 * @return
		 */
		Optional<EmployeeInfoContactImport> getByContactInformation(String employeeId);
	}
}
