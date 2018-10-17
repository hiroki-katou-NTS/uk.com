package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.KrcmtSpecialLeaveReam;

@Stateless
public class JpaSpecialLeaveGrantRepo extends JpaRepository implements SpecialLeaveGrantRepository {

	private static final String GET_ALL_BY_SID_SPECIALCODE = "SELECT a FROM KrcmtSpecialLeaveReam a WHERE a.employeeId = :employeeId AND a.specialLeaCode = :specialLeaCode order by a.grantDate DESC";

	private static final String QUERY_WITH_SPECIALID = "SELECT a FROM KrcmtSpecialLeaveReam a WHERE a.specialLeaID = :specialLeaId";

	private static final String GET_ALL_BY_SID_SPECIALCODE_STATUS = "SELECT a FROM KrcmtSpecialLeaveReam a WHERE a.employeeId = :employeeId AND a.specialLeaCode = :specialLeaCode AND a.expStatus = :expStatus order by a.grantDate";

	private static final String DELETE_QUERY = "DELETE FROM KrcmtSpecialLeaveReam a" + " WHERE a.specialLeaID = :specialid ";
	
	private static final String GET_BY_PERIOD_STATUS = "SELECT a FROM KrcmtSpecialLeaveReam a"
			+ " WHERE a.employeeId = :employeeId"
			+ " AND a.specialLeaCode = :specialLeaCode"			
			+ " AND a.grantDate <= :grantDate"
			+ " AND a.deadlineDate >= :deadlineDate"
			+ " AND a.expStatus = :expStatus"
			+ " ORDER BY a.grantDate ASC";

	@Override
	public List<SpecialLeaveGrantRemainingData> getAll(String employeeId, int specialCode) {
		List<KrcmtSpecialLeaveReam> entities = this.queryProxy()
				.query(GET_ALL_BY_SID_SPECIALCODE, KrcmtSpecialLeaveReam.class).setParameter("employeeId", employeeId)
				.setParameter("specialLeaCode", specialCode).getList();

		return entities.stream()
				.map(x -> SpecialLeaveGrantRemainingData.createFromJavaType(x.specialLeaID, x.cId, x.employeeId,
						x.specialLeaCode, x.grantDate, x.deadlineDate, x.expStatus, x.registerType, x.numberDayGrant,
						x.timeGrant, x.numberDayUse, x.timeUse, x.useSavingDays, x.numberOverDays, x.timeOver,
						x.numberDayRemain, x.timeRemain))
				.collect(Collectors.toList());
	}

	@Override
	public void add(SpecialLeaveGrantRemainingData data) {
		if (data != null)
			this.commandProxy().insert(toEntity(data));
	}

	@Override
	public void update(SpecialLeaveGrantRemainingData data) {
		if (data != null) {
			Optional<KrcmtSpecialLeaveReam> entityOpt = this.queryProxy().find(data.getSpecialId(),
					KrcmtSpecialLeaveReam.class);
			if (entityOpt.isPresent()) {
				KrcmtSpecialLeaveReam entity = entityOpt.get();
				updateDetail(entity, data);
				this.commandProxy().update(entity);
			}
		}
	}

	@Override
	public void delete(String specialid) {

		Optional<KrcmtSpecialLeaveReam> entityOpt = this.queryProxy().find(specialid, KrcmtSpecialLeaveReam.class);
		if (entityOpt.isPresent()) {
			KrcmtSpecialLeaveReam entity = entityOpt.get();
			this.commandProxy().remove(entity);
		}
	}

	@Override
	public Optional<SpecialLeaveGrantRemainingData> getBySpecialId(String specialId) {

		Optional<SpecialLeaveGrantRemainingData> entity = this.queryProxy()
				.query(QUERY_WITH_SPECIALID, KrcmtSpecialLeaveReam.class).setParameter("specialLeaId", specialId)
				.getSingle(c -> toDomain(c));
		return entity;
	}

	private void updateDetail(KrcmtSpecialLeaveReam entity, SpecialLeaveGrantRemainingData data) {
		entity.employeeId = data.getEmployeeId();
		entity.cId = data.getCId();
		entity.specialLeaCode = data.getSpecialLeaveCode().v();

		entity.grantDate = data.getGrantDate();
		entity.deadlineDate = data.getDeadlineDate();
		entity.expStatus = data.getExpirationStatus().value;
		entity.registerType = data.getRegisterType().value;
		// grant data
		entity.numberDayGrant = data.getDetails().getGrantNumber().getDayNumberOfGrant().v();
		entity.timeGrant = data.getDetails().getGrantNumber().getTimeOfGrant().isPresent()
				? data.getDetails().getGrantNumber().getTimeOfGrant().get().v()
				: 0;
		// remain data
		entity.numberDayRemain = data.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
		entity.timeRemain = data.getDetails().getRemainingNumber().getTimeOfRemain().isPresent()
				? data.getDetails().getRemainingNumber().getTimeOfRemain().get().v()
				: 0;
		// use data
		entity.numberDayUse = data.getDetails().getUsedNumber().getDayNumberOfUse().v();
		entity.timeUse = data.getDetails().getUsedNumber().getTimeOfUse().isPresent()
				? data.getDetails().getUsedNumber().getTimeOfUse().get().v()
				: 0;
		// use Saving data(tai lieu đang bảo truyền null vào)
		// entity.useSavingDays =
		// data.getDetails().getUsedNumber().getUseSavingDays().isPresent()
		// ? data.getDetails().getUsedNumber().getUseSavingDays().get().v()
		// : 0;
		
		// Over
		if (data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent()) {
			entity.numberOverDays = data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get()
					.getNumberOverDays().v();
			entity.timeOver = data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver()
					.isPresent()
							? data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver()
									.get().v()
							: 0;
		}

	}

