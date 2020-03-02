package nts.uk.ctx.pr.shared.infra.repository.salgenpurposeparam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaValue;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaValueRepository;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaYMHistRepository;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaYearMonthHistory;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QqsmtSalGenParaYmHis;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QqsmtSalGenParaYmHisPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

@Stateless
public class JpaSalGenParaYMHistRepository extends JpaRepository implements SalGenParaYMHistRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtSalGenParaYmHis f";
    private static final String SELECT_ALL_QUERY_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenParaYmHisPk.paraNo =:paraNo AND  f.salGenParaYmHisPk.cid =:cid ORDER BY f.startYearMonth DESC ";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenParaYmHisPk.paraNo =:paraNo AND  f.salGenParaYmHisPk.cid =:cid AND  f.salGenParaYmHisPk.hisId =:hisId ";
    private static final String SELECT_BY_KEY_HIS_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenParaYmHisPk.hisId =:hisId ";

    @Override
    public Optional<SalGenParaYearMonthHistory> getAllSalGenParaYMHist(String cid, String paraNo) {
        List<QqsmtSalGenParaYmHis> entities = this.queryProxy().query(SELECT_ALL_QUERY_BY_CID, QqsmtSalGenParaYmHis.class)
                .setParameter("paraNo", paraNo)
                .setParameter("cid", cid)
                .getList();
         return Optional.of(new SalGenParaYearMonthHistory(paraNo,cid,toDomain(entities)));

    }

    @Override
    public Optional<SalGenParaYearMonthHistory> getSalGenParaYMHistById(String paraNo, String cid, String hisId){
        List<QqsmtSalGenParaYmHis> entities = this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtSalGenParaYmHis.class)
        .setParameter("paraNo", paraNo)
        .setParameter("cid", cid)
        .setParameter("hisId", hisId)
        .getList();
        return Optional.of(new SalGenParaYearMonthHistory(paraNo,cid,toDomain(entities)));

    }



    @Override
    public void add(YearMonthHistoryItem domain,SalGenParaValue domainSalGenParaValue, String cId,String paraNo){
        this.commandProxy().insert(QqsmtSalGenParaYmHis.toEntity(domain,cId,paraNo,domainSalGenParaValue.getSelection(),domainSalGenParaValue.getAvailableAtr(),domainSalGenParaValue.getNumValue(),domainSalGenParaValue.getCharValue(),domainSalGenParaValue.getTimeValue(),domainSalGenParaValue.getTargetAtr()));
    }

    @Override
    public void update(YearMonthHistoryItem domain,String cId,String paraNo){
        Optional<SalGenParaValue> domainSalGenParaValue = getSalGenParaValueById(domain.identifier());
        this.commandProxy().update(QqsmtSalGenParaYmHis.toEntity(domain,cId,paraNo,domainSalGenParaValue.get().getSelection(),domainSalGenParaValue.get().getAvailableAtr(),domainSalGenParaValue.get().getNumValue(),domainSalGenParaValue.get().getCharValue(),domainSalGenParaValue.get().getTimeValue(),domainSalGenParaValue.get().getTargetAtr()));
    }

    @Override
    public void remove(String paraNo, String cid, String hisId){
        this.commandProxy().remove(QqsmtSalGenParaYmHis.class, new QqsmtSalGenParaYmHisPk(paraNo, cid, hisId));
    }
    private List<YearMonthHistoryItem> toDomain(List<QqsmtSalGenParaYmHis> entities) {
        List<YearMonthHistoryItem> yearMonthHistoryItemList = new ArrayList<YearMonthHistoryItem>();
        if (entities == null || entities.isEmpty()) {
            return yearMonthHistoryItemList;
        }

        entities.forEach(entity -> {
            yearMonthHistoryItemList.add( new YearMonthHistoryItem(entity.salGenParaYmHisPk.hisId,
                    new YearMonthPeriod(new YearMonth(entity.startYearMonth),
                            new YearMonth(entity.endYearMonth))));
        });
        return yearMonthHistoryItemList;
    }

    @Override
    public List<SalGenParaValue> getAllSalGenParaValue() {
        return null;
    }

    @Override
    public Optional<SalGenParaValue> getSalGenParaValueById(String hisId) {
        return this.queryProxy().query(SELECT_BY_KEY_HIS_ID, QqsmtSalGenParaYmHis.class)
                .setParameter("hisId", hisId)
                .getSingle(c->c.toDomain());

    }

    @Override
    public void addSalGenParaValue(String paraNo,SalGenParaValue domainSalGenParaValue) {
        Optional<SalGenParaYearMonthHistory> monthHistoryOptional =  getSalGenParaYMHistById(paraNo,AppContexts.user().companyId(),domainSalGenParaValue.getHistoryId());

        this.commandProxy().update(QqsmtSalGenParaYmHis.toEntity(monthHistoryOptional.get().getHistory().stream().filter(h -> h.identifier().equals(domainSalGenParaValue.getHistoryId())).findFirst().get(),AppContexts.user().companyId(),paraNo,domainSalGenParaValue.getSelection(),domainSalGenParaValue.getAvailableAtr(),domainSalGenParaValue.getNumValue(),domainSalGenParaValue.getCharValue(),domainSalGenParaValue.getTimeValue(),domainSalGenParaValue.getTargetAtr()));

    }

    @Override
    public void updateSalGenParaValue(String paraNo,SalGenParaValue domainSalGenParaValue) {
        Optional<SalGenParaYearMonthHistory> monthHistoryOptional =  getSalGenParaYMHistById(paraNo,AppContexts.user().companyId(),domainSalGenParaValue.getHistoryId());

        this.commandProxy().update(QqsmtSalGenParaYmHis.toEntity(monthHistoryOptional.get().getHistory().stream().filter(h -> h.identifier().equals(domainSalGenParaValue.getHistoryId())).findFirst().get(),AppContexts.user().companyId(),paraNo,domainSalGenParaValue.getSelection(),domainSalGenParaValue.getAvailableAtr(),domainSalGenParaValue.getNumValue(),domainSalGenParaValue.getCharValue(),domainSalGenParaValue.getTimeValue(),domainSalGenParaValue.getTargetAtr()));

    }

    @Override
    public void removeSalGenParaValue(String hisId) {

    }
}
