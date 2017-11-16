package nts.uk.ctx.at.record.infra.repository.affiliationinformation;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.infra.entity.affiliationinformation.KrcdtDaiAffiliationInf;
import nts.uk.ctx.at.record.infra.entity.affiliationinformation.KrcdtDaiAffiliationInfPK;

@Stateless
public class JpaAffiliationInforOfDailyPerforRepository extends JpaRepository
		implements AffiliationInforOfDailyPerforRepository {

	private static final String REMOVE_BY_EMPLOYEE;
	
	private static final String DEL_BY_LIST_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiAffiliationInf a ");
		builderString.append("WHERE a.krcdtDaiAffiliationInfPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiAffiliationInfPK.ymd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiAffiliationInf a ");
		builderString.append("WHERE WHERE a.krcdtDaiAffiliationInfPK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcdtDaiAffiliationInfPK.ymd IN :ymds ");
		DEL_BY_LIST_KEY = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createNamedQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).executeUpdate();
	}

	@Override
	public void add(AffiliationInforOfDailyPerfor affiliationInforOfDailyPerfor) {
		this.commandProxy().insert(toEntity(affiliationInforOfDailyPerfor));
	}

	private KrcdtDaiAffiliationInf toEntity(AffiliationInforOfDailyPerfor affiliationInforOfDailyPerfor) {
		val entity = new KrcdtDaiAffiliationInf();

		entity.krcdtDaiAffiliationInfPK = new KrcdtDaiAffiliationInfPK();
		entity.krcdtDaiAffiliationInfPK.employeeId = affiliationInforOfDailyPerfor.getEmployeeId();
		entity.krcdtDaiAffiliationInfPK.ymd = affiliationInforOfDailyPerfor.getYmd();
		entity.bonusPayCode = affiliationInforOfDailyPerfor.getBonusPaySettingCode().v();
		entity.classificationCode = affiliationInforOfDailyPerfor.getClsCode().v();
		entity.employmentCode = affiliationInforOfDailyPerfor.getEmploymentCode().v();
		entity.jobtitleID = affiliationInforOfDailyPerfor.getJobTitleID();
		entity.workplaceID = affiliationInforOfDailyPerfor.getWplID();

		return entity;
	}

	@Override
	public void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		this.getEntityManager().createQuery(DEL_BY_LIST_KEY).setParameter("employeeIds", employeeIds)
		.setParameter("ymds", ymds).executeUpdate();
	}
}
