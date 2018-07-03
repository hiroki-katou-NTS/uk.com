package nts.uk.ctx.exio.infra.repository.exo.outcnddetail;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetail;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailRepository;
import nts.uk.ctx.exio.infra.entity.exo.outcnddetail.OiomtOutCndDetail;
import nts.uk.ctx.exio.infra.entity.exo.outcnddetail.OiomtOutCndDetailPk;

@Stateless
public class JpaOutCndDetailRepository extends JpaRepository implements OutCndDetailRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtOutCndDetail f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.outCndDetailPk.cid =:cid AND  f.outCndDetailPk.companyCndSetCd =:companyCndSetCd ";

    @Override
    public List<OutCndDetail> getAllOutCndDetail(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtOutCndDetail.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<OutCndDetail> getOutCndDetailById(String cid, String companyCndSetCd){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtOutCndDetail.class)
        .setParameter("cid", cid)
        .setParameter("companyCndSetCd", companyCndSetCd)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(OutCndDetail domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(OutCndDetail domain){
        this.commandProxy().update(toEntity(domain));
    }

    @Override
    public void remove(String cid, String companyCndSetCd){
        this.commandProxy().remove(OiomtOutCndDetail.class, new OiomtOutCndDetailPk(cid, companyCndSetCd)); 
    }
    
    public static OiomtOutCndDetail toEntity(OutCndDetail domain){
    	return new OiomtOutCndDetail(domain.getCid(), domain.getCompanyCndSetCd().v(), domain.getExterOutCdnSql().v());
    }
    
    public static OutCndDetail toDomain(OiomtOutCndDetail entity){
    	return new OutCndDetail(entity.outCndDetailPk.cid, entity.outCndDetailPk.companyCndSetCd, entity.exterOutCdnSql);
    }
}
