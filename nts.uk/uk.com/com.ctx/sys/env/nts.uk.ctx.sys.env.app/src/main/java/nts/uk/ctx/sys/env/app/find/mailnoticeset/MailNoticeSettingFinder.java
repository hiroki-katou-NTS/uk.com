/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.company.dto.UserInfoUseMethodDto;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.dto.EmployeeBasicDto;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.dto.EmployeeInfoContactDto;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.dto.MailNoticeSettingDto;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.dto.PasswordPolicyDto;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.dto.PersonContactDto;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.employee.UseContactSettingDto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.EmployeeBasicAdapter;
import nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.EmployeeInfoContactAdapter;
import nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.EnvPasswordPolicyAdapter;
import nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.PersonContactAdapter;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SelfEditUserInfo;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSettingRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class MailNoticeSettingFinder.
 */
@Stateless
public class MailNoticeSettingFinder {

	/** The user info use method repository. */
	@Inject
	private UserInfoUseMethodRepository userInfoUseMethodRepository;

	/** The use contact setting repository. */
	@Inject
	private UseContactSettingRepository useContactSettingRepository;

	/** The employee adapter. */
	@Inject
	private EmployeeBasicAdapter employeeAdapter;

	/** The employee info contact adapter. */
	@Inject
	private EmployeeInfoContactAdapter employeeInfoContactAdapter;

	/** The person contact adapter. */
	@Inject
	private PersonContactAdapter personContactAdapter;

	/** The password policy adapter. */
	@Inject
	private EnvPasswordPolicyAdapter passwordPolicyAdapter;

	/**
	 * Find all.
	 *
	 * @return the mail notice setting dto
	 */
	public MailNoticeSettingDto findAll() {
		String companyId = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();
		MailNoticeSettingDto dto = new MailNoticeSettingDto();

		// Get domain UserInfoUseMethod - ユーザー情報の使用方法
		List<UserInfoUseMethod> listUserInfoUseMethod = this.userInfoUseMethodRepository.findByCompanyId(companyId);
		dto.setListUserInfoUseMethod(listUserInfoUseMethod.stream().map(item -> {
			UserInfoUseMethodDto memento = new UserInfoUseMethodDto();
			item.saveToMemento(memento);
			return memento;
		}).collect(Collectors.toList()));
		
		// Check if password is editable
		Optional<UserInfoUseMethod> opUserInfoUseMethod = listUserInfoUseMethod.stream()
				.filter(item -> UserInfoItem.PASSWORD.equals(item.getSettingItem()))
				.findFirst();
		if (opUserInfoUseMethod.isPresent() && SelfEditUserInfo.CAN_EDIT.equals(opUserInfoUseMethod.get().getSelfEdit())) {
			dto.setEditPassword(true);
		} else {
			dto.setEditPassword(false);
		}		

		// Check if special user or not
		String employeeId = AppContexts.user().employeeId();
		String personId = AppContexts.user().personId();
		if (StringUtil.isNullOrEmpty(employeeId, true)) {
			// Special user
			dto.setNotSpecialUser(false);	
			
			// Throw exception if password can not be edited
			if (!dto.getEditPassword()) {
				throw new BusinessException("Msg_1177");
			}			
		} else {
			// Not special user
			dto.setNotSpecialUser(true);
			
			// Get domain 連絡先使用設定
			List<UseContactSetting> listUseContactSetting = this.useContactSettingRepository
					.findByEmployeeId(employeeId, companyId);
			dto.setListUseContactSetting(listUseContactSetting.stream().map(item -> {
				UseContactSettingDto memento = new UseContactSettingDto();
				item.saveToMemento(memento);
				return memento;
			}).collect(Collectors.toList()));

			// Get import domain 社員連絡先 (from Request List 378)
			List<String> employeeIds = new ArrayList<>();
			employeeIds.add(employeeId);
			Optional<EmployeeInfoContactDto> opEmployeeInfoContact = this.employeeInfoContactAdapter
					.getListContact(employeeIds).stream()
					.filter(item -> item.getEmployeeId().equals(employeeId))
					.findAny()
					.map(item -> {
						EmployeeInfoContactDto memento = new EmployeeInfoContactDto();
						item.saveToMemento(memento);
						return memento;
					});
			dto.setEmployeeInfoContact(opEmployeeInfoContact.orElse(null));

			// Get import domain 個人連絡先 (from Request List 379)
			List<String> personIds = new ArrayList<>();
			personIds.add(personId);
			Optional<PersonContactDto> opPersonContactImport = this.personContactAdapter.getListContact(personIds)
					.stream()
					.filter(item -> item.getPersonId().equals(personId))
					.findAny()
					.map(item -> {
						PersonContactDto memento = new PersonContactDto();
						item.saveToMemento(memento);
						return memento;
					});
			dto.setPersonContact(opPersonContactImport.orElse(null));
		}

		// Get import domain 社員 (from Request List 377)
		Optional<EmployeeBasicDto> opEmployeeBasic = this.employeeAdapter.getEmpBasicBySId(employeeId)
				.map(item -> new EmployeeBasicDto(
						item.getEmployeeId(),
						item.getEmployeeCode(), 
						item.getEmployeeName()));
		dto.setEmployee(opEmployeeBasic.orElse(null));

		// Get import domain パスワードポリシー (from Request List 385)
		Optional<PasswordPolicyDto> opPasswordPolicy = this.passwordPolicyAdapter.getPasswordPolicy(contractCode)
				.map(item -> {
					PasswordPolicyDto memento = new PasswordPolicyDto();
					item.saveToMemento(memento);
					return memento;
				});
		dto.setPasswordPolicy(opPasswordPolicy.orElse(null));
		return dto;
	}

}
