package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementlayout;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByCtg;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtSettingByCtg;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtSettingByCtgPk;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtStatementLayoutHist;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStatementLayoutSetRepository extends JpaRepository implements StatementLayoutSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSettingByCtg f";
    private static final String SELECT_BY_HISTORY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.settingByCtgPk.histId =:histId ";
    private static final String SELECT_HIST_BY_ID = "SELECT f FROM QpbmtStatementLayoutHist f " +
            " WHERE f.statementLayoutHistPk.histId = :histId ";
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

        List<QpbmtSettingByCtg> statementLayoutSetEntityList = this.queryProxy().query(SELECT_BY_HISTORY_ID, QpbmtSettingByCtg.class)
                .setParameter("histId", histId)
                .getList();
        List<SettingByCtg> settingByCtgList = statementLayoutSetEntityList.stream().map(x -> x.toDomain()).collect(Collectors.toList());

        if(statementLayoutHistEntity.isPresent()) {
            return statementLayoutSetEntityList.isEmpty() ? Optional.empty() : Optional.of(new StatementLayoutSet(histId,
                    statementLayoutHistEntity.get().layoutPattern, settingByCtgList));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void add(StatementLayoutSet domain){
        List<SettingByCtg> settingByCtgList = domain.getListSettingByCtg();
        for(SettingByCtg settingByCtg: settingByCtgList) {
            QpbmtSettingByCtg settingByCtgEntity = QpbmtSettingByCtg.toEntity(domain.getHistId(), settingByCtg);

            this.commandProxy().insert(settingByCtgEntity);
        }
    }

    @Override
    public void update(StatementLayoutSet domain){
    }

    @Override
    public void remove(String histId){
        for(CategoryAtr category : CategoryAtr.values()) {
            this.commandProxy().remove(QpbmtSettingByCtg.class, new QpbmtSettingByCtgPk(histId, category.value));
            this.getEntityManager().createQuery(DELETE_ALL_PAYMENT_DETAIL_BY_HISTID).setParameter("histId", histId).executeUpdate();
            this.getEntityManager().createQuery(DELETE_ALL_DEDU_DETAIL_BY_HISTID).setParameter("histId", histId).executeUpdate();
            //delete time detail
        }
    }

}
