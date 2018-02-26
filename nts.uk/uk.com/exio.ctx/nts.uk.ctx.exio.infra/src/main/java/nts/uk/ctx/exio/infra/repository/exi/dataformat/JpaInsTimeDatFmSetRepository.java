package nts.uk.ctx.exio.infra.repository.exi.dataformat;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtInsTimeDatFmSet;
import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtInsTimeDatFmSetPk;
import nts.uk.ctx.exio.dom.exi.dataformat.InsTimeDatFmSetRepository;
import nts.uk.ctx.exio.dom.exi.dataformat.InsTimeDatFmSet;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaInsTimeDatFmSetRepository extends JpaRepository implements InsTimeDatFmSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtInsTimeDatFmSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.insTimeDatFmSetPk.cid =:cid AND  f.insTimeDatFmSetPk.conditionSetCd =:conditionSetCd AND  f.insTimeDatFmSetPk.acceptItemNum =:acceptItemNum ";

    @Override
    public List<InsTimeDatFmSet> getAllInsTimeDatFmSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtInsTimeDatFmSet.class)
                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<InsTimeDatFmSet> getInsTimeDatFmSetById(String cid, String conditionSetCd, int acceptItemNum){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtInsTimeDatFmSet.class)
        .setParameter("cid", cid)
        .setParameter("conditionSetCd", conditionSetCd)
        .setParameter("acceptItemNum", acceptItemNum)
        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(InsTimeDatFmSet domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(InsTimeDatFmSet domain){
        OiomtInsTimeDatFmSet newInsTimeDatFmSet = toEntity(domain);
        OiomtInsTimeDatFmSet updateInsTimeDatFmSet = this.queryProxy().find(newInsTimeDatFmSet.insTimeDatFmSetPk, OiomtInsTimeDatFmSet.class).get();
        if (null == updateInsTimeDatFmSet) {
            return;
        }
        updateInsTimeDatFmSet.version = newInsTimeDatFmSet.version;
        updateInsTimeDatFmSet.delimiterSet = newInsTimeDatFmSet.delimiterSet;
        updateInsTimeDatFmSet.fixedValue = newInsTimeDatFmSet.fixedValue;
        updateInsTimeDatFmSet.hourMinSelect = newInsTimeDatFmSet.hourMinSelect;
        updateInsTimeDatFmSet.effectiveDigitLength = newInsTimeDatFmSet.effectiveDigitLength;
        updateInsTimeDatFmSet.roundProc = newInsTimeDatFmSet.roundProc;
        updateInsTimeDatFmSet.decimalSelect = newInsTimeDatFmSet.decimalSelect;
        updateInsTimeDatFmSet.valueOfFixedValue = newInsTimeDatFmSet.valueOfFixedValue;
        updateInsTimeDatFmSet.startDigit = newInsTimeDatFmSet.startDigit;
        updateInsTimeDatFmSet.endDigit = newInsTimeDatFmSet.endDigit;
        updateInsTimeDatFmSet.roundProcCls = newInsTimeDatFmSet.roundProcCls;
        this.commandProxy().update(updateInsTimeDatFmSet);
    }

    @Override
    public void remove(String cid, String conditionSetCd, int acceptItemNum){
        this.commandProxy().remove(OiomtInsTimeDatFmSetPk.class, new OiomtInsTimeDatFmSetPk(cid, conditionSetCd, acceptItemNum)); 
    }

    private static InsTimeDatFmSet toDomain(OiomtInsTimeDatFmSet entity) {
        return InsTimeDatFmSet.createFromJavaType(entity.version, entity.insTimeDatFmSetPk.cid, entity.insTimeDatFmSetPk.conditionSetCd, entity.insTimeDatFmSetPk.acceptItemNum, entity.delimiterSet, entity.fixedValue, entity.hourMinSelect, entity.effectiveDigitLength, entity.roundProc, entity.decimalSelect, entity.valueOfFixedValue, entity.startDigit, entity.endDigit, entity.roundProcCls);
    }

    private OiomtInsTimeDatFmSet toEntity(InsTimeDatFmSet domain) {
        return new OiomtInsTimeDatFmSet(domain.getVersion(), new OiomtInsTimeDatFmSetPk(domain.getCid(), domain.getConditionSetCd(), domain.getAcceptItemNum()), domain.getDelimiterSet(), domain.getFixedValue(), domain.getHourMinSelect(), domain.getEffectiveDigitLength(), domain.getRoundProc(), domain.getDecimalSelect(), domain.getValueOfFixedValue(), domain.getStartDigit(), domain.getEndDigit(), domain.getRoundProcCls());
    }

}
