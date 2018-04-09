package nts.uk.ctx.sys.gateway.infra.repository.mail;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.mail.UrlTaskIncreRepository;
import nts.uk.ctx.sys.gateway.infra.entity.mail.SgwmtUrlTaskIncre;
import nts.uk.ctx.sys.gateway.infra.entity.mail.SgwmtUrlTaskIncrePk;
import nts.uk.shr.com.url.UrlTaskIncre;

@Stateless
public class JpaUrlTaskIncreRepository extends JpaRepository 
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SgwmtUrlTaskIncre f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.urlTaskIncrePk.embeddedId =:embeddedId AND  f.urlTaskIncrePk.cid =:cid AND  f.urlTaskIncrePk.taskIncreId =:taskIncreId ";


    public List<UrlTaskIncre> getAllUrlTaskIncre(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SgwmtUrlTaskIncre.class)
                .getList(item -> item.toDomain());
    }


    public Optional<UrlTaskIncre> getUrlTaskIncreById(String embeddedId, String cid, String taskIncreId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, SgwmtUrlTaskIncre.class)
        .setParameter("embeddedId", embeddedId)
        .setParameter("cid", cid)
        .setParameter("taskIncreId", taskIncreId)
        .getSingle(c->c.toDomain());
    }

    public void add(UrlTaskIncre domain){
    	
		SgwmtUrlTaskIncre xxx = new SgwmtUrlTaskIncre(new SgwmtUrlTaskIncrePk("1", "2", "3"), "4", "5");
        this.commandProxy().insert(xxx);
    }

    public void update(UrlTaskIncre domain){
        this.commandProxy().update(SgwmtUrlTaskIncre.toEntity(domain));
    }


    public void remove(String embeddedId, String cid, String taskIncreId){
        this.commandProxy().remove(SgwmtUrlTaskIncre.class, new SgwmtUrlTaskIncrePk(embeddedId, cid, taskIncreId)); 
    }
}