	/**
	 * Convert to entity
	 * 
	 * @param domain
	 * @return
	 */
	private KrcmtSpecialLeaveReam toEntity(SpecialLeaveGrantRemainingData data) {
		KrcmtSpecialLeaveReam entity = new KrcmtSpecialLeaveReam();
		entity.cId = data.getCId();
		entity.specialLeaID = data.getSpecialId();
		entity.employeeId = data.getEmployeeId();
		entity.specialLeaCode = data.getSpecialLeaveCode().v();

		entity.expStatus = data.getExpirationStatus().value;
		entity.registerType = data.getRegisterType().value;

		entity.grantDate = data.getGrantDate();
		entity.deadlineDate = data.getDeadlineDate();

		// grant data
		entity.numberDayGrant = data.getDetails().getGrantNumber().getDayNumberOfGrant().v();
		entity.timeGrant = data.getDetails().getGrantNumber().getTimeOfGrant().isPresent()
				? data.getDetails().getGrantNumber().getTimeOfGrant().get().v()
				: 0;
		// remain data
		entity.numberDayRemain = data.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
		entity.timeRemain = data.getDetails().getRemainingNumber().getTimeOfRemain().isPresent()
				? data.getDetails().getRemainingNumber().getTimeOfRemain().get().v()
				: 0;
		// use data
		entity.numberDayUse = data.getDetails().getUsedNumber().getDayNumberOfUse().v();
		entity.timeUse = data.getDetails().getUsedNumber().getTimeOfUse().isPresent()
				? data.getDetails().getUsedNumber().getTimeOfUse().get().v()
				: 0;
		// use Saving data(tai lieu đang bảo truyền null vào)
		// entity.useSavingDays =
		// data.getDetails().getUsedNumber().getUseSavingDays().isPresent()
		// ? data.getDetails().getUsedNumber().getUseSavingDays().get().v()
		// : 0;
		entity.useSavingDays = 0d;
		// Over
		if (data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent()) {
			entity.numberOverDays = data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get()
					.getNumberOverDays().v();
			entity.timeOver = data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver()
					.isPresent()
							? data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver()
									.get().v()
							: 0;
		}

		return entity;
	}

	private SpecialLeaveGrantRemainingData toDomain(KrcmtSpecialLeaveReam e) {
		// TODO Auto-generated method stub
		return SpecialLeaveGrantRemainingData.createFromJavaType(e.specialLeaID, e.cId, e.employeeId, e.specialLeaCode,
				e.grantDate, e.deadlineDate, e.expStatus, e.registerType, e.numberDayGrant, e.timeGrant, e.numberDayUse,
				e.timeUse, e.useSavingDays, e.numberOverDays, e.timeOver, e.numberDayRemain, e.timeRemain);
	}

	@Override
	public List<SpecialLeaveGrantRemainingData> getAllByExpStatus(String employeeId, int specialCode,
			int expirationStatus) {
		List<KrcmtSpecialLeaveReam> entities = this.queryProxy()
				.query(GET_ALL_BY_SID_SPECIALCODE_STATUS, KrcmtSpecialLeaveReam.class)
				.setParameter("employeeId", employeeId).setParameter("specialLeaCode", specialCode)
				.setParameter("expStatus", expirationStatus).getList();

		return entities.stream()
				.map(x -> SpecialLeaveGrantRemainingData.createFromJavaType(x.specialLeaID, x.cId, x.employeeId,
						x.specialLeaCode, x.grantDate, x.deadlineDate, x.expStatus, x.registerType, x.numberDayGrant,
						x.timeGrant, x.numberDayUse, x.timeUse, x.useSavingDays, x.numberOverDays, x.timeOver,
						x.numberDayRemain, x.timeRemain))
				.collect(Collectors.toList());
	}

	@Override
	public List<SpecialLeaveGrantRemainingData> getByPeriodStatus(String sid, int specialLeaveCode,
			LeaveExpirationStatus expirationStatus, GeneralDate ymd) {
		List<KrcmtSpecialLeaveReam> entities = this.queryProxy()
				.query(GET_BY_PERIOD_STATUS, KrcmtSpecialLeaveReam.class)
				.setParameter("employeeId", sid)
				.setParameter("specialLeaCode", specialLeaveCode)
				.setParameter("expStatus", expirationStatus.value)
				.setParameter("grantDate", ymd)
				.setParameter("deadlineDate", ymd)
				.getList();

		return entities.stream()
				.map(x -> toDomainSpe(x))
				.collect(Collectors.toList());
	}
	private SpecialLeaveGrantRemainingData toDomainSpe(KrcmtSpecialLeaveReam x) {
		return SpecialLeaveGrantRemainingData.createFromJavaType(x.specialLeaID, x.cId, x.employeeId,
				x.specialLeaCode, x.grantDate, x.deadlineDate, x.expStatus, x.registerType, x.numberDayGrant,
				x.timeGrant, x.numberDayUse, x.timeUse, x.useSavingDays, x.numberOverDays, x.timeOver,
				x.numberDayRemain, x.timeRemain);
	}

}
