package nts.uk.ctx.at.schedule.infra.repository.schedule.alarm.consecutiveattendance.continuousworktime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.continuousworktime.MaxNumberDaysOfContWorkTimeComRepository;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.continuousworktime.MaxNumberDaysOfContinuousWorkTimeCom;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.continuousworktime.WorkTimeContinuousCode;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.continuousworktime.KscmtAlchkConsecutiveWktmCmp;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.continuousworktime.KscmtAlchkConsecutiveWktmCmpDtl;

/**
 * 
 * @author hiroko_miura
 *
 */
public class JpaMaxNumberDaysOfContWorkTimeComRepository extends JpaRepository implements MaxNumberDaysOfContWorkTimeComRepository {

	private static String SELECT_HEADER_WHERE_CID = "SELECT * FROM KSCMT_ALCHK_CONSECUTIVE_WKTM_CMP"
			+ " WHERE CID = @companyId";
	
	private static String SELECT_HEADER_WHERE_CID_AND_CODE = SELECT_HEADER_WHERE_CID + " AND CD = @code";
	
	private static String SELECT_DETAIL_WHERE_CID = "SELECT * FROM KSCMT_ALCHK_CONSECUTIVE_WKTM_CMP_DTL"
			+ " WHERE CID = @companyId";
	
	private static String SELECT_DETAIL_WHERE_CID_AND_CODE = SELECT_DETAIL_WHERE_CID + " AND CD = @code";
	
	
	@Override
	public void insert(String companyId, MaxNumberDaysOfContinuousWorkTimeCom domain) {
		this.commandProxy().insert(KscmtAlchkConsecutiveWktmCmp.of(companyId, domain));
		this.commandProxy().insertAll(KscmtAlchkConsecutiveWktmCmpDtl.toDetailEntityList(companyId, domain));
	}

	@Override
	public void update(String companyId, MaxNumberDaysOfContinuousWorkTimeCom domain) {
		this.commandProxy().update(KscmtAlchkConsecutiveWktmCmp.of(companyId, domain));
		this.commandProxy().updateAll(KscmtAlchkConsecutiveWktmCmpDtl.toDetailEntityList(companyId, domain));
	}

	@Override
	public void delete(String companyId, WorkTimeContinuousCode code) {
		Optional<MaxNumberDaysOfContinuousWorkTimeCom> domain = this.get(companyId, code);
		
		if (domain.isPresent()) {
			KscmtAlchkConsecutiveWktmCmp entity = KscmtAlchkConsecutiveWktmCmp.of(companyId, domain.get());
			List<KscmtAlchkConsecutiveWktmCmpDtl> dtlEntity = KscmtAlchkConsecutiveWktmCmpDtl.toDetailEntityList(companyId, domain.get());
			this.commandProxy().remove(entity);
			this.commandProxy().removeAll(dtlEntity);
		}
	}

	@Override
	public boolean exists(String companyId, WorkTimeContinuousCode code) {
		return this.get(companyId, code).isPresent();
	}

	@Override
	public Optional<MaxNumberDaysOfContinuousWorkTimeCom> get(String companyId, WorkTimeContinuousCode code) {
		
		Optional<KscmtAlchkConsecutiveWktmCmp> header = new NtsStatement(SELECT_HEADER_WHERE_CID_AND_CODE, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramString("code", code.v())
				.getSingle(x -> KscmtAlchkConsecutiveWktmCmp.MAPPER.toEntity(x));
		
		if (!header.isPresent())
			return Optional.empty();
		
		List<KscmtAlchkConsecutiveWktmCmpDtl> details = new NtsStatement(SELECT_DETAIL_WHERE_CID_AND_CODE, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramString("code", code.v())
				.getList(x -> KscmtAlchkConsecutiveWktmCmpDtl.MAPPER.toEntity(x));
		
		return Optional.of(header.get().toDomain(details));
	}

	@Override
	public List<MaxNumberDaysOfContinuousWorkTimeCom> getAll(String companyId) {
		List<KscmtAlchkConsecutiveWktmCmp> headers = new NtsStatement(SELECT_HEADER_WHERE_CID, this.jdbcProxy())
				.paramString("companyId", companyId)
				.getList(x -> KscmtAlchkConsecutiveWktmCmp.MAPPER.toEntity(x));
		
		if (headers.isEmpty())
			return Collections.emptyList();
		
		List<KscmtAlchkConsecutiveWktmCmpDtl> alldetails = new NtsStatement(SELECT_DETAIL_WHERE_CID, this.jdbcProxy())
				.paramString("companyId", companyId)
				.getList(x -> KscmtAlchkConsecutiveWktmCmpDtl.MAPPER.toEntity(x));
		
		
		return headers.stream().map(head -> {

			List<KscmtAlchkConsecutiveWktmCmpDtl> details = alldetails.stream()
					.filter(d -> d.pk.code == head.pk.code)
					.collect(Collectors.toList());
			
			return head.toDomain(details);
			
		}).collect(Collectors.toList());
	}

}
