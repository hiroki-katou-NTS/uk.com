package nts.uk.ctx.at.schedule.infra.repository.employeeinfo.medicalworkstyle;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassifiCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassifiName;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassificationRepository;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.medicalworkstyle.KscmtNurseLicense;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.medicalworkstyle.KscmtNurseLicensePK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@Stateless
public class JpaNurseClassificationRepository extends JpaRepository implements NurseClassificationRepository {

	private static String FIND_WITH_COMPANYID = "select a from KscmtNurseLicense a where a.kscmtNurseLicensePK.companyId = :companyId";

	@Override
	public List<NurseClassification> getListCompanyNurseCategory(String companyId) {
		return this.queryProxy().query(FIND_WITH_COMPANYID, KscmtNurseLicense.class)
				.setParameter("companyId", companyId).getList(x -> toDomain(x));
	}

	@Override
	public Optional<NurseClassification> getSpecifiNurseCategory(String companyId, String code) {
		return this.queryProxy().find(new KscmtNurseLicensePK(companyId, code), KscmtNurseLicense.class)
				.map(x -> toDomain(x));
	}

	@Override
	public void insert(NurseClassification nurseClassification) {
		this.commandProxy().insert(toEntity(nurseClassification));
	}

	@Override
	public void update(NurseClassification nurseClassification) {
		this.commandProxy().update(toEntity(nurseClassification));
	}

	@Override
	public void delete(String companyId, String code) {
		this.commandProxy().remove(KscmtNurseLicense.class, new KscmtNurseLicensePK(companyId, code));

	}

	private NurseClassification toDomain(KscmtNurseLicense ent) {
		return new NurseClassification(new CompanyId(ent.getKscmtNurseLicensePK().getCompanyId()),
				new NurseClassifiCode(ent.getKscmtNurseLicensePK().getCode()), new NurseClassifiName(ent.getName()),
				LicenseClassification.valueOf(ent.getLicenseAtr()), ent.getOfficeWork() == 1);
	}

	private KscmtNurseLicense toEntity(NurseClassification domain) {
		return new KscmtNurseLicense(
				new KscmtNurseLicensePK(domain.getCompanyId().v(), domain.getNurseClassifiCode().v()),
				domain.getNurseClassifiName().v(), domain.getLicense().value, domain.isOfficeWorker() ? 1 : 0);
	}

}
