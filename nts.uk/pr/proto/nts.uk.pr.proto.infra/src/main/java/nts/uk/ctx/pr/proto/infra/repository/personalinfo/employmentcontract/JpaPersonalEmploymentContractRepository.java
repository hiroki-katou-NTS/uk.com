package nts.uk.ctx.pr.proto.infra.repository.personalinfo.employmentcontract;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContract;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContractRepository;
import nts.uk.ctx.pr.proto.infra.entity.personalinfo.employmentcontract.PclmtPersonEmpContract;

@RequestScoped
public class JpaPersonalEmploymentContractRepository extends JpaRepository
		implements PersonalEmploymentContractRepository {
	private final String SELECT_BY_CCD_PID_STRD_ENDD = "SELECT c FROM PclmtPersonEmpContract"
			+ " WHERE c.pclmtPersonEmpContractPK.ccd = :CCD" + " and c.pclmtPersonEmpContractPK.pId IN (:PID)"
			+ " and c.pclmtPersonEmpContractPK.strD <= :BASEYMD" + " and c.endD >= :BASEYMD";

	@Override
	public List<PersonalEmploymentContract> findAll(String companyCode, List<String> personIdList, Date baseYmd) {
		return this.queryProxy().query(SELECT_BY_CCD_PID_STRD_ENDD, PclmtPersonEmpContract.class)
				.setParameter("CCD", companyCode).setParameter("PID", personIdList).setParameter("BASEYMD", baseYmd)
				.getList(c -> toDomain(c));
	}

	private static PersonalEmploymentContract toDomain(PclmtPersonEmpContract entity) {
		val domain = PersonalEmploymentContract.createFromJavaType(entity.payrollSystem,
				entity.pclmtPersonEmpContractPK.pId, entity.pclmtPersonEmpContractPK.pId,
				GeneralDate.legacyDate(entity.pclmtPersonEmpContractPK.strD), GeneralDate.legacyDate(entity.endD));
		entity.toDomain(domain);
		return domain;
	}

	@Override
	public Optional<PersonalEmploymentContract> find(String companyCode, String personId, LocalDate baseYmd) {
		// TODO Auto-generated method stub
		return null;
	}

}
