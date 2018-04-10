package nts.uk.ctx.at.request.infra.repository.setting.company.mailsetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMail;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMailRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.mailsetting.remandsetting.KrqstContentOfRemandMail;
import nts.uk.ctx.at.request.infra.entity.setting.company.mailsetting.remandsetting.KrqstContentOfRemandMailPk;

@Stateless
public class JpaContentOfRemandMailRepository extends JpaRepository implements ContentOfRemandMailRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrqstRemandMail f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.remandMailPk.cid =:cid ";

    @Override
    public List<ContentOfRemandMail> getAllRemandMail(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KrqstContentOfRemandMail.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<ContentOfRemandMail> getRemandMailById(String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, KrqstContentOfRemandMail.class)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(ContentOfRemandMail domain){
        this.commandProxy().insert(KrqstContentOfRemandMail.toEntity(domain));
    }

    @Override
    public void update(ContentOfRemandMail domain){
        this.commandProxy().update(KrqstContentOfRemandMail.toEntity(domain));
    }

    @Override
    public void remove(String cid){
        this.commandProxy().remove(KrqstContentOfRemandMail.class, new KrqstContentOfRemandMailPk(cid)); 
    }
}
