package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.KrcmtSpecialLeaveReam;
import nts.uk.ctx.at.record.infra.entity.worklocation.KwlmtWorkLocation;

public class JpaSpecialLeaveGrantRepo extends JpaRepository implements SpecialLeaveGrantRepository {

	private String GET_ALL_BY_SID_SPECIALCODE = "SELECT a FROM KrcmtSpecialLeaveReam a WHERE a.employeeId = :employeeId AND a.specialLeaCode = :specialLeaCode order by a.grantDate DESC";

	private String QUERY_WITH_SPECIALID = "SELECT a FROM KrcmtSpecialLeaveReam a WHERE a.key.specialLeaID = :specialLeaId";

	@Override
	public List<SpecialLeaveGrantRemainingData> getAll(String employeeId, int specialCode) {
		List<KrcmtSpecialLeaveReam> entities = this.queryProxy()
				.query(GET_ALL_BY_SID_SPECIALCODE, KrcmtSpecialLeaveReam.class).setParameter("employeeId", employeeId)
				.setParameter("specialLeaCode", specialCode).getList();

		return entities.stream()
				.map(x -> SpecialLeaveGrantRemainingData.createFromJavaType(x.key.specialLeaID,x.employeeId, x.specialLeaCode, x.grantDate,
						x.deadlineDate, x.expStatus, x.registerType, x.numberOfDayGrant, x.timeGrant, x.numberOfDayUse,
						x.timeUse, x.numberOfDayUseToLose, x.numberOfExceededDays, x.timeExceeded, x.numberOfDayRemain,
						x.timeRemain))
				.collect(Collectors.toList());
	}

	@Override
	public void add(SpecialLeaveGrantRemainingData data) {
		KrcmtSpecialLeaveReam entity = new KrcmtSpecialLeaveReam();
		updateDetail(entity, data);
		this.commandProxy().insert(entity);

	}

	@Override
	public void update(SpecialLeaveGrantRemainingData data) {
		// Optional<KrcmtSpecialLeaveReam> entityOpt = this.queryProxy().find(
		// new KrcmtSpecialLeaveReam(key), KrcmtSpecialLeaveReam.class);
		// if (entityOpt.isPresent()) {
		// updateDetail(entityOpt.get(), data);
		// this.commandProxy().update(entityOpt.get());
		// }
	}

	@Override
	public void delete(String employeeId, GeneralDate grantDate) {
		// Optional<KrcmtSpecialLeaveReam> entityOpt = this.queryProxy()
		// .find(new KrcmtSpecialLeaveReam(key), KrcmtSpecialLeaveReam.class);
		// if (entityOpt.isPresent()) {
		// this.commandProxy().remove(entityOpt.get());
		// }

	}

	private void updateDetail(KrcmtSpecialLeaveReam entity, SpecialLeaveGrantRemainingData data) {
		entity.key.specialLeaID = data.getSpecialId();
		entity.employeeId = data.getEmployeeId();
		entity.specialLeaCode = data.getSpecialLeaveCode().v();
		entity.grantDate = data.getGrantDate();
		entity.deadlineDate = data.getDeadlineDate();
		entity.expStatus = data.getExpirationStatus().value;
		entity.registerType = data.getRegisterType().value;
		entity.numberOfDayGrant = data.getDetails().getGrantNumber().getDayNumberOfGrant().v();
		entity.timeGrant = data.getDetails().getGrantNumber().getTimeOfGrant().isPresent()
				? data.getDetails().getGrantNumber().getTimeOfGrant().get().v()
				: null;
		entity.numberOfDayRemain = data.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
		entity.timeRemain = data.getDetails().getRemainingNumber().getTimeOfRemain().isPresent()
				? data.getDetails().getRemainingNumber().getTimeOfRemain().get().v()
				: null;
		entity.numberOfDayUse = data.getDetails().getUsedNumber().getDayNumberOfUse().v();
		entity.timeUse = data.getDetails().getUsedNumber().getTimeOfUse().isPresent()
				? data.getDetails().getUsedNumber().getTimeOfUse().get().v()
				: null;
		entity.numberOfDayUseToLose = data.getDetails().getUsedNumber().getNumberOfDayUseToLose().isPresent()
				? data.getDetails().getUsedNumber().getNumberOfDayUseToLose().get().v()
				: null;
		entity.numberOfExceededDays = data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent()
				? data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getDayNumberOfExeeded().v()
				: null;
		entity.timeExceeded = (data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent() && data
				.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOfExeeded().isPresent())
						? data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOfExeeded()
								.get().v()
						: null;

	}

	@Override
	public Optional<SpecialLeaveGrantRemainingData> getBySpecialId(String specialId) {

		Optional<SpecialLeaveGrantRemainingData> entity = this.queryProxy()
				.query(QUERY_WITH_SPECIALID, KrcmtSpecialLeaveReam.class).setParameter("specialLeaId", specialId)
				.getSingle(c -> toDomain(c));
		return entity;
	}

	private SpecialLeaveGrantRemainingData toDomain(KrcmtSpecialLeaveReam e) {
		// TODO Auto-generated method stub
		return SpecialLeaveGrantRemainingData.createFromJavaType(e.employeeId, e.specialLeaCode,
				e.grantDate, e.deadlineDate, 
				e.expStatus, e.registerType,
				e.numberOfDayGrant, e.timeGrant, 
				e.numberOfDayUse, e.timeUse, 
				e.numberOfDayUseToLose, e.numberOfExceededDays,
				e.timeExceeded, e.numberOfDayRemain, e.timeRemain);
	}

}
