package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.basic;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic.BasicFixedExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic.BasicFixedExtractionConditionRepository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaBasicFixedExtractionConditionRepository extends JpaRepository implements BasicFixedExtractionConditionRepository {
    @Override
    public List<BasicFixedExtractionCondition> getByID(String id) {
        return null;
    }

    @Override
    public List<BasicFixedExtractionCondition> getByIDs(List<String> ids) {
        return null;
    }

    @Override
    public void register(BasicFixedExtractionCondition domain) {

    }

    @Override
    public void update(BasicFixedExtractionCondition domain) {

    }

    @Override
    public void delete(String id) {

    }
}
