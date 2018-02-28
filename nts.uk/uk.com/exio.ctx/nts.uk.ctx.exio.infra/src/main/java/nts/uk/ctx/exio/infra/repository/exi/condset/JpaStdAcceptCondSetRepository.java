package nts.uk.ctx.exio.infra.repository.exi.condset;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exi.condset.OiomtStdAcceptCondSet;
import nts.uk.ctx.exio.infra.entity.exi.condset.OiomtStdAcceptCondSetPk;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaStdAcceptCondSetRepository extends JpaRepository implements StdAcceptCondSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtStdAcceptCondSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stdAcceptCondSetPk.cid =:cid AND  f.stdAcceptCondSetPk.conditionSetCd =:conditionSetCd ";

    @Override
    public List<StdAcceptCondSet> getAllStdAcceptCondSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtStdAcceptCondSet.class)
                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<StdAcceptCondSet> getStdAcceptCondSetById(String cid, String conditionSetCd){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtStdAcceptCondSet.class)
        .setParameter("cid", cid)
        .setParameter("conditionSetCd", conditionSetCd)
        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(StdAcceptCondSet domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(StdAcceptCondSet domain){
        OiomtStdAcceptCondSet newStdAcceptCondSet = toEntity(domain);
        OiomtStdAcceptCondSet updateStdAcceptCondSet = this.queryProxy().find(newStdAcceptCondSet.stdAcceptCondSetPk, OiomtStdAcceptCondSet.class).get();
        if (null == updateStdAcceptCondSet) {
            return;
        }
        updateStdAcceptCondSet.version = newStdAcceptCondSet.version;
        updateStdAcceptCondSet.categoryId = newStdAcceptCondSet.categoryId;
        updateStdAcceptCondSet.csvDataLineNumber = newStdAcceptCondSet.csvDataLineNumber;
        updateStdAcceptCondSet.systemType = newStdAcceptCondSet.systemType;
        updateStdAcceptCondSet.deleteExistData = newStdAcceptCondSet.deleteExistData;
        updateStdAcceptCondSet.csvDataStartLine = newStdAcceptCondSet.csvDataStartLine;
        updateStdAcceptCondSet.acceptMode = newStdAcceptCondSet.acceptMode;
        updateStdAcceptCondSet.conditionSetName = newStdAcceptCondSet.conditionSetName;
        updateStdAcceptCondSet.checkCompleted = newStdAcceptCondSet.checkCompleted;
        updateStdAcceptCondSet.deleteExtDataMethod = newStdAcceptCondSet.deleteExtDataMethod;
        this.commandProxy().update(updateStdAcceptCondSet);
    }

    @Override
    public void remove(String cid, String conditionSetCd){
        this.commandProxy().remove(OiomtStdAcceptCondSetPk.class, new OiomtStdAcceptCondSetPk(cid, conditionSetCd)); 
    }

    private static StdAcceptCondSet toDomain(OiomtStdAcceptCondSet entity) {
        return StdAcceptCondSet.createFromJavaType(entity.version, entity.stdAcceptCondSetPk.cid, entity.stdAcceptCondSetPk.conditionSetCd, entity.categoryId, entity.csvDataLineNumber, entity.systemType, entity.deleteExistData, entity.csvDataStartLine, entity.acceptMode, entity.conditionSetName, entity.checkCompleted, entity.deleteExtDataMethod);
    }

    private OiomtStdAcceptCondSet toEntity(StdAcceptCondSet domain) {
        return new OiomtStdAcceptCondSet(domain.getVersion(), new OiomtStdAcceptCondSetPk(domain.getCid(), domain.getConditionSetCd()), domain.getCategoryId(), domain.getCsvDataLineNumber(), domain.getSystemType(), domain.getDeleteExistData(), domain.getCsvDataStartLine(), domain.getAcceptMode(), domain.getConditionSetName(), domain.getCheckCompleted(), domain.getDeleteExtDataMethod());
    }

    private static final String SELECT_BY_SYS_TYPE = SELECT_ALL_QUERY_STRING + " WHERE  f.systemType =:systemType ";
    @Override
    public List<StdAcceptCondSet> getStdAcceptCondSetBySysType(int systemType){
    	return this.queryProxy().query(SELECT_BY_SYS_TYPE, OiomtStdAcceptCondSet.class)
    			.setParameter("systemType", systemType)
                .getList(item -> toDomain(item));
    }
}
