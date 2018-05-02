package nts.uk.ctx.sys.assist.infra.repository.storage;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageMng;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageMngRepository;
import nts.uk.ctx.sys.assist.infra.entity.storage.SspmtDataStorageMng;

@Stateless
public class JpaDataStorageMngRepository extends JpaRepository implements DataStorageMngRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspmtDataStorageMng f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.storeProcessingId =:storeProcessingId ";

    @Override
    public List<DataStorageMng> getAllDataStorageMng(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspmtDataStorageMng.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<DataStorageMng> getDataStorageMngById(String storeProcessingId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, SspmtDataStorageMng.class)
        .setParameter("storeProcessingId", storeProcessingId)
        .getSingle(c->c.toDomain());
    }
}
