package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.workmethodrelationship;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.RelationshipSpecifiedMethod;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethod;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodClassfication;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodContinuousWork;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodHoliday;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationship;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethodRelationshipCom;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KSCMT_ALCHK_WORK_CONTEXT_CMP")
@AllArgsConstructor
public class KscmtAlchkWorkContextCmp  extends ContractUkJpaEntity {
	
	public static final String HOLIDAY_WORK_TIME_CODE = "000";
	
	@EmbeddedId
	public KscmtAlchkWorkContextCmpPk pk;

	@Column(name = "PROHIBIT_ATR")
	public int specifiedMethod;
	
	@Column(name = "CURRENT_WORK_ATR")
	public int currentWorkingMethod;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static Function<NtsResultRecord, KscmtAlchkWorkContextCmp> mapper = s -> 
		new KscmtAlchkWorkContextCmp(
				new KscmtAlchkWorkContextCmpPk(
						s.getString("SID"), 
						s.getInt("PREVIOUS_WORK_ATR"), 
						s.getString("PREVIOUS_WKTM_CD")), 
				s.getInt("PROHIBIT_ATR"), 
				s.getInt("CURRENT_WORK_ATR"));
	
	public static KscmtAlchkWorkContextCmp fromDomain(WorkMethodRelationshipCom domain) {
		
		WorkMethodRelationship relationship = domain.getWorkMethodRelationship();
			
		return new KscmtAlchkWorkContextCmp(
					KscmtAlchkWorkContextCmpPk.fromDomain(domain), 
					relationship.getSpecifiedMethod().value, 
					relationship.getCurrentWorkMethodList().get(0).getWorkMethodClassification().value);
			
	}
	
	public WorkMethodRelationshipCom toDomain(List<KscmtAlchkWorkContextCmpDtl> dtlList) {
		
		return new WorkMethodRelationshipCom(this.pk.companyId, 
				WorkMethodRelationship.create(
						this.toPrevWorkMethod(), 
						this.toCurrentWorkMethod(dtlList), 
						RelationshipSpecifiedMethod.of(this.specifiedMethod)));
	}
	
	private WorkMethod toPrevWorkMethod() {
		
		return this.pk.prevWorkMethod == WorkMethodClassfication.ATTENDANCE.value ?
				new WorkMethodAttendance(new WorkTimeCode(this.pk.prevWorkTimeCode)) : 
					new WorkMethodHoliday();
	}
	
	private List<WorkMethod> toCurrentWorkMethod(List<KscmtAlchkWorkContextCmpDtl> dtlList) {
		
		switch (WorkMethodClassfication.of(this.currentWorkingMethod)) {
			case ATTENDANCE:
				return dtlList.stream().map(dtl -> dtl.toWorkMethodAttendance()).collect(Collectors.toList()); 
			case HOLIDAY:
				return Arrays.asList(new WorkMethodHoliday());
			case CONTINUOSWORK:
			default:
				return Arrays.asList(new WorkMethodContinuousWork());
		}
	}

}
