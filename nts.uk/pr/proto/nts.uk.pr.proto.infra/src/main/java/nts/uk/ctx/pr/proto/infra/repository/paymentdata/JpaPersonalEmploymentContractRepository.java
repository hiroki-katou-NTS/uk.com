package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.proto.dom.paymentdata.PersonalEmploymentContract;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PersonalEmploymentContractRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.PclmtPersonEmpContract;

public class JpaPersonalEmploymentContractRepository extends JpaRepository implements PersonalEmploymentContractRepository {

	@Override
	public List<PersonalEmploymentContract> find(String companyCode, List<String> personIdList, Date baseYmd) {
		List<PersonalEmploymentContract> lstPersonalEmploymentContract = new ArrayList<>();
		for (int i = 0; i < personIdList.size(); i++) {
			Optional<PersonalEmploymentContract> tmpPersonalEmploymentContract = (Optional<PersonalEmploymentContract>) this
					.queryProxy()
					.query("SELECT c FROM PCLMT_PERSON_EMP_CONTRACT c WHERE c.CCD = :CCD and c.PID = :PID and c.STR_D <= :BASEYMD and c.END_D >= :BASEYMD",
							PclmtPersonEmpContract.class)
					.setParameter("CCD", companyCode).setParameter("PID", personIdList.get(i))
					.setParameter("BASEYMD", baseYmd).getSingle(c -> toDomain(c));
			if (tmpPersonalEmploymentContract.isPresent()) {
				lstPersonalEmploymentContract.add(tmpPersonalEmploymentContract.get());
			}
		}
		return lstPersonalEmploymentContract;
	}

	private static PersonalEmploymentContract toDomain(PclmtPersonEmpContract entity) {
		val domain = PersonalEmploymentContract.createFromJavaType(entity.payrollSystem,
				entity.pclmtPersonEmpContractPK.pId, entity.pclmtPersonEmpContractPK.pId,
				GeneralDate.legacyDate(entity.pclmtPersonEmpContractPK.strD), GeneralDate.legacyDate(entity.endD));
		entity.toDomain(domain);
		return domain;
	}

}
