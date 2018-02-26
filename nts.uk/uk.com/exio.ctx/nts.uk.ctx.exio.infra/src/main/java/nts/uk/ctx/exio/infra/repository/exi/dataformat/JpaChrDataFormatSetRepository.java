package nts.uk.ctx.exio.infra.repository.exi.dataformat;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtChrDataFormatSet;
import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtChrDataFormatSetPk;
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSetRepository;
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSet;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaChrDataFormatSetRepository extends JpaRepository implements ChrDataFormatSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtChrDataFormatSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.chrDataFormatSetPk.cid =:cid AND  f.chrDataFormatSetPk.conditionSetCd =:conditionSetCd AND  f.chrDataFormatSetPk.acceptItemNum =:acceptItemNum ";

    @Override
    public List<ChrDataFormatSet> getAllChrDataFormatSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtChrDataFormatSet.class)
                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<ChrDataFormatSet> getChrDataFormatSetById(String cid, String conditionSetCd, int acceptItemNum){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtChrDataFormatSet.class)
        .setParameter("cid", cid)
        .setParameter("conditionSetCd", conditionSetCd)
        .setParameter("acceptItemNum", acceptItemNum)
        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(ChrDataFormatSet domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(ChrDataFormatSet domain){
        OiomtChrDataFormatSet newChrDataFormatSet = toEntity(domain);
        OiomtChrDataFormatSet updateChrDataFormatSet = this.queryProxy().find(newChrDataFormatSet.chrDataFormatSetPk, OiomtChrDataFormatSet.class).get();
        if (null == updateChrDataFormatSet) {
            return;
        }
        updateChrDataFormatSet.version = newChrDataFormatSet.version;
        updateChrDataFormatSet.cdEditing = newChrDataFormatSet.cdEditing;
        updateChrDataFormatSet.fixedValue = newChrDataFormatSet.fixedValue;
        updateChrDataFormatSet.effectiveDigitLength = newChrDataFormatSet.effectiveDigitLength;
        updateChrDataFormatSet.cdConvertCd = newChrDataFormatSet.cdConvertCd;
        updateChrDataFormatSet.cdEditMethod = newChrDataFormatSet.cdEditMethod;
        updateChrDataFormatSet.cdEditDigit = newChrDataFormatSet.cdEditDigit;
        updateChrDataFormatSet.fixedVal = newChrDataFormatSet.fixedVal;
        updateChrDataFormatSet.startDigit = newChrDataFormatSet.startDigit;
        updateChrDataFormatSet.endDigit = newChrDataFormatSet.endDigit;
        this.commandProxy().update(updateChrDataFormatSet);
    }

    @Override
    public void remove(String cid, String conditionSetCd, int acceptItemNum){
        this.commandProxy().remove(OiomtChrDataFormatSetPk.class, new OiomtChrDataFormatSetPk(cid, conditionSetCd, acceptItemNum)); 
    }

    private static ChrDataFormatSet toDomain(OiomtChrDataFormatSet entity) {
        return ChrDataFormatSet.createFromJavaType(entity.version, entity.chrDataFormatSetPk.cid, entity.chrDataFormatSetPk.conditionSetCd, entity.chrDataFormatSetPk.acceptItemNum, entity.cdEditing, entity.fixedValue, entity.effectiveDigitLength, entity.cdConvertCd, entity.cdEditMethod, entity.cdEditDigit, entity.fixedVal, entity.startDigit, entity.endDigit);
    }

    private OiomtChrDataFormatSet toEntity(ChrDataFormatSet domain) {
        return new OiomtChrDataFormatSet(domain.getVersion(), new OiomtChrDataFormatSetPk(domain.getCid(), domain.getConditionSetCd(), domain.getAcceptItemNum()), domain.getCdEditing(), domain.getFixedValue(), domain.getEffectiveDigitLength(), domain.getCdConvertCd(), domain.getCdEditMethod(), domain.getCdEditDigit(), domain.getFixedVal(), domain.getStartDigit(), domain.getEndDigit());
    }

}
