package nts.uk.ctx.pr.proto.infra.repository.personalinfo.employmentcontract;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.ListUtil;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContract;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContractRepository;
import nts.uk.ctx.pr.proto.infra.entity.personalinfo.employmentcontract.PclmtPersonEmpContract;

@RequestScoped
public class JpaPersonalEmploymentContractRepository extends JpaRepository
		implements PersonalEmploymentContractRepository {
	private final String SELECT_BY_CCD_PID_STRD_ENDD = "SELECT c FROM PclmtPersonEmpContract"
			+ " WHERE c.pclmtPersonEmpContractPK.ccd = :ccd" + " and c.pclmtPersonEmpContractPK.pId IN :pIds"
			+ " and c.pclmtPersonEmpContractPK.strD <= :baseYmd" + " and c.endD >= :baseYmd";

	@Override
	public List<PersonalEmploymentContract> findAll(String companyCode, List<String> personIds, LocalDate baseYmd) {
		List<PersonalEmploymentContract> results = new ArrayList<>();
		ListUtil.split(personIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, personIdList -> {
			this.queryProxy().query(SELECT_BY_CCD_PID_STRD_ENDD, PclmtPersonEmpContract.class)
			.setParameter("ccd", companyCode).setParameter("pIds", personIdList).setParameter("baseYmd", baseYmd)
			.getList().stream().forEach(e -> results.add(toDomain(e)));
		});
		return results;
	}

	private static PersonalEmploymentContract toDomain(PclmtPersonEmpContract entity) {
		val domain = PersonalEmploymentContract.createFromJavaType(entity.payrollSystem,
				entity.pclmtPersonEmpContractPK.pId, entity.pclmtPersonEmpContractPK.pId,
				GeneralDate.localDate(entity.pclmtPersonEmpContractPK.strD), GeneralDate.localDate(entity.endD));
		entity.toDomain(domain);
		return domain;
	}

	@Override
	public Optional<PersonalEmploymentContract> find(String companyCode, String personId, LocalDate baseYmd) {
		// TODO Auto-generated method stub
		return null;
	}

}
