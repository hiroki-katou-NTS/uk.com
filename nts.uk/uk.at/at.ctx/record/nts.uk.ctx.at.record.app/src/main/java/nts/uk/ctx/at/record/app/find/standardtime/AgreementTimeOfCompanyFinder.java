package nts.uk.ctx.at.record.app.find.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.find.standardtime.dto.AgreementTimeOfCompanyDto;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfCompany;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeCompanyRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.BasicAgreementSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 全社 screen
 *
 */
@Stateless
public class AgreementTimeOfCompanyFinder {

	@Inject
	private AgreementTimeCompanyRepository agreementTimeCompanyRepository;

	@Inject
	private BasicAgreementSettingRepository basicAgreementSettingRepository;

	public AgreementTimeOfCompanyDto findAll(int laborSystemAtr) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		Optional<AgreementTimeOfCompany> agreementTimeOfCompany = agreementTimeCompanyRepository.find(companyId,
				EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class));

		if (agreementTimeOfCompany.isPresent()) {
			String basicSettingId = agreementTimeOfCompany.get().getBasicSettingId();
			return basicAgreementSettingRepository.find(basicSettingId).map(f -> AgreementTimeOfCompanyDto.toDomain(f))
					.orElse(null);

		} else {
			return null;
		}
	}
}
