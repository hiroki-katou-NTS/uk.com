package nts.uk.ctx.pr.report.dom.payment.comparing.repository;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.masterpage.PersonInfo;
import nts.uk.ctx.pr.report.dom.payment.comparing.masterpage.PersonInfoRepository;

@Stateless
public class JpaPersonInfoRepositoryRepository extends JpaRepository implements PersonInfoRepository {
	private final String SELECT_PERSON_INFO = "SELECT pc.pcpmtPersonComPK.ccd, pd.nameOfficial as personName FROM PcpmtPersonCom pc, PbsmtPersonBase pb"
			+ " WHERE pc.pcpmtPersonComPK.pid = pb.pid AND pc.pcpmtPersonComPK.ccd = :ccd";

	@Override
	public List<PersonInfo> getPersonInfo(String companyCode) {
		return this.queryProxy().query(SELECT_PERSON_INFO, Object[].class).setParameter("ccd", companyCode)
				.getList(p -> {
					String employeeCode = p[0].toString();
					String employeeName = p[1].toString();
					return PersonInfo.createFromJavaType(employeeCode, employeeName);
				});
	}

}
