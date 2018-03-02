package nts.uk.ctx.exio.infra.repository.exi.condset;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exi.condset.OiomtAcScreenCondSet;
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
    	return null;
//        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtAcScreenCondSet.class)
//                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<AcScreenCondSet> getAcScreenCondSetById(String cid, String conditionSetCd, int acceptItemNum){
    	return Optional.empty();
//        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtAcScreenCondSet.class)
//        .setParameter("cid", cid)
//        .setParameter("conditionSetCd", conditionSetCd)
//        .setParameter("acceptItemNum", acceptItemNum)
//        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(AcScreenCondSet domain){
//        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(AcScreenCondSet domain){
//        OiomtAcScreenCondSet newAcScreenCondSet = toEntity(domain);
//        OiomtAcScreenCondSet updateAcScreenCondSet = this.queryProxy().find(newAcScreenCondSet.acScreenCondSetPk, OiomtAcScreenCondSet.class).get();
//        if (null == updateAcScreenCondSet) {
//            return;
//        }
//        
//        this.commandProxy().update(updateAcScreenCondSet);
    }

    @Override
    public void remove(String cid, String conditionSetCd, int acceptItemNum){
//        this.commandProxy().remove(OiomtAcScreenCondSetPk.class, new OiomtAcScreenCondSetPk(cid, conditionSetCd, acceptItemNum)); 
    }

}
