package nts.uk.ctx.basic.app.find.organization.position;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.basic.dom.organization.position.AuthorizationLevel;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class AuthorizationLevelFinder {
	@Inject
	private PositionRepository repository;
	
	public Optional<AuthorizationLevelDto> getAuthLevel(String authScopeAtr,String authCode) {
		
		String companyCode = AppContexts.user().companyCode();
		return repository.findAllAuth(companyCode,authScopeAtr, authCode)
				.map(e->{return convertToDto(e);});
		
	}

	private AuthorizationLevelDto convertToDto(AuthorizationLevel position) {
		AuthorizationLevelDto positionDto = new AuthorizationLevelDto();
		positionDto.setAuthorizationCode(position.getAuthCode().v());
		positionDto.setAuthorizationName(position.getAuthName().v());
		positionDto.setAuthScopeAtr(position.getAuthScopeAtr().v());
		positionDto.setEmpScopeAtr(position.getEmpScopeAtr().value);
		positionDto.setInChargeAtr(position.getInChargeAtr().value);
		
		return positionDto;
	}
}


