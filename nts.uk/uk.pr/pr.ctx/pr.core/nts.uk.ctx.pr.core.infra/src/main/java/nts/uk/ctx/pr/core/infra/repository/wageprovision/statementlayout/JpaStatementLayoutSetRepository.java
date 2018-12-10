package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementlayout;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.LineByLineSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByCtg;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtLineByLineSet;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtStatementLayoutHist;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Stateless
public class JpaStatementLayoutSetRepository extends JpaRepository implements StatementLayoutSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtLineByLineSet f";
    private static final String SELECT_LINE_BY_HISTORY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.lineByLineSetPk.histId =:histId ";
    private static final String SELECT_HIST_BY_ID = "SELECT f FROM QpbmtStatementLayoutHist f " +
            " WHERE f.statementLayoutHistPk.histId = :histId ";
    private static final String DELETE_ALL_LINE_BY_HISTID = "DELETE FROM QpbmtLineByLineSet f " +
            " WHERE f.lineByLineSetPk.histId =:histId";
    private static final String DELETE_ALL_ITEM_BY_HISTID = "DELETE FROM QpbmtSettingByItem f " +
            " WHERE f.settingByItemPk.histId =:histId";
    private static final String DELETE_ALL_RANGE_BY_HISTID = "DELETE FROM QpbmtStateItemRangeSet f " +
            " WHERE f.stateItemRangeSetPk.histId =:histId";
    private static final String DELETE_ALL_PAYMENT_DETAIL_BY_HISTID = "DELETE FROM QpbmtPayItemDetailSet f " +
            " WHERE f.payItemDetailSetPk.histId =:histId";
    private static final String DELETE_ALL_DEDU_DETAIL_BY_HISTID = "DELETE FROM QpbmtDdtItemDetailSet f " +
            " WHERE f.ddtItemDetailSetPk.histId =:histId";

    @Override
    public List<StatementLayoutSet> getAllStatementLayoutSet(){
        return Collections.emptyList();
    }

    @Override
    public Optional<StatementLayoutSet> getStatementLayoutSetById(String histId){
        Optional<QpbmtStatementLayoutHist> statementLayoutHistEntity = this.queryProxy().query(SELECT_HIST_BY_ID, QpbmtStatementLayoutHist.class)
                .setParameter("histId", histId).getSingle();

        List<QpbmtLineByLineSet> lineByLineSetEntityList = this.queryProxy().query(SELECT_LINE_BY_HISTORY_ID, QpbmtLineByLineSet.class)
                .setParameter("histId", histId)
                .getList();

        Map<Integer, List<QpbmtLineByLineSet>> lineByLineSetEntityMap = lineByLineSetEntityList.stream().collect(groupingBy(QpbmtLineByLineSet::getCategory));

        List<SettingByCtg> settingByCtgList = new ArrayList<>();
        lineByLineSetEntityMap.entrySet().forEach(entry ->
                settingByCtgList.add(new SettingByCtg(entry.getKey(), entry.getValue().stream().map(i -> i.toDomain()).collect(Collectors.toList())))
        );

        if(statementLayoutHistEntity.isPresent()) {
            return lineByLineSetEntityList.isEmpty() ? Optional.empty() : Optional.of(new StatementLayoutSet(histId,
                    statementLayoutHistEntity.get().layoutPattern, settingByCtgList));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void add(String statementCd, StatementLayoutSet domain){
        String cid = AppContexts.user().companyId();

        List<SettingByCtg> settingByCtgList = domain.getListSettingByCtg();
        for(SettingByCtg settingByCtg: settingByCtgList) {
            for(LineByLineSetting lineByLineSetting: settingByCtg.getListLineByLineSet()) {
                QpbmtLineByLineSet lineByLineSettingEntity = QpbmtLineByLineSet.toEntity(cid, statementCd, domain.getHistId(), settingByCtg.getCtgAtr().value, lineByLineSetting);
                this.commandProxy().insert(lineByLineSettingEntity);
            }
        }
    }

    @Override
    public void update(StatementLayoutSet domain){
    }

    @Override
    public void remove(String histId){
        this.getEntityManager().createQuery(DELETE_ALL_LINE_BY_HISTID).setParameter("histId", histId).executeUpdate();
        this.getEntityManager().createQuery(DELETE_ALL_ITEM_BY_HISTID).setParameter("histId", histId).executeUpdate();
        this.getEntityManager().createQuery(DELETE_ALL_PAYMENT_DETAIL_BY_HISTID).setParameter("histId", histId).executeUpdate();
        this.getEntityManager().createQuery(DELETE_ALL_DEDU_DETAIL_BY_HISTID).setParameter("histId", histId).executeUpdate();
        this.getEntityManager().createQuery(DELETE_ALL_RANGE_BY_HISTID).setParameter("histId", histId).executeUpdate();
    }

}
