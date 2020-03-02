package nts.uk.ctx.pr.core.infra.repository.socialinsurance.socialinsuranceoffice;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.infra.entity.socialinsurance.socialinsuranceoffice.QpbmtSocialInsuranceOffice;
import nts.uk.ctx.pr.core.infra.entity.socialinsurance.socialinsuranceoffice.QpbmtSocialInsuranceOfficePk;

import javax.ejb.Stateless;

@Stateless
public class JpaSocialInsuranceOfficeRepository extends JpaRepository implements SocialInsuranceOfficeRepository {
	
	public static final String QUERY = "select a from QpbmtSocialInsuranceOffice a where a.socialInsuranceOfficePk.cid = :cid order by a.socialInsuranceOfficePk.code ASC";
	public static final String QUERYCODE = "select a from QpbmtSocialInsuranceOffice a where a.socialInsuranceOfficePk.cid =:cid and a.socialInsuranceOfficePk.code = :code ";
	
	@Override
	public List<SocialInsuranceOffice> findByCid(String cid) {
		return this.queryProxy().query(QUERY, QpbmtSocialInsuranceOffice.class)
				.setParameter("cid", cid)
	       		.getList(c ->c.toDomain(c));
	}

	@Override
	public Optional<SocialInsuranceOffice> findByCodeAndCid(String cid, String code) {
		return this.queryProxy().query(QUERYCODE, QpbmtSocialInsuranceOffice.class)
				.setParameter("cid", cid)
				.setParameter("code", code)
	       		.getSingle(c-> c.toDomain(c));
	}

	@Override
	public void add(SocialInsuranceOffice domain) {
		this.commandProxy().insert(QpbmtSocialInsuranceOffice.toEntity(domain));
		
	}

	@Override
	public void update(SocialInsuranceOffice domain) {
		this.commandProxy().update(QpbmtSocialInsuranceOffice.toEntity(domain));
	}
	
	@Override
	public void remove(String cid, String code) {
		QpbmtSocialInsuranceOfficePk key = new QpbmtSocialInsuranceOfficePk(cid, code);
		this.commandProxy().remove(QpbmtSocialInsuranceOffice.class, key);
	}
	
}
