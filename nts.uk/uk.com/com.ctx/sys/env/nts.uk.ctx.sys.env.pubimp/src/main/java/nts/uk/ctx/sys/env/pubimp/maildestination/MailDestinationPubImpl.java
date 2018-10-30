package nts.uk.ctx.sys.env.pubimp.maildestination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.env.dom.contact.EmployeeContactAdapter;
import nts.uk.ctx.sys.env.dom.contact.EmployeeContactObjectImport;
import nts.uk.ctx.sys.env.dom.contact.PersonContactObjectOfEmployeeImport;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SettingUseSendMail;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSettingRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.ctx.sys.env.pub.maildestination.IMailDestinationPub;
import nts.uk.ctx.sys.env.pub.maildestination.MailDestination;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author sonnlb
 *
 */
@Stateless
public class MailDestinationPubImpl implements IMailDestinationPub {

	@Inject
	private MailDestinationFunctionRepository mailDesRepo;
	@Inject
	private UserInfoUseMethodRepository useInfoMethodRepo;
	@Inject
	private EmployeeContactAdapter empContactAdapter;
	@Inject
	private UseContactSettingRepository useContactRepo;

	@Override
	public List<MailDestination> getEmpEmailAddress(String cID, List<String> sIDs, Integer functionID) {

		List<MailDestination> emailAddress = new ArrayList<MailDestination>();
		// パラメータ.社員ID(List)をメール送信一覧に追加する
		sIDs.forEach(sID -> {
			emailAddress.add(new MailDestination(sID, Collections.emptyList()));
		});

		// ドメインモデル「メール送信先機能」を取得する
		List<MailDestinationFunction> mailDes = mailDesRepo.findByCidSettingItemAndUse(cID, functionID, NotUseAtr.USE);

		if (CollectionUtil.isEmpty(mailDes)) {
			return emailAddress;
		}
		mailDes.forEach(x -> {
			// ドメインモデル「ユーザー情報の使用方法」を取得する
			Optional<UserInfoUseMethod> useInfoMethodOpt = useInfoMethodRepo.findByCompanyIdAndSettingItem(cID,
					x.getSettingItem());

			boolean isInvalid = checkUseInfoMethod(useInfoMethodOpt);

			if (isInvalid) {
				setEmail(useInfoMethodOpt.get(), emailAddress, sIDs, cID);
			}

		});

		return emailAddress;

	}

	private void setEmail(UserInfoUseMethod useInfoMethod, List<MailDestination> emailAddress, List<String> sIDs,
			String cID) {

		boolean isCompanyEmail = useInfoMethod.getSettingItem().equals(UserInfoItem.COMPANY_PC_MAIL)
				|| useInfoMethod.getSettingItem().equals(UserInfoItem.COMPANY_MOBILE_MAIL);
		if (isCompanyEmail) {
			// Imported(環境)「社員連絡先」を取得する RequestList 378
			List<EmployeeContactObjectImport> empContacts = empContactAdapter.getList(sIDs);

			setCompanyMail(useInfoMethod, emailAddress, sIDs, empContacts, cID, useInfoMethod.getSettingItem());

		}

		boolean isPersonEmail = useInfoMethod.getSettingItem().equals(UserInfoItem.PERSONAL_PC_MAIL)
				|| useInfoMethod.getSettingItem().equals(UserInfoItem.PERSONAL_MOBILE_MAIL);

		if (isPersonEmail) {
			// Imported(環境)「個人連絡先」を取得する RequestList 420
			List<PersonContactObjectOfEmployeeImport> personContacts = empContactAdapter.getListOfEmployees(sIDs);

			setPersonMail(useInfoMethod, emailAddress, sIDs, personContacts, cID, useInfoMethod.getSettingItem());
		}

	}

	private void setPersonMail(UserInfoUseMethod useInfoMethod, List<MailDestination> emailAddress, List<String> sIDs,
			List<PersonContactObjectOfEmployeeImport> personContacts, String cID, UserInfoItem userInfoItem) {
		boolean isPersonSelectAble = useInfoMethod.getSettingUseMail().get()
				.equals(SettingUseSendMail.PERSONAL_SELECTABLE);

		if (isPersonSelectAble) {
			// ドメインモデル「連絡先使用設定」を取得する
			List<UseContactSetting> useContacts = useContactRepo.findByListEmployeeId(sIDs, cID).stream()
					.filter(x -> x.getSettingItem().equals(userInfoItem)).filter(x -> x.isUseMailSetting())
					.collect(Collectors.toList());

			boolean isUseContactsNotEmpty = !CollectionUtil.isEmpty(useContacts);

			if (isUseContactsNotEmpty) {
				addEmailFromPerContact(emailAddress, sIDs, personContacts,userInfoItem);

			}
		}

		boolean isUse = useInfoMethod.getSettingUseMail().get().equals(SettingUseSendMail.USE);

		if (isUse) {
			addEmailFromPerContact(emailAddress, sIDs, personContacts, userInfoItem);
		}

	}

