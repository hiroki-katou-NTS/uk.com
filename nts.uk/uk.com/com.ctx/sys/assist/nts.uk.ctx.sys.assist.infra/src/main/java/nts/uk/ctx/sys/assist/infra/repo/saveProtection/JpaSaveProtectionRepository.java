package nts.uk.ctx.sys.assist.infra.repo.saveProtection;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.saveProtection.SaveProtection;
import nts.uk.ctx.sys.assist.dom.saveProtection.SaveProtectionRepository;
import nts.uk.ctx.sys.assist.infra.enity.saveProtection.SspmtSaveProtection;
import nts.uk.ctx.sys.assist.infra.enity.saveProtection.SspmtSaveProtectionPk;

@Stateless
public class JpaSaveProtectionRepository extends JpaRepository implements SaveProtectionRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspmtSaveProtection f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";

    @Override
    public List<SaveProtection> getAllSaveProtection(){
        /*return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspmtSaveProtection.class)
                .getList(item -> item.toDomain());*/
    	return null;
    }

    @Override
    public Optional<SaveProtection> getSaveProtectionById(){
        /*return this.queryProxy().query(SELECT_BY_KEY_STRING, SspmtSaveProtection.class)
        .getSingle(c->c.toDomain());*/
    	return null;
    }

    @Override
    public void add(SaveProtection domain){
        //this.commandProxy().insert(SspmtSaveProtection.toEntity(domain));
    }

    @Override
    public void update(SaveProtection domain){
        //this.commandProxy().update(SspmtSaveProtection.toEntity(domain));
    }

    @Override
    public void remove(){
        this.commandProxy().remove(SspmtSaveProtection.class, new SspmtSaveProtectionPk()); 
    }
}
