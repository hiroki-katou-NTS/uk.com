package nts.uk.ctx.sys.gateway.infra.repository.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.mail.UrlExecInfoRepository;
import nts.uk.ctx.sys.gateway.dom.mail.service.RegisterEmbededURLImpl;
import nts.uk.ctx.sys.gateway.infra.entity.mail.SgwmtUrlExecInfo;
import nts.uk.ctx.sys.gateway.infra.entity.mail.SgwmtUrlExecInfoPk;
import nts.uk.ctx.sys.gateway.infra.entity.mail.SgwmtUrlTaskIncre;
import nts.uk.ctx.sys.gateway.infra.entity.mail.SgwmtUrlTaskIncrePk;
import nts.uk.shr.com.url.UrlExecInfo;
import nts.uk.shr.com.url.UrlTaskIncre;

@Stateless
public class JpaUrlExecInfoRepository extends JpaRepository implements UrlExecInfoRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SgwmtUrlExecInfo f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.urlExecInfoPk.embeddedId =:embeddedId AND  f.urlExecInfoPk.cid =:cid ";

    @Override
    public List<UrlExecInfo> getAllUrlExecInfo(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SgwmtUrlExecInfo.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<UrlExecInfo> getUrlExecInfoById(String embeddedId, String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, SgwmtUrlExecInfo.class)
        .setParameter("embeddedId", embeddedId)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(UrlExecInfo domain){
    	SgwmtUrlExecInfo x = SgwmtUrlExecInfo.toEntity(domain);
        this.commandProxy().insert(x);
    }

    @Override
    public void update(UrlExecInfo domain){
        this.commandProxy().update(SgwmtUrlExecInfo.toEntity(domain));
    }

    @Override
    public void remove(String embeddedId, String cid){
        this.commandProxy().remove(SgwmtUrlExecInfo.class, new SgwmtUrlExecInfoPk(embeddedId, cid)); 
    }
    public static void main(String[] args) {
		String embeddedId = UUID.randomUUID().toString();
		String cid = "11";
		String programId = "1";
		String loginId = "1";
		String contractCd = "1";
		GeneralDateTime expiredDate = GeneralDateTime.now();
		GeneralDateTime issueDate = GeneralDateTime.now();
		String screenId = "1";
		String sid = "1";
		String scd = "1";
		List<UrlTaskIncre> taskIncre = new ArrayList<>();
		taskIncre.add(new UrlTaskIncre("1", "1", "1", "1", "1"));
		taskIncre.add(new UrlTaskIncre("2", "2", "2", "2", "2"));
//		UrlExecInfo xxx = new UrlExecInfo(embeddedId, cid, programId, loginId, contractCd, expiredDate, issueDate, screenId, sid, scd, taskIncre);
//		JpaUrlExecInfoRepository x = new JpaUrlExecInfoRepository();
//		x.add(xxx);
		UrlTaskIncre x1 = new UrlTaskIncre("1", "2", "3", "4", "5");
		JpaUrlTaskIncreRepository x = new JpaUrlTaskIncreRepository();
				List<UrlTaskIncre> xx = x.getAllUrlTaskIncre();
	}
}
