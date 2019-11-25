package nts.uk.ctx.pr.core.infra.repository.laborinsurance.laborinsuranceoffice;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.PublicEmploymentSecurityOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.PublicEmploymentSecurityOfficeRepository;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.laborinsuranceoffice.QpbmtPubEmpSecOff;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.laborinsuranceoffice.QpbmtPubEmpSecOffPk;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaPublicEmploymentSecurityOfficeRepository extends JpaRepository
		implements PublicEmploymentSecurityOfficeRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPubEmpSecOff f";
	private static final String SELECT_BY_CID_AND_CODES = SELECT_ALL_QUERY_STRING
			+ " WHERE f.qpbmtPubEmpSecOffPk.cid = :cid AND f.qpbmtPubEmpSecOffPk.pubEmpSecOffCode IN :codes";

	@Override
	public List<PublicEmploymentSecurityOffice> getPublicEmploymentSecurityOfficeByCompany() {
		return null;
	}

	@Override
	public Optional<PublicEmploymentSecurityOffice> getPublicEmploymentSecurityOfficeById(String cid,
			String pubEmpSecOfficeCode) {
		return this.queryProxy().find(new QpbmtPubEmpSecOffPk(cid, pubEmpSecOfficeCode), QpbmtPubEmpSecOff.class)
				.map(r -> r.toDomain());
	}

	@Override
	public void add(PublicEmploymentSecurityOffice domain) {

	}

	@Override
	public void update(PublicEmploymentSecurityOffice domain) {

	}

	@Override
	public void remove(String pubEmpSecOfficeCode) {

	}

	@Override
	public List<PublicEmploymentSecurityOffice> getByCidAndCodes(String cid, List<String> codes) {
		List<PublicEmploymentSecurityOffice> result = new ArrayList<>();
		if (codes == null || codes.isEmpty())
			return result;
		CollectionUtil.split(codes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT,
				codeList -> result.addAll(this.queryProxy().query(SELECT_BY_CID_AND_CODES, QpbmtPubEmpSecOff.class)
						.setParameter("cid", cid).setParameter("codes", codes).getList(QpbmtPubEmpSecOff::toDomain)));
		return result;
	}
}
