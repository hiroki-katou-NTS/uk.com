/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.company.dto.UserInfoUseMethodDto;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.dto.MailNoticeSettingDto;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.employee.UseContactSettingDto;
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

	/**
	 * Find all.
	 *
	 * @return the mail notice setting dto
	 */
	public MailNoticeSettingDto findAll() {
		String companyId = AppContexts.user().companyId();
		MailNoticeSettingDto dto = new MailNoticeSettingDto();
		
		// Get domain UserInfoUseMethod - ユーザー情報の使用方法
		List<UserInfoUseMethod> listUserInfoUseMethod = this.userInfoUseMethodRepository.findByCompanyId(companyId);
		dto.setListUserInfoUseMethod(listUserInfoUseMethod.stream()
				.map(item -> {
					UserInfoUseMethodDto memento = new UserInfoUseMethodDto();
					item.saveToMemento(memento);
					return memento;
				})
				.collect(Collectors.toList()));
		
		// Check if special user or not
		String employeeId = AppContexts.user().employeeId();
		if (StringUtil.isNullOrEmpty(employeeId, true)) {
			// Special user
			Optional<UserInfoUseMethod> opUserInfoUseMethod = listUserInfoUseMethod.stream()
					.filter(item -> UserInfoItem.PASSWORD.equals(item.getSettingItem()))
					.findFirst();
			if (!opUserInfoUseMethod.isPresent() || SelfEditUserInfo.CAN_NOT_EDIT.equals(opUserInfoUseMethod.get().getSelfEdit())) {
				throw new BusinessException("Msg_1177");
			} 
		} else {
			// Not special user			
			// Get domain 連絡先使用設定			
			List<UseContactSetting> listUseContactSetting = this.useContactSettingRepository.findByEmployeeId(employeeId, companyId);
			dto.setListUseContactSetting(listUseContactSetting.stream()
					.map(item -> {
						UseContactSettingDto memento = new UseContactSettingDto();
						item.saveToMemento(memento);
						return memento;
					})
					.collect(Collectors.toList()));
			
			//TODO import 社員連絡先
			//TODO import 個人連絡先
		}
		
		//TODO import 社員
		//TODO import パスワードポリシー
		return dto;
	}

}
