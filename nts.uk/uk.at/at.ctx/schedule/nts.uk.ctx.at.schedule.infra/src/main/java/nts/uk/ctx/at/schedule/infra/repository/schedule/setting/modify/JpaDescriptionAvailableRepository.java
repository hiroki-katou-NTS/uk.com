package nts.uk.ctx.at.schedule.infra.repository.schedule.setting.modify;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.description.DescriptionRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.description.ScheduleAuthority;
import nts.uk.ctx.at.schedule.dom.schedule.setting.description.ScheduleCommon;
import nts.uk.ctx.at.schedule.dom.schedule.setting.description.ScheduleDate;
import nts.uk.ctx.at.schedule.dom.schedule.setting.description.ScheduleShift;
import nts.uk.ctx.at.schedule.dom.schedule.setting.description.ScheduleWorkplace;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.description.KscstScheduleAuthority;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.description.KscstScheduleCommon;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.description.KscstScheduleDate;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.description.KscstScheduleShift;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.description.KscstScheduleWorkplace;

@Stateless
public class JpaDescriptionAvailableRepository extends JpaRepository implements DescriptionRepository {
	private static final String SELECT_BY_COM;
	private static final String SELECT_BY_AUTH;
	private static final String SELECT_BY_DATE;
	private static final String SELECT_BY_SHIFT;
	private static final String SELECT_BY_WORK;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstScheduleCommon e");
		builderString.append(" ORDER BY e.displayOrderCom");
		SELECT_BY_COM = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstScheduleAuthority e");
		builderString.append(" ORDER BY e.displayOrderAuth");
		SELECT_BY_AUTH = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstScheduleDate e");
		builderString.append(" ORDER BY e.displayOrderDate");
		SELECT_BY_DATE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstScheduleShift e");
		builderString.append(" ORDER BY e.displayOrderShift");
		SELECT_BY_SHIFT = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstScheduleWorkplace e");
		builderString.append(" ORDER BY e.displayOrderWork");
		SELECT_BY_WORK = builderString.toString();
	}

	@Override
	public List<ScheduleAuthority> findByAut() {
		return this.queryProxy().query(SELECT_BY_AUTH, KscstScheduleAuthority.class)
				.getList(c -> convertToDomainAut(c));
	}

	private ScheduleAuthority convertToDomainAut(KscstScheduleAuthority authority) {
		ScheduleAuthority scheduleAuthority = ScheduleAuthority.createFromJavaType(authority.kscstScheduleAuthorityPK.functionNoAuth
				, authority.displayOrderAuth
				, authority.displayNameAuth
				, authority.descriptionAuth
				, authority.initialValueAuth);
		return scheduleAuthority;
	}

	@Override
	public List<ScheduleCommon> findByCom() {
		return this.queryProxy().query(SELECT_BY_COM, KscstScheduleCommon.class)
				.getList(c -> convertToDomainCom(c));
	}
	
	private ScheduleCommon convertToDomainCom(KscstScheduleCommon common) {
		ScheduleCommon scheduleCommon = ScheduleCommon.createFromJavaType(common.kscstScheduleCommonPK.functionNoCom
				, common.displayOrderCom
				, common.displayNameCom
				, common.descriptionCom
				, common.initialValueCom);
		return scheduleCommon;
	}

	@Override
	public List<ScheduleDate> findByDate() {
		return this.queryProxy().query(SELECT_BY_DATE, KscstScheduleDate.class)
				.getList(c -> convertToDomainDate(c));
	}
	
	private ScheduleDate convertToDomainDate(KscstScheduleDate date) {
		ScheduleDate scheduleDate = ScheduleDate.createFromJavaType(date.kscstScheduleDatePK.functionNoDate
				, date.displayOrderDate
				, date.displayNameDate
				, date.descriptionDate
				, date.initialValueDate);
		return scheduleDate;
	}

	@Override
	public List<ScheduleShift> findByShift() {
		return this.queryProxy().query(SELECT_BY_SHIFT, KscstScheduleShift.class)
				.getList(c -> convertToDomainShift(c));
	}
	
	private ScheduleShift convertToDomainShift(KscstScheduleShift shift) {
		ScheduleShift scheduleShift = ScheduleShift.createFromJavaType(shift.kscstScheduleShiftPK.functionNoShift
				, shift.displayOrderShift
				, shift.displayNameShift
				, shift.descriptionShift
				, shift.initialValueShift);
		return scheduleShift;
	}

	@Override
	public List<ScheduleWorkplace> findByWork() {
		return this.queryProxy().query(SELECT_BY_WORK, KscstScheduleWorkplace.class)
				.getList(c -> convertToDomainWork(c));
	}
	
	private ScheduleWorkplace convertToDomainWork(KscstScheduleWorkplace workplace) {
		ScheduleWorkplace scheduleWorkplace = ScheduleWorkplace.createFromJavaType(workplace.kscstScheduleWorkplacePK.functionNoWork
				, workplace.displayOrderWork
				, workplace.displayNameWork
				, workplace.descriptionWork
				, workplace.initialValueWork);
		return scheduleWorkplace;
	}


}
