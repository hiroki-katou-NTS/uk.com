package nts.uk.ctx.pr.report.dom.payment.comparing.repository;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.masterpage.PersonInfo;
import nts.uk.ctx.pr.report.dom.payment.comparing.masterpage.PersonInfoRepository;

@Stateless
public class JpaPersonInfoRepositoryRepository extends JpaRepository implements PersonInfoRepository {
	private final String SELECT_PERSON_INFO = "SELECT  pb.pid as personID, pc.scd as employeerCode, pb.nameOfficial as employeerName FROM PcpmtPersonCom pc, PbsmtPersonBase pb"
			+ " WHERE pc.pcpmtPersonComPK.pid = pb.pid AND pc.pcpmtPersonComPK.ccd = :ccd";

	@Override
	public List<PersonInfo> getPersonInfo(String companyCode) {
		return this.queryProxy().query(SELECT_PERSON_INFO, Object[].class).setParameter("ccd", companyCode)
				.getList(p -> {
					String personID = p[0].toString();
					String employeeCode = p[1].toString();
					String employeeName = p[2].toString();
					return PersonInfo.createFromJavaType(personID, employeeCode, employeeName, "", "");
				});
	}

}
