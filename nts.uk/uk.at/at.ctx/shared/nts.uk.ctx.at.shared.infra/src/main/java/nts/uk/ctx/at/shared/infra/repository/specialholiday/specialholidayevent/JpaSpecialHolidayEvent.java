package nts.uk.ctx.at.shared.infra.repository.specialholiday.specialholidayevent;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.specialholiday.GenderAtr;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.ClassificationList;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.EmploymentList;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.FixedDayGrant;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEvent;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEventRepository;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition.AgeRange;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent.KshstClassificationList;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent.KshstEmploymentList;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent.KshstSpecialHolidayEvent;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent.KshstSpecialHolidayEventPK;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class JpaSpecialHolidayEvent extends JpaRepository implements SpecialHolidayEventRepository {

	private static final String FIND_EMP_LIST_QUERY;
	private static final String FIND_CLS_LIST_QUERY;
	private static final String FIND_BY_NO_LIST_QUERY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstEmploymentList e");
		builderString.append(" WHERE e.pk.companyId = :companyId");
		builderString.append(" AND e.pk.specialHolidayEventNo= :SHENo");
		FIND_EMP_LIST_QUERY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstClassificationList c");
		builderString.append(" WHERE c.pk.companyId = :companyId");
		builderString.append(" AND c.pk.specialHolidayEventNo= :SHENo");
		FIND_CLS_LIST_QUERY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstClassificationList c");
		builderString.append(" WHERE c.pk.companyId = :companyId");
		builderString.append(" AND c.pk.specialHolidayEventNo IN :SHENos");
		FIND_BY_NO_LIST_QUERY = builderString.toString();

	}

	@Override
	public List<SpecialHolidayEvent> findByCompanyIdAndNoLst(String companyId, List<Integer> sHsNo) {
		return this.queryProxy().query(FIND_BY_NO_LIST_QUERY, KshstSpecialHolidayEvent.class)
				.setParameter("companyId", companyId).setParameter("SHENo", sHsNo).getList(c -> toDomain(c));
	}

	@Override
	public Optional<SpecialHolidayEvent> findByKey(String companyId, int eventNo) {
		return this.queryProxy()
				.find(new KshstSpecialHolidayEventPK(companyId, eventNo), KshstSpecialHolidayEvent.class)
				.map(x -> toDomain(x));
	}

	private SpecialHolidayEvent toDomain(KshstSpecialHolidayEvent entity) {
		return new SpecialHolidayEvent(entity.pk.companyId, entity.pk.specialHolidayEventNo, entity.limitFixedDays,
				entity.refRelationShip, new FixedDayGrant(entity.fixedDayGrant),
				EnumAdaptor.valueOf(entity.makeInvitation, UseAtr.class),
				EnumAdaptor.valueOf(entity.includeHolidays, UseAtr.class),
				EnumAdaptor.valueOf(entity.ageLimit, UseAtr.class),
				EnumAdaptor.valueOf(entity.genderRestrict, UseAtr.class),
				EnumAdaptor.valueOf(entity.restrictEmployment, UseAtr.class),
				EnumAdaptor.valueOf(entity.restrictClassification, UseAtr.class),
				EnumAdaptor.valueOf(entity.gender, GenderAtr.class),
				new AgeRange(entity.ageRangeLowerLimit, entity.ageRangeHigherLimit), entity.ageStandardYear,
				entity.ageStandardBaseDate, new Memo(entity.memo),
				getClsList(entity.pk.companyId, entity.pk.specialHolidayEventNo),
				getEmpList(entity.pk.companyId, entity.pk.specialHolidayEventNo));
	}

	private List<EmploymentList> getEmpList(String companyId, int SHENo) {
		return this.queryProxy().query(FIND_EMP_LIST_QUERY, KshstEmploymentList.class)
				.setParameter("companyId", companyId).setParameter("SHENo", SHENo).getList(c -> toEmpList(c));
	}

	private EmploymentList toEmpList(KshstEmploymentList entity) {
		return new EmploymentList(entity.pk.companyId, entity.pk.specialHolidayEventNo,
				new EmploymentCode(entity.pk.employmentCd));
	}

	private List<ClassificationList> getClsList(String companyId, int SHENo) {
		return this.queryProxy().query(FIND_CLS_LIST_QUERY, KshstClassificationList.class)
				.setParameter("companyId", companyId).setParameter("SHENo", SHENo).getList(c -> toClsList(c));
	}

	private ClassificationList toClsList(KshstClassificationList entity) {
		return new ClassificationList(entity.pk.companyId, entity.pk.specialHolidayEventNo, entity.pk.classificationCd);
	}

}