	private void addEmailFromPerContact(List<MailDestination> emailAddress, List<String> sIDs,
			List<PersonContactObjectOfEmployeeImport> personContacts, UserInfoItem userInfoItem) {
		sIDs.forEach(sID -> {

			Optional<MailDestination> mailDestinationOpt = emailAddress.stream()
					.filter(mailDestination -> mailDestination.getEmployeeID().equals(sID)).findFirst();
			mailDestinationOpt.ifPresent(mailDestination -> {
				Optional<PersonContactObjectOfEmployeeImport> perContactOpt = personContacts.stream()
						.filter(perContact -> perContact.getEmployeeId().equals(sID)).findFirst();

				perContactOpt.ifPresent(item -> {
					if(userInfoItem.equals(UserInfoItem.PERSONAL_PC_MAIL)){
					mailDestination.addOutGoingMails(item.getMailAdress());
					}
					if (userInfoItem.equals(UserInfoItem.PERSONAL_MOBILE_MAIL)) {
						mailDestination.addOutGoingMails(item.getMobileMailAdress());
					}
				});

			});

		});

	}
	
	private void setCompanyMail(UserInfoUseMethod useInfoMethod, List<MailDestination> emailAddress, List<String> sIDs,
			List<EmployeeContactObjectImport> empContacts, String cID, UserInfoItem userInfoItem) {
		boolean isPersonSelectAble = useInfoMethod.getSettingUseMail().get()
				.equals(SettingUseSendMail.PERSONAL_SELECTABLE);
		
		if (isPersonSelectAble) {
			// ドメインモデル「連絡先使用設定」を取得する
			List<UseContactSetting> useContacts = useContactRepo.findByListEmployeeId(sIDs, cID).stream()
					.filter(x -> x.getSettingItem().equals(userInfoItem)).filter(x -> x.isUseMailSetting())
					.collect(Collectors.toList());

			boolean isUseContactsNotEmpty = !CollectionUtil.isEmpty(useContacts);

			if (isUseContactsNotEmpty) {
				addEmailFromEmpContact(emailAddress, sIDs, empContacts, userInfoItem);

			}
		}

		boolean isUse = useInfoMethod.getSettingUseMail().get().equals(SettingUseSendMail.USE);

		if (isUse) {
			addEmailFromEmpContact(emailAddress, sIDs, empContacts, userInfoItem);
		}

	}

	private void addEmailFromEmpContact(List<MailDestination> emailAddress, List<String> sIDs,
			List<EmployeeContactObjectImport> empContacts, UserInfoItem userInfoItem) {
		sIDs.forEach(sID -> {

			Optional<MailDestination> mailDestinationOpt = emailAddress.stream()
					.filter(mailDestination -> mailDestination.getEmployeeID().equals(sID)).findFirst();

			mailDestinationOpt.ifPresent(mailDestination -> {
				Optional<EmployeeContactObjectImport> empContactOpt = empContacts.stream()
						.filter(empContact -> empContact.getSid().equals(sID)).findFirst();

				empContactOpt.ifPresent(item -> {
					if (userInfoItem.equals(UserInfoItem.COMPANY_PC_MAIL)) {
						mailDestination.addOutGoingMails(item.getMailAddress());
					}
					
					if (userInfoItem.equals(UserInfoItem.COMPANY_MOBILE_MAIL)) {
						mailDestination.addOutGoingMails(item.getPhoneMailAddress());
					}
				});

			});

		});

	}

	private boolean checkUseInfoMethod(Optional<UserInfoUseMethod> useInfoMethodOpt) {

		if (!useInfoMethodOpt.isPresent()) {

			return false;
		}

		UserInfoUseMethod useInfoMethod = useInfoMethodOpt.get();

		if (!useInfoMethod.getSettingUseMail().isPresent()) {
			return false;
		}

		SettingUseSendMail settingUseSendMail = useInfoMethod.getSettingUseMail().get();

		if (!(settingUseSendMail.equals(SettingUseSendMail.USE)
				|| settingUseSendMail.equals(SettingUseSendMail.PERSONAL_SELECTABLE))) {

			return false;

		}
		return true;
	}

}
