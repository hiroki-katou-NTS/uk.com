package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompanyRepository;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;


/**
* 明細書紐付け設定（会社）: Finder
*/
@Stateless
public class StateLinkSettingCompanyFinder {

    @Inject
    private StateLinkSettingCompanyRepository finder;

    public Optional<StateLinkSettingCompanyDto> getStateLinkSettingCompanyById(String hisId){
        Optional<StateLinkSettingCompany> stateLinkSettingCompany =  finder.getStateLinkSettingCompanyById(hisId);
        if(stateLinkSettingCompany.isPresent()){
            return Optional.of(StateLinkSettingCompanyDto.fromDomain(stateLinkSettingCompany.get()));
        }
        return Optional.empty();
    }

}
