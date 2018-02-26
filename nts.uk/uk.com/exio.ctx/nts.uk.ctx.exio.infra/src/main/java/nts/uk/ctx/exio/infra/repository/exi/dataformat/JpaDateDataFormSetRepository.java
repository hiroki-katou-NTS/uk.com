package nts.uk.ctx.exio.infra.repository.exi.dataformat;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtDateDataFormSet;
import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtDateDataFormSetPk;
import nts.uk.ctx.exio.dom.exi.dataformat.DateDataFormSetRepository;
import nts.uk.ctx.exio.dom.exi.dataformat.DateDataFormSet;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaDateDataFormSetRepository extends JpaRepository implements DateDataFormSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtDateDataFormSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.dateDataFormSetPk.cid =:cid AND  f.dateDataFormSetPk.conditionSetCd =:conditionSetCd AND  f.dateDataFormSetPk.acceptItemNum =:acceptItemNum ";

    @Override
    public List<DateDataFormSet> getAllDateDataFormSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtDateDataFormSet.class)
                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<DateDataFormSet> getDateDataFormSetById(String cid, String conditionSetCd, int acceptItemNum){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtDateDataFormSet.class)
        .setParameter("cid", cid)
        .setParameter("conditionSetCd", conditionSetCd)
        .setParameter("acceptItemNum", acceptItemNum)
        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(DateDataFormSet domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(DateDataFormSet domain){
        OiomtDateDataFormSet newDateDataFormSet = toEntity(domain);
        OiomtDateDataFormSet updateDateDataFormSet = this.queryProxy().find(newDateDataFormSet.dateDataFormSetPk, OiomtDateDataFormSet.class).get();
        if (null == updateDateDataFormSet) {
            return;
        }
        updateDateDataFormSet.version = newDateDataFormSet.version;
        updateDateDataFormSet.fixedValue = newDateDataFormSet.fixedValue;
        updateDateDataFormSet.valueOfFixedValue = newDateDataFormSet.valueOfFixedValue;
        updateDateDataFormSet.formatSelection = newDateDataFormSet.formatSelection;
        this.commandProxy().update(updateDateDataFormSet);
    }

    @Override
    public void remove(String cid, String conditionSetCd, int acceptItemNum){
        this.commandProxy().remove(OiomtDateDataFormSetPk.class, new OiomtDateDataFormSetPk(cid, conditionSetCd, acceptItemNum)); 
    }

    private static DateDataFormSet toDomain(OiomtDateDataFormSet entity) {
        return DateDataFormSet.createFromJavaType(entity.version, entity.dateDataFormSetPk.cid, entity.dateDataFormSetPk.conditionSetCd, entity.dateDataFormSetPk.acceptItemNum, entity.fixedValue, entity.valueOfFixedValue, entity.formatSelection);
    }

    private OiomtDateDataFormSet toEntity(DateDataFormSet domain) {
        return new OiomtDateDataFormSet(domain.getVersion(), new OiomtDateDataFormSetPk(domain.getCid(), domain.getConditionSetCd(), domain.getAcceptItemNum()), domain.getFixedValue(), domain.getValueOfFixedValue(), domain.getFormatSelection());
    }

}
