package nts.uk.ctx.exio.infra.repository.exi.dataformat;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtNumDataFormatSet;
import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtNumDataFormatSetPk;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSetRepository;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSet;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaNumDataFormatSetRepository extends JpaRepository implements NumDataFormatSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtNumDataFormatSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.numDataFormatSetPk.cid =:cid AND  f.numDataFormatSetPk.conditionSetCd =:conditionSetCd AND  f.numDataFormatSetPk.acceptItemNum =:acceptItemNum ";

    @Override
    public List<NumDataFormatSet> getAllNumDataFormatSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtNumDataFormatSet.class)
                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<NumDataFormatSet> getNumDataFormatSetById(String cid, String conditionSetCd, int acceptItemNum){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtNumDataFormatSet.class)
        .setParameter("cid", cid)
        .setParameter("conditionSetCd", conditionSetCd)
        .setParameter("acceptItemNum", acceptItemNum)
        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(NumDataFormatSet domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(NumDataFormatSet domain){
        OiomtNumDataFormatSet newNumDataFormatSet = toEntity(domain);
        OiomtNumDataFormatSet updateNumDataFormatSet = this.queryProxy().find(newNumDataFormatSet.numDataFormatSetPk, OiomtNumDataFormatSet.class).get();
        if (null == updateNumDataFormatSet) {
            return;
        }
        updateNumDataFormatSet.version = newNumDataFormatSet.version;
        updateNumDataFormatSet.fixedValue = newNumDataFormatSet.fixedValue;
        updateNumDataFormatSet.decimalDivision = newNumDataFormatSet.decimalDivision;
        updateNumDataFormatSet.effectiveDigitLength = newNumDataFormatSet.effectiveDigitLength;
        updateNumDataFormatSet.cdConvertCd = newNumDataFormatSet.cdConvertCd;
        updateNumDataFormatSet.valueOfFixedValue = newNumDataFormatSet.valueOfFixedValue;
        updateNumDataFormatSet.decimalDigitNum = newNumDataFormatSet.decimalDigitNum;
        updateNumDataFormatSet.startDigit = newNumDataFormatSet.startDigit;
        updateNumDataFormatSet.endDigit = newNumDataFormatSet.endDigit;
        updateNumDataFormatSet.decimalPointCls = newNumDataFormatSet.decimalPointCls;
        updateNumDataFormatSet.decimalFraction = newNumDataFormatSet.decimalFraction;
        this.commandProxy().update(updateNumDataFormatSet);
    }

    @Override
    public void remove(String cid, String conditionSetCd, int acceptItemNum){
        this.commandProxy().remove(OiomtNumDataFormatSet.class, new OiomtNumDataFormatSetPk(cid, conditionSetCd, acceptItemNum)); 
    }

    private static NumDataFormatSet toDomain(OiomtNumDataFormatSet entity) {
        return NumDataFormatSet.createFromJavaType(entity.version, entity.numDataFormatSetPk.cid, entity.numDataFormatSetPk.conditionSetCd, entity.numDataFormatSetPk.acceptItemNum, entity.fixedValue, entity.decimalDivision, entity.effectiveDigitLength, entity.cdConvertCd, entity.valueOfFixedValue, entity.decimalDigitNum, entity.startDigit, entity.endDigit, entity.decimalPointCls, entity.decimalFraction);
    }

    private OiomtNumDataFormatSet toEntity(NumDataFormatSet domain) {
        return new OiomtNumDataFormatSet(domain.getVersion(), new OiomtNumDataFormatSetPk(domain.getCid(), domain.getConditionSetCd(), domain.getAcceptItemNum()), domain.getFixedValue(), domain.getDecimalDivision(), domain.getEffectiveDigitLength(), domain.getCdConvertCd(), domain.getValueOfFixedValue(), domain.getDecimalDigitNum(), domain.getStartDigit(), domain.getEndDigit(), domain.getDecimalPointCls(), domain.getDecimalFraction());
    }

}
