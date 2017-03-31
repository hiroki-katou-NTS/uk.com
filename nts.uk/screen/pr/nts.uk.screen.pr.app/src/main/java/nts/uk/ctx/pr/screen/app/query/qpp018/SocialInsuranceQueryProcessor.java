/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.ctx.pr.screen.app.query.qpp018;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.screen.app.query.qpp018.dto.InsuranceOfficeDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author duongnd
 *
 */
@Stateless
public class SocialInsuranceQueryProcessor {

    /** The social insurance office repository. */
    @Inject
    private SocialInsuranceOfficeRepository socialInsuranceOfficeRepo;
    
    /**
     * Find all office.
     *
     * @return the list
     */
    public List<InsuranceOfficeDto> findAllOffice() {
        List<InsuranceOfficeDto> result = new ArrayList<>();
        String companyCode = AppContexts.user().companyCode();
        List<SocialInsuranceOffice> offices = socialInsuranceOfficeRepo.findAll(companyCode);
        for (SocialInsuranceOffice office : offices) {
            InsuranceOfficeDto dto = new InsuranceOfficeDto();
            dto.setCode(office.getCode().toString());
            dto.setName(office.getName().toString());
            result.add(dto);
        }
        return result;
    }
}
