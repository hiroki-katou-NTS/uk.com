package nts.uk.ctx.pr.core.infra.repository.wageprovision.speclayout.itemrangeset;


import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.itemrangeset.SpecificationItemRangeSettingRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.itemrangeset.SpecificationItemRangeSetting;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.speclayout.itemrangeset.QpbmtSpecItemRangeSet;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.speclayout.itemrangeset.QpbmtSpecItemRangeSetPk;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;
import java.util.Optional;

@Stateless
public class JpaSpecificationItemRangeSettingRepository extends JpaRepository implements SpecificationItemRangeSettingRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSpecItemRangeSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.specItemRangeSetPk.histId =:histId ";

    @Override
    public List<SpecificationItemRangeSetting> getAllSpecificationItemRangeSetting(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSpecItemRangeSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SpecificationItemRangeSetting> getSpecificationItemRangeSettingById(String histId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSpecItemRangeSet.class)
        .setParameter("histId", histId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SpecificationItemRangeSetting domain){
        this.commandProxy().insert(QpbmtSpecItemRangeSet.toEntity(domain));
    }

    @Override
    public void update(SpecificationItemRangeSetting domain){
        this.commandProxy().update(QpbmtSpecItemRangeSet.toEntity(domain));
    }

    @Override
    public void remove( String histId){
        this.commandProxy().remove(QpbmtSpecItemRangeSet.class, new QpbmtSpecItemRangeSetPk(histId));
    }
}
