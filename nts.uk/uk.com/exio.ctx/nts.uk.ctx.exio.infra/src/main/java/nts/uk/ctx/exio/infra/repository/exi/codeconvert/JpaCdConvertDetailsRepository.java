package nts.uk.ctx.exio.infra.repository.exi.codeconvert;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exi.codeconvert.OiomtCdConvertDetails;
import nts.uk.ctx.exio.infra.entity.exi.codeconvert.OiomtCdConvertDetailsPk;
import nts.uk.ctx.exio.dom.exi.codeconvert.CdConvertDetailsRepository;
import nts.uk.ctx.exio.dom.exi.codeconvert.CdConvertDetails;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaCdConvertDetailsRepository extends JpaRepository implements CdConvertDetailsRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtCdConvertDetails f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.cdConvertDetailsPk.cid =:cid AND  f.cdConvertDetailsPk.convertCd =:convertCd AND  f.cdConvertDetailsPk.lineNumber =:lineNumber ";

    @Override
    public List<CdConvertDetails> getAllCdConvertDetails(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtCdConvertDetails.class)
                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<CdConvertDetails> getCdConvertDetailsById(String cid, String convertCd, int lineNumber){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtCdConvertDetails.class)
        .setParameter("cid", cid)
        .setParameter("convertCd", convertCd)
        .setParameter("lineNumber", lineNumber)
        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(CdConvertDetails domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(CdConvertDetails domain){
        OiomtCdConvertDetails newCdConvertDetails = toEntity(domain);
        OiomtCdConvertDetails updateCdConvertDetails = this.queryProxy().find(newCdConvertDetails.cdConvertDetailsPk, OiomtCdConvertDetails.class).get();
        if (null == updateCdConvertDetails) {
            return;
        }
        updateCdConvertDetails.version = newCdConvertDetails.version;
        updateCdConvertDetails.outputItem = newCdConvertDetails.outputItem;
        updateCdConvertDetails.systemCd = newCdConvertDetails.systemCd;
        this.commandProxy().update(updateCdConvertDetails);
    }

    @Override
    public void remove(String cid, String convertCd, int lineNumber){
        this.commandProxy().remove(OiomtCdConvertDetailsPk.class, new OiomtCdConvertDetailsPk(cid, convertCd, lineNumber)); 
    }

    private static CdConvertDetails toDomain(OiomtCdConvertDetails entity) {
        return CdConvertDetails.createFromJavaType(entity.version, entity.cdConvertDetailsPk.cid, entity.cdConvertDetailsPk.convertCd, entity.cdConvertDetailsPk.lineNumber, entity.outputItem, entity.systemCd);
    }

    private OiomtCdConvertDetails toEntity(CdConvertDetails domain) {
        return new OiomtCdConvertDetails(domain.getVersion(), new OiomtCdConvertDetailsPk(domain.getCid(), domain.getConvertCd(), domain.getLineNumber()), domain.getOutputItem(), domain.getSystemCd());
    }

}
