package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.bansamedayholiday;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_ALCHK_BAN_HD_TOGETHER")
public class KscmtAlchkBanHdBanTogether extends ContractUkJpaEntity{
	
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
	
	@OneToMany(targetEntity = KscmtAlchkBanHdTogetherDtl.class, mappedBy = "kscmtAlchkBanHdBanTogether", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "KSCMT_ALCHK_BAN_HD_TOGETHER_DTL")
	public List<KscmtAlchkBanHdTogetherDtl> banHdTogetherDtls;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
//	public BanHolidayTogether toDomain() {
//		return BanHolidayTogether.create(
//				new TargetOrgIdenInfor(EnumAdaptor.valueOf(pk.targetUnit, TargetOrganizationUnit.class), Optional.ofNullable(pk.targetId), Optional.ofNullable(pk.targetId)),
//				new BanHolidayTogetherCode(pk.code), 
//				new BanHolidayTogetherName(name),
//				Optional.ofNullable(value)
//				workDayReference, empsCanNotSameHolidays, minNumberOfEmployeeToWork)
//				(pk.position, new ShiftCombinationName(positionName),
//				cmpCombiDtls.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
//	}
}
