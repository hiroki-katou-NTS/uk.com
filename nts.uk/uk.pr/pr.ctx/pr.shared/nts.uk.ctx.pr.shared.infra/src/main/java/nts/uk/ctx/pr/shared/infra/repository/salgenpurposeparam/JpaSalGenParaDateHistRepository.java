package nts.uk.ctx.pr.shared.infra.repository.salgenpurposeparam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.*;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QqsmtSalGenParaYmHis;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QqsmtSalGenPrDateHis;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QqsmtSalGenPrDateHisPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class JpaSalGenParaDateHistRepository extends JpaRepository implements SalGenParaDateHistRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtSalGenPrDateHis f";
    private static final String SELECT_BY_KEY_PARANO = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenPrDateHisPk.paraNo =:paraNo AND  f.salGenPrDateHisPk.cid =:cid ORDER BY f.startDate DESC  ";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenPrDateHisPk.paraNo =:paraNo AND  f.salGenPrDateHisPk.cid =:cid AND  f.salGenPrDateHisPk.hisId =:hisId ";
    private static final String SELECT_BY_KEY_HIS_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenPrDateHisPk.hisId =:hisId ";

    @Override
    public Optional<SalGenParaDateHistory> getAllSalGenParaDateHist(String cid,String paraNo){
        List<QqsmtSalGenPrDateHis> entities = this.queryProxy().query(SELECT_BY_KEY_PARANO,QqsmtSalGenPrDateHis.class)
                .setParameter("paraNo", paraNo)
                .setParameter("cid", cid)
                .getList();
        return Optional.of(new SalGenParaDateHistory(paraNo,cid,toDomain(entities)));
    }

    @Override
    public Optional<SalGenParaDateHistory> getSalGenParaDateHistById(String paraNo, String cid, String hisId){
        List<QqsmtSalGenPrDateHis> entities = this.queryProxy().query(SELECT_BY_KEY_STRING,QqsmtSalGenPrDateHis.class)
        .setParameter("paraNo", paraNo)
        .setParameter("cid", cid)
        .setParameter("hisId", hisId)
        .getList();
         return Optional.of(new SalGenParaDateHistory(paraNo,cid,toDomain(entities)));
    }

    @Override
    public void add(DateHistoryItem domain,SalGenParaValue domainSalGenParaValue, String cId,String paraNo){
          this.commandProxy().insert(QqsmtSalGenPrDateHis.toEntity(domain,cId,paraNo,domainSalGenParaValue.getSelection(),domainSalGenParaValue.getAvailableAtr(),domainSalGenParaValue.getNumValue(),domainSalGenParaValue.getCharValue(),domainSalGenParaValue.getTimeValue(),domainSalGenParaValue.getTargetAtr()));

    }



    @Override
    public void update(DateHistoryItem domain,String paraNo,String cId){
        Optional<SalGenParaValue> domainSalGenParaValue = getSalGenParaValueById(domain.identifier());
        this.commandProxy().update(QqsmtSalGenPrDateHis.toEntity(domain,paraNo,cId,domainSalGenParaValue.get().getSelection(),domainSalGenParaValue.get().getAvailableAtr(),domainSalGenParaValue.get().getNumValue(),domainSalGenParaValue.get().getCharValue(),domainSalGenParaValue.get().getTimeValue(),domainSalGenParaValue.get().getTargetAtr()));
    }

    @Override
    public void remove(String paraNo, String cid, String hisId){
        this.commandProxy().remove(QqsmtSalGenPrDateHis.class, new QqsmtSalGenPrDateHisPk(paraNo, cid, hisId));
    }

    private List<DateHistoryItem> toDomain(List<QqsmtSalGenPrDateHis> entities) {
        List<DateHistoryItem> yearMonthHistoryItemList = new ArrayList<DateHistoryItem>();
        if (entities == null || entities.isEmpty()) {
            return yearMonthHistoryItemList;
        }

        entities.forEach(entity -> {
            yearMonthHistoryItemList.add( new DateHistoryItem(entity.salGenPrDateHisPk.hisId,
                    new DatePeriod(entity.startDate,
                            entity.endDate)));
        });
        return yearMonthHistoryItemList;
    }

    @Override
    public List<SalGenParaValue> getAllSalGenParaValue() {
        return null;
    }

    @Override
    public Optional<SalGenParaValue> getSalGenParaValueById(String hisId) {
        return this.queryProxy().query(SELECT_BY_KEY_HIS_ID, QqsmtSalGenPrDateHis.class)
                .setParameter("hisId", hisId)
                .getSingle(c->c.toDomain());
    }

    @Override
    public void addSalGenParaValue(String paraNo,SalGenParaValue domainSalGenParaValue) {
        Optional<SalGenParaDateHistory> monthHistoryOptional =  getSalGenParaDateHistById(paraNo,AppContexts.user().companyId(),domainSalGenParaValue.getHistoryId());

        this.commandProxy().update(QqsmtSalGenPrDateHis.toEntity(monthHistoryOptional.get().getDateHistoryItem().stream().filter(h -> h.identifier().equals(domainSalGenParaValue.getHistoryId())).findFirst().get(),AppContexts.user().companyId(),paraNo,domainSalGenParaValue.getSelection(),domainSalGenParaValue.getAvailableAtr(),domainSalGenParaValue.getNumValue(),domainSalGenParaValue.getCharValue(),domainSalGenParaValue.getTimeValue(),domainSalGenParaValue.getTargetAtr()));


    }

    @Override
    public void updateSalGenParaValue(String paraNo,SalGenParaValue domainSalGenParaValue) {
        Optional<SalGenParaDateHistory> monthHistoryOptional =  getSalGenParaDateHistById(paraNo,AppContexts.user().companyId(),domainSalGenParaValue.getHistoryId());

        this.commandProxy().update(QqsmtSalGenPrDateHis.toEntity(monthHistoryOptional.get().getDateHistoryItem().stream().filter(h -> h.identifier().equals(domainSalGenParaValue.getHistoryId())).findFirst().get(),
                paraNo,
                AppContexts.user().companyId(),
                domainSalGenParaValue.getSelection(),domainSalGenParaValue.getAvailableAtr(),domainSalGenParaValue.getNumValue(),domainSalGenParaValue.getCharValue(),domainSalGenParaValue.getTimeValue(),domainSalGenParaValue.getTargetAtr()));

    }

    @Override
    public void removeSalGenParaValue(String hisId) {

    }
}
