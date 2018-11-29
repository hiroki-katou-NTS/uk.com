package nts.uk.ctx.exio.infra.repository.exo.cdconvert;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.cdconvert.CdConvertDetail;
import nts.uk.ctx.exio.dom.exo.cdconvert.CdConvertDetailRepository;

@Stateless
public class JpaCdConvertDetailRepository extends JpaRepository implements CdConvertDetailRepository
{

//    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtCdConvertDetail f";
//    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";

    @Override
    public List<CdConvertDetail> getAllCdConvertDetail(){
    	return null;
        /*return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtCdConvertDetail.class)
                .getList(item -> item.toDomain());*/
    }

    @Override
    public Optional<CdConvertDetail> getCdConvertDetailById(){
    	return null;
       /* return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtCdConvertDetail.class)
        .getSingle(c->c.toDomain());*/
    }

    @Override
    public void add(CdConvertDetail domain){
        //this.commandProxy().insert(OiomtCdConvertDetail.toEntity(domain));
    }

    @Override
    public void update(CdConvertDetail domain){
        //this.commandProxy().update(OiomtCdConvertDetail.toEntity(domain));
    }

    @Override
    public void remove(){
        //this.commandProxy().remove(OiomtCdConvertDetail.class, new OiomtCdConvertDetailPk()); 
    }
}
