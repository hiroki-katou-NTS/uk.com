package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.KrcmtSpecialLeaveReam;

@Stateless
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
						x.deadlineDate, x.expStatus, x.registerType, x.numberDayGrant, x.timeGrant, x.numberDayUse,
						x.timeUse, x.useSavingDays, x.numberOverDays, x.timeOver, x.numberDayRemain,
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
		entity.numberDayGrant = data.getDetails().getGrantNumber().getDayNumberOfGrant().v();
		entity.timeGrant = data.getDetails().getGrantNumber().getTimeOfGrant().isPresent()
				? data.getDetails().getGrantNumber().getTimeOfGrant().get().v()
				: null;
		entity.numberDayRemain = data.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
		entity.timeRemain = data.getDetails().getRemainingNumber().getTimeOfRemain().isPresent()
				? data.getDetails().getRemainingNumber().getTimeOfRemain().get().v()
				: null;
		entity.numberDayUse = data.getDetails().getUsedNumber().getDayNumberOfUse().v();
		entity.timeUse = data.getDetails().getUsedNumber().getTimeOfUse().isPresent()
				? data.getDetails().getUsedNumber().getTimeOfUse().get().v()
				: null;
		entity.useSavingDays = data.getDetails().getUsedNumber().getUseSavingDays().isPresent()
				? data.getDetails().getUsedNumber().getUseSavingDays().get().v()
				: null;
		entity.numberOverDays = data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent()
				? data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getNumberOverDays().v()
				: null;
		entity.timeOver = (data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent() && data
				.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver().isPresent())
						? data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver()
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
		return SpecialLeaveGrantRemainingData.createFromJavaType(e.key.specialLeaID,e.employeeId, e.specialLeaCode,
				e.grantDate, e.deadlineDate, 
				e.expStatus, e.registerType,
				e.numberDayGrant, e.timeGrant, 
				e.numberDayUse, e.timeUse, 
				e.useSavingDays, e.numberOverDays,
				e.timeOver, e.numberDayRemain, e.timeRemain);
	}

}
