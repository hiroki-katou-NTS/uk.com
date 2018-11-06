package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompanyRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;


/**
* 明細書紐付け設定（会社）: Finder
*/
@Stateless
public class StateLinkSettingCompanyFinder {

    @Inject
    private StateLinkSettingCompanyRepository stateLinkSettingCompanyRepository;

    @Inject
    private StatementLayoutRepository statementLayoutRepository;

    public Optional<StatementLayoutDto> getStateLinkSettingCompanyById(String cid, String hisId){
        Optional<StateLinkSettingCompany> stateLinkSettingCompany =  stateLinkSettingCompanyRepository.getStateLinkSettingCompanyById(hisId);
        StateLinkSettingCompanyDto stateLinkSettingCompanyDto;
        String salaryLayoutName = null;
        String bonusLayoutName = null;
        if(stateLinkSettingCompany.isPresent()){
            stateLinkSettingCompanyDto = StateLinkSettingCompanyDto.fromDomain(stateLinkSettingCompany.get());

            List<StatementLayout> listSatementLayout = statementLayoutRepository.getAllStatementLayout();
            Optional<StatementLayout> salaryLayout = listSatementLayout.stream().filter(o -> o.getStatementCode().equals(stateLinkSettingCompanyDto.getSalaryCode())).findFirst();
            if(salaryLayout.isPresent()){
                salaryLayoutName = salaryLayout.get().getStatementName().v();
            }
            Optional<StatementLayout> bonusLayout = listSatementLayout.stream().filter(o -> o.getStatementCode().equals(stateLinkSettingCompanyDto.getBonusCode())).findFirst();
            if(bonusLayout.isPresent()){
                bonusLayoutName = bonusLayout.get().getStatementName().v();
            }

            return Optional.of(new StatementLayoutDto(hisId,
                    stateLinkSettingCompanyDto.getSalaryCode(),
                    salaryLayoutName,
                    stateLinkSettingCompanyDto.getBonusCode(),
                    bonusLayoutName));
        }
        return Optional.empty();
    }

}
