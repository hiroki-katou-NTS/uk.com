package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSettingRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtQualificationGroupSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Stateless
public class JpaQualificationGroupSettingRepository extends JpaRepository implements QualificationGroupSettingRepository {

    private static final String FIND_BY_COMPANY = "SELECT a FROM QpbmtQualificationGroupSetting a WHERE a.qualificationGroupSettingPk.cid =:cid ORDER BY a.qualificationGroupSettingPk.qualificationGroupCode";
    private static final String FIND_BY_COMPANY_AND_CODE = "SELECT a FROM QpbmtQualificationGroupSetting a WHERE a.qualificationGroupSettingPk.cid =:cid AND a.qualificationGroupSettingPk.qualificationGroupCode =:qualificationGroupCode";
    private static final String DELETE_BY_COMPANY_AND_CODE = "DELETE FROM QpbmtQualificationGroupSetting a WHERE a.qualificationGroupSettingPk.cid =:cid AND a.qualificationGroupSettingPk.qualificationGroupCode =:qualificationGroupCode";

    @Override
    public List<QualificationGroupSetting> getQualificationGroupSettingByCompanyID() {
        return QpbmtQualificationGroupSetting.toDomain(this.queryProxy().query(FIND_BY_COMPANY, QpbmtQualificationGroupSetting.class).setParameter("cid", AppContexts.user().companyId()).getList().stream().filter(distinctByKey(item -> item.qualificationGroupSettingPk.qualificationGroupCode)).collect(Collectors.toList()));
    }

    @Override
    public Optional<QualificationGroupSetting> getQualificationGroupSettingById(String qualificationGroupCode) {
        return QpbmtQualificationGroupSetting.toDomainForGroup(this.queryProxy().query(FIND_BY_COMPANY_AND_CODE, QpbmtQualificationGroupSetting.class).setParameter("cid", AppContexts.user().companyId()).setParameter("qualificationGroupCode", qualificationGroupCode).getList());
    }

    @Override
    public void add(QualificationGroupSetting domain) {
        this.commandProxy().insertAll(QpbmtQualificationGroupSetting.toEntity(domain));
    }

    @Override
    public void update(QualificationGroupSetting domain) {
        this.getEntityManager().createQuery(DELETE_BY_COMPANY_AND_CODE, QpbmtQualificationGroupSetting.class).setParameter("cid", AppContexts.user().companyId()).setParameter("qualificationGroupCode", domain.getQualificationGroupCode().v()).executeUpdate();
        this.commandProxy().insertAll(QpbmtQualificationGroupSetting.toEntity(domain));
    }

    @Override
    public void remove(QualificationGroupSetting domain) {
        this.getEntityManager().createQuery(DELETE_BY_COMPANY_AND_CODE, QpbmtQualificationGroupSetting.class).setParameter("cid", AppContexts.user().companyId()).setParameter("qualificationGroupCode", domain.getQualificationGroupCode().v()).executeUpdate();
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
