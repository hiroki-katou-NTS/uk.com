package nts.uk.ctx.exio.infra.repository.exi.condset;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exi.condset.OiomtAcScreenCondSet;
import nts.uk.ctx.exio.infra.entity.exi.condset.OiomtAcScreenCondSetPk;
import nts.uk.ctx.exio.dom.exi.condset.AcScreenCondSetRepository;
import nts.uk.ctx.exio.dom.exi.condset.AcScreenCondSet;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaAcScreenCondSetRepository extends JpaRepository implements AcScreenCondSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtAcScreenCondSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.acScreenCondSetPk.cid =:cid AND  f.acScreenCondSetPk.conditionSetCd =:conditionSetCd AND  f.acScreenCondSetPk.acceptItemNum =:acceptItemNum ";

    @Override
    public List<AcScreenCondSet> getAllAcScreenCondSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtAcScreenCondSet.class)
                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<AcScreenCondSet> getAcScreenCondSetById(String cid, String conditionSetCd, int acceptItemNum){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtAcScreenCondSet.class)
        .setParameter("cid", cid)
        .setParameter("conditionSetCd", conditionSetCd)
        .setParameter("acceptItemNum", acceptItemNum)
        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(AcScreenCondSet domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(AcScreenCondSet domain){
        OiomtAcScreenCondSet newAcScreenCondSet = toEntity(domain);
        OiomtAcScreenCondSet updateAcScreenCondSet = this.queryProxy().find(newAcScreenCondSet.acScreenCondSetPk, OiomtAcScreenCondSet.class).get();
        if (null == updateAcScreenCondSet) {
            return;
        }
        updateAcScreenCondSet.version = newAcScreenCondSet.version;
        updateAcScreenCondSet.selCompareCond = newAcScreenCondSet.selCompareCond;
        updateAcScreenCondSet.timeCondVal2 = newAcScreenCondSet.timeCondVal2;
        updateAcScreenCondSet.timeCondVal1 = newAcScreenCondSet.timeCondVal1;
        updateAcScreenCondSet.timeMoCondVal2 = newAcScreenCondSet.timeMoCondVal2;
        updateAcScreenCondSet.timeMoCondVal1 = newAcScreenCondSet.timeMoCondVal1;
        updateAcScreenCondSet.dateCondVal2 = newAcScreenCondSet.dateCondVal2;
        updateAcScreenCondSet.dateCondVal1 = newAcScreenCondSet.dateCondVal1;
        updateAcScreenCondSet.charCondVal2 = newAcScreenCondSet.charCondVal2;
        updateAcScreenCondSet.charCondVal1 = newAcScreenCondSet.charCondVal1;
        updateAcScreenCondSet.numCondVal2 = newAcScreenCondSet.numCondVal2;
        updateAcScreenCondSet.numCondVal1 = newAcScreenCondSet.numCondVal1;
        this.commandProxy().update(updateAcScreenCondSet);
    }

    @Override
    public void remove(String cid, String conditionSetCd, int acceptItemNum){
        this.commandProxy().remove(OiomtAcScreenCondSetPk.class, new OiomtAcScreenCondSetPk(cid, conditionSetCd, acceptItemNum)); 
    }

    private static AcScreenCondSet toDomain(OiomtAcScreenCondSet entity) {
        return AcScreenCondSet.createFromJavaType(entity.version, entity.acScreenCondSetPk.cid, entity.acScreenCondSetPk.conditionSetCd, entity.acScreenCondSetPk.acceptItemNum, entity.selCompareCond, entity.timeCondVal2, entity.timeCondVal1, entity.timeMoCondVal2, entity.timeMoCondVal1, entity.dateCondVal2, entity.dateCondVal1, entity.charCondVal2, entity.charCondVal1, entity.numCondVal2, entity.numCondVal1);
    }

    private OiomtAcScreenCondSet toEntity(AcScreenCondSet domain) {
        return new OiomtAcScreenCondSet(domain.getVersion(), new OiomtAcScreenCondSetPk(domain.getCid(), domain.getConditionSetCd(), domain.getAcceptItemNum()), domain.getSelectComparisonCondition(), domain.getTimeConditionValue2(), domain.getTimeConditionValue1(), domain.getTimeMomentConditionValue2(), domain.getTimeMomentConditionValue1(), domain.getDateConditionValue2(), domain.getDateConditionValue1(), domain.getCharacterConditionValue2(), domain.getCharacterConditionValue1(), domain.getNumberConditionValue2(), domain.getNumberConditionValue1());
    }

}
