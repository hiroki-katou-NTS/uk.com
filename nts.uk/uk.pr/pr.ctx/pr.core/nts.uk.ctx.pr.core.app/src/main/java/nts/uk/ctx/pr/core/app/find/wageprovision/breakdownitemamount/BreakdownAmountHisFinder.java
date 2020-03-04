package nts.uk.ctx.pr.core.app.find.wageprovision.breakdownitemamount;

import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
/**
 * 給与内訳個人金額履歴: Finder
 */
public class BreakdownAmountHisFinder {

    @Inject
    private StatementItemRepository finderStatementItem;

    @Inject
    private BreakdownAmountHisRepository finderBreakdownHis;

    @Inject
    private BreakdownAmountRepository breakdownAmountRepository;

    @Inject
    private BreakdownItemSetRepository breakdownItemSetRepository;

    public List<StatementDto> getStatemetItem() {
        String cid = AppContexts.user().companyId();
        List<StatementDto> lstStatementSt = finderStatementItem.getItemCustomByDeprecated(cid).stream()
                .map(i -> new StatementDto(i)).collect(Collectors.toList());
        List<StatementDto> lstStatementDeduction = finderStatementItem.getItemCustomByDeprecated2(cid).stream()
                .map(i -> new StatementDto(i)).collect(Collectors.toList());
        lstStatementSt.addAll(lstStatementDeduction);
        return lstStatementSt;
    }

    public BreakdownAmountHisDto getBreakdownHis(int categoryAtr, String itemNameCd, int salaryBonusAtr, String employeeID) {
        String cid = AppContexts.user().companyId();
        BreakdownAmountHisDto dto = null;
        Optional<BreakdownAmountHis> data = finderBreakdownHis.getBreakdownAmountHisBySalaryBonusAtr(cid, categoryAtr, itemNameCd, employeeID, salaryBonusAtr);
        if (data.isPresent()) {
            BreakdownAmountHis amount = data.get();
            return BreakdownAmountHisDto.fromDomain(amount);
        } else {
            return dto;
        }
    }

    public List<BreakdownAmountListDto> getBreakDownAmoun(String historyID, int categoryAtr, String itemNameCd) {
        String cid = AppContexts.user().companyId();
        Optional<BreakdownAmount> getAllBreakdownAmountCode = breakdownAmountRepository.getAllBreakdownAmountCode(historyID);
        List<BreakdownAmountListDto> data;
        if (getAllBreakdownAmountCode.isPresent()) {
            BreakdownAmount breakdownAmount = getAllBreakdownAmountCode.get();
            List<BreakdownAmountList> breakdownAmountList = breakdownAmount.getBreakdownAmountList();
            List<BreakdownItemSet> breakdownItemSet = breakdownItemSetRepository.getBreakdownItemStByStatementItemId(cid, categoryAtr, itemNameCd);
            data = breakdownItemSet.stream().map(domain -> {
                Optional<BreakdownAmountList> domain2 = breakdownAmountList.stream().filter(i -> i.getBreakdownItemCode().equals(domain.getBreakdownItemCode())).findFirst();
                return BreakdownAmountListDto.fromDomain(domain2, domain);
            }).collect(Collectors.toList());

        } else {
            List<BreakdownItemSet> breakdownItemSet = breakdownItemSetRepository.getBreakdownItemStByStatementItemId(cid, categoryAtr, itemNameCd);
            data = breakdownItemSet.stream().map(item -> new BreakdownAmountListDto(item.getBreakdownItemCode().v(), item.getBreakdownItemName().v(), 0L)).collect(Collectors.toList());
        }
        return data;
    }
}
