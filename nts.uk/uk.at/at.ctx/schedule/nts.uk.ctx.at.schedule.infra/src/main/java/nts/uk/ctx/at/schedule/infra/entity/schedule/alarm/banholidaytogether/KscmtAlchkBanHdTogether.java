package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.banholidaytogether;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogetherCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogetherName;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.MinNumberEmployeeTogether;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.BusinessDaysCalendarType;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendar;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendarClass;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendarCompany;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendarWorkplace;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_ALCHK_BAN_HD_TOGETHER")
public class KscmtAlchkBanHdTogether extends ContractUkJpaEntity{
	
	public static final JpaEntityMapper<KscmtAlchkBanHdTogether> MAPPER = new JpaEntityMapper<>(KscmtAlchkBanHdTogether.class);
	
	@EmbeddedId
	public KscmtAlchkBanHdBanTogetherPk pk;
	
	/** 名称 */
	@Column(name = "NAME")
	public String name;

	/** 最低限出勤すべき人数 */
	@Column(name = "LOWER_LIMIT")
	public int lowerLimit;

	/** 稼働日の参照先 */
	@Column(name = "REF_CALENDAR_ATR")
	public Integer refCaledarAtr;
	
	/** 稼働日の参照先.職場ID */
	@Column(name = "REF_CALENDAR_WKPID")
	public String refCalendarWkpId;

	/** 稼働日の参照先.分類コード */
	@Column(name = "REF_CALENDAR_CLSCD")
	public String refCalendarClsCd;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public static KscmtAlchkBanHdTogether fromDomain(String cid, BanHolidayTogether domain) {
		val entity = new KscmtAlchkBanHdTogether();		
		entity.pk = KscmtAlchkBanHdBanTogetherPk.fromDomain(cid, domain);
		entity.name = domain.getBanHolidayTogetherName().v();
		entity.lowerLimit = domain.getMinOfWorkingEmpTogether().v();
		
		if (domain.getWorkDayReference().isPresent()) {
			val workDayRef = domain.getWorkDayReference().get();
			entity.refCaledarAtr = workDayRef.getBusinessDaysCalendarType().value;
			switch (workDayRef.getBusinessDaysCalendarType()) {
			case WORKPLACE:
				val workplaceRef = (ReferenceCalendarWorkplace) workDayRef;
				entity.refCalendarWkpId = workplaceRef.getWorkplaceID();
				break;
			case CLASSSICATION:
				val classRef = (ReferenceCalendarClass) workDayRef;
				entity.refCalendarClsCd = classRef.getClassCode().v();
				break;
			default:
				break;
			}
		}
		
		return entity;		
	}
	
	public BanHolidayTogether toDomain(List<KscmtAlchkBanHdTogetherDtl> dltList) {
		return BanHolidayTogether.create(
				TargetOrgIdenInfor.createFromTargetUnit(EnumAdaptor.valueOf(this.pk.targetUnit, TargetOrganizationUnit.class), this.pk.targetId),
				new BanHolidayTogetherCode(this.pk.code), 
				new BanHolidayTogetherName(this.name),
				toReferenCalendar(),
				new MinNumberEmployeeTogether(this.lowerLimit),
				toEmpsCanNotSameHolidays(dltList)
				);
	}
	
	private Optional<ReferenceCalendar> toReferenCalendar(){
		if(this.refCaledarAtr == null) {
			return Optional.empty();
		}
		switch(BusinessDaysCalendarType.of(this.refCaledarAtr)) {
		 
		case COMPANY:
			return Optional.of(new ReferenceCalendarCompany());
		
		case WORKPLACE:
			return Optional.of(new ReferenceCalendarWorkplace(this.refCalendarWkpId));
		
		case CLASSSICATION:
			return Optional.of(new ReferenceCalendarClass(new ClassificationCode(this.refCalendarClsCd)));
		
		default:
			return Optional.empty();
		}
	}
	
	private List<String> toEmpsCanNotSameHolidays(List<KscmtAlchkBanHdTogetherDtl> dltList){
		return dltList.stream().map(c -> { return c.pk.sid;}).collect(Collectors.toList());
		
	}
}
