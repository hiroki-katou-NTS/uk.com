package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutHistDto;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompanyRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHist;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
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

    @Inject
    private StatementLayoutHistRepository statementLayoutHistRepository;

    public Optional<StatementLayoutDto> getStateLinkSettingCompanyById(String cid, String hisId, int startYearMonth){
        Optional<StateLinkSettingCompany> stateLinkSettingCompany =  stateLinkSettingCompanyRepository.getStateLinkSettingCompanyById(hisId);
        StateLinkSettingCompanyDto stateLinkSettingCompanyDto;
        String salaryCode = null;
        String salaryLayoutName = null;
        String bonusCode = null;
        String bonusLayoutName = null;
        if(stateLinkSettingCompany.isPresent()){
            stateLinkSettingCompanyDto = StateLinkSettingCompanyDto.fromDomain(stateLinkSettingCompany.get());
            List<StatementLayoutHistDto> listStatementLayout = this.getAllStatementLayoutHis(startYearMonth);
            Optional<StatementLayoutHistDto> salaryLayout = listStatementLayout.stream().filter(o -> o.getStatementCode().equals(stateLinkSettingCompanyDto.getSalaryCode())).findFirst();
            if(salaryLayout.isPresent()){
                salaryCode = salaryLayout.get().getStatementCode();
                salaryLayoutName = salaryLayout.get().getStatementName();
            }
            Optional<StatementLayoutHistDto> bonusLayout = listStatementLayout.stream().filter(o -> o.getStatementCode().equals(stateLinkSettingCompanyDto.getBonusCode())).findFirst();
            if(bonusLayout.isPresent()){
                bonusCode = bonusLayout.get().getStatementCode();
                bonusLayoutName = bonusLayout.get().getStatementName();
            }

            return Optional.of(new StatementLayoutDto(hisId,
                    salaryCode,
                    salaryLayoutName,
                    bonusCode,
                    bonusLayoutName));
        }
        return Optional.empty();
    }

    public List<StatementLayoutHistDto> getAllStatementLayoutHis(int startYearMonth) {
        String cid = AppContexts.user().companyId();
        List<StatementLayoutHistDto> result = new ArrayList<StatementLayoutHistDto>();
        List<StatementLayoutHist> listStatementLayoutHistory = statementLayoutHistRepository.getAllStatementLayoutHistByCid(cid,startYearMonth);
        List<StatementLayout> listStatementLayout =  statementLayoutRepository.getStatementLayoutByCId(cid);
        listStatementLayoutHistory.forEach(item -> {
            result.add(new StatementLayoutHistDto(item.getCid(),item.getStatementCode().v(),
                    listStatementLayout.stream().filter(elementToSearch -> elementToSearch.getStatementCode().v().equals(item.getStatementCode().v())).findFirst().get().getStatementName().v(),
                    item.getHistory().get(0).identifier(),
                    item.getHistory().get(0).start().v(),
                    item.getHistory().get(0).end().v()
            ));
        });
        return result;
    }

}
