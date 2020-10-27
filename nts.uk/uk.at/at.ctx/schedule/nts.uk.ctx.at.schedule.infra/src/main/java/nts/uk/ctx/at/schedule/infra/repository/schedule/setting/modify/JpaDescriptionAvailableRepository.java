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
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.description.KscctScheFuncSya;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.description.KscctScheFuncCommon;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.description.KscctScheFuncDate;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.description.KscctScheFuncShift;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.description.KscctScheFuncWkp;

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
		builderString.append(" FROM KscctScheFuncCommon e");
		builderString.append(" ORDER BY e.displayOrderCom");
		SELECT_BY_COM = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscctScheFuncSya e");
		builderString.append(" ORDER BY e.displayOrderAuth");
		SELECT_BY_AUTH = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscctScheFuncDate e");
		builderString.append(" ORDER BY e.displayOrderDate");
		SELECT_BY_DATE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscctScheFuncShift e");
		builderString.append(" ORDER BY e.displayOrderShift");
		SELECT_BY_SHIFT = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscctScheFuncWkp e");
		builderString.append(" ORDER BY e.displayOrderWork");
		SELECT_BY_WORK = builderString.toString();
	}

	@Override
	public List<ScheduleAuthority> findByAut() {
		return this.queryProxy().query(SELECT_BY_AUTH, KscctScheFuncSya.class)
				.getList(c -> convertToDomainAut(c));
	}

	private ScheduleAuthority convertToDomainAut(KscctScheFuncSya authority) {
		ScheduleAuthority scheduleAuthority = ScheduleAuthority.createFromJavaType(authority.kscctScheFuncSyaPK.functionNoAuth
				, authority.displayOrderAuth
				, authority.displayNameAuth
				, authority.descriptionAuth
				, authority.initialValueAuth);
		return scheduleAuthority;
	}

	@Override
	public List<ScheduleCommon> findByCom() {
		return this.queryProxy().query(SELECT_BY_COM, KscctScheFuncCommon.class)
				.getList(c -> convertToDomainCom(c));
	}
	
	private ScheduleCommon convertToDomainCom(KscctScheFuncCommon common) {
		ScheduleCommon scheduleCommon = ScheduleCommon.createFromJavaType(common.kscctScheFuncCommonPK.functionNoCom
				, common.displayOrderCom
				, common.displayNameCom
				, common.descriptionCom
				, common.initialValueCom);
		return scheduleCommon;
	}

	@Override
	public List<ScheduleDate> findByDate() {
		return this.queryProxy().query(SELECT_BY_DATE, KscctScheFuncDate.class)
				.getList(c -> convertToDomainDate(c));
	}
	
	private ScheduleDate convertToDomainDate(KscctScheFuncDate date) {
		ScheduleDate scheduleDate = ScheduleDate.createFromJavaType(date.kscctScheFuncDatePK.functionNoDate
				, date.displayOrderDate
				, date.displayNameDate
				, date.descriptionDate
				, date.initialValueDate);
		return scheduleDate;
	}

	@Override
	public List<ScheduleShift> findByShift() {
		return this.queryProxy().query(SELECT_BY_SHIFT, KscctScheFuncShift.class)
				.getList(c -> convertToDomainShift(c));
	}
	
	private ScheduleShift convertToDomainShift(KscctScheFuncShift shift) {
		ScheduleShift scheduleShift = ScheduleShift.createFromJavaType(shift.kscctScheFuncShiftPK.functionNoShift
				, shift.displayOrderShift
				, shift.displayNameShift
				, shift.descriptionShift
				, shift.initialValueShift);
		return scheduleShift;
	}

	@Override
	public List<ScheduleWorkplace> findByWork() {
		return this.queryProxy().query(SELECT_BY_WORK, KscctScheFuncWkp.class)
				.getList(c -> convertToDomainWork(c));
	}
	
	private ScheduleWorkplace convertToDomainWork(KscctScheFuncWkp workplace) {
		ScheduleWorkplace scheduleWorkplace = ScheduleWorkplace.createFromJavaType(workplace.kscctScheFuncWkpPK.functionNoWork
				, workplace.displayOrderWork
				, workplace.displayNameWork
				, workplace.descriptionWork
				, workplace.initialValueWork);
		return scheduleWorkplace;
	}


}
