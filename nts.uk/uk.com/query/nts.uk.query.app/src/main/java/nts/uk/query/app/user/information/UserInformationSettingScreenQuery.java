package nts.uk.query.app.user.information;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod_;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod_Repository;
import nts.uk.query.app.user.information.setting.MailFunctionDto;
import nts.uk.query.app.user.information.setting.UserInfoUseMethod_Dto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UserInformationSettingScreenQuery {
	@Inject
    private UserInfoUseMethod_Repository userInfoUseMethod_repository;
	
	@Inject
	private MailFunctionRepository mailFunctionRepository;

	public UserInformationSettingDto getUserInformationSettings() {
		String loginCid = AppContexts.user().companyId();
		Optional<UserInfoUseMethod_> userInfoUseMethod_ = this.userInfoUseMethod_repository.findByCId(loginCid);
		UserInfoUseMethod_Dto userInfoUseMethodDto = UserInfoUseMethod_Dto.builder().build();
        userInfoUseMethod_.ifPresent(method -> method.setMemento(userInfoUseMethodDto));
		
		List<MailFunction> mailFunctions = this.mailFunctionRepository.findAll();
		List<MailFunctionDto> mailFunctionDtos = mailFunctions.stream()
				.map(m -> {
					MailFunctionDto dto = new MailFunctionDto();
					m.saveToMemento(dto);
					return dto;
				})
				.collect(Collectors.toList());
		
		return UserInformationSettingDto.builder()
				.infoUseMethodDto(userInfoUseMethodDto)
				.mailFunctionDtos(mailFunctionDtos)
				.build();
	}
}
