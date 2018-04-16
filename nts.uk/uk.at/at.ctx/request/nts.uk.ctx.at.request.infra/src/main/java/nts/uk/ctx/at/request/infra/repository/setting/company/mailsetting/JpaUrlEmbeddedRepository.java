package nts.uk.ctx.at.request.infra.repository.setting.company.mailsetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbedded;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbeddedRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.mailsetting.mailcontenturlsetting.KrqstUrlEmbedded;
import nts.uk.ctx.at.request.infra.entity.setting.company.mailsetting.mailcontenturlsetting.KrqstUrlEmbeddedPk;

@Stateless
public class JpaUrlEmbeddedRepository extends JpaRepository implements UrlEmbeddedRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrqstUrlEmbedded f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.urlEmbeddedPk.cid =:cid ";

    @Override
    public List<UrlEmbedded> getAllUrlEmbedded(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KrqstUrlEmbedded.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<UrlEmbedded> getUrlEmbeddedById(String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, KrqstUrlEmbedded.class)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(UrlEmbedded domain){
        this.commandProxy().insert(KrqstUrlEmbedded.toEntity(domain));
    }

    @Override
    public void update(UrlEmbedded domain){
        this.commandProxy().update(KrqstUrlEmbedded.toEntity(domain));
    }

    @Override
    public void remove(String cid){
        this.commandProxy().remove(KrqstUrlEmbedded.class, new KrqstUrlEmbeddedPk(cid)); 
    }
}
