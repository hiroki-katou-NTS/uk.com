package nts.uk.ctx.at.record.infra.entity.monthly.vacation.reserveleave;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RealReserveLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveGrant;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveRemainingDetail;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveUndigestedNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveUsedNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：積立年休月別残数データ
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_RSVLEA_REMAIN")
@NoArgsConstructor
public class KrcdtMonRsvleaRemain extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonRsvleaRemainPK PK;
	
	/** 締め処理状態 */
	@Column(name = "CLOSURE_STATUS")
	public int closureStatus;
	
	/** 開始年月日 */
	@Column(name = "START_DATE")
	public GeneralDate startDate;
	/** 終了年月日 */
	@Column(name = "END_DATE")
	public GeneralDate endDate;
	
	/** 使用日数 */
	@Column(name = "USED_DAYS")
	public double usedDays;
	/** 使用日数付与前 */
	@Column(name = "USED_DAYS_BEFORE")
	public double usedDaysBefore;
	/** 使用日数付与後 */
	@Column(name = "USED_DAYS_AFTER")
	public Double usedDaysAfter;
	
	/** 実使用日数 */
	@Column(name = "FACT_USED_DAYS")
	public double factUsedDays;
	/** 実使用日数付与前 */
	@Column(name = "FACT_USED_DAYS_BEFORE")
	public double factUsedDaysBefore;
	/** 実使用日数付与後 */
	@Column(name = "FACT_USED_DAYS_AFTER")
	public Double factUsedDaysAfter;
	
	/** 合計残日数 */
	@Column(name = "REMAINING_DAYS")
	public double remainingDays;
	/** 実合計残日数 */
	@Column(name = "FACT_REMAINING_DAYS")
	public double factRemainingDays;
	
	/** 合計残日数付与前 */
	@Column(name = "REMAINING_DAYS_BEFORE")
	public double remainingDaysBefore;
	/** 実合計残日数付与前 */
	@Column(name = "FACT_REMAINING_DAYS_BEFORE")
	public double factRemainingDaysBefore;
	
	/** 合計残日数付与後 */
	@Column(name = "REMAINING_DAYS_AFTER")
	public Double remainingDaysAfter;
	/** 実合計残日数付与後 */
	@Column(name = "FACT_REMAINING_DAYS_AFTER")
	public Double factRemainingDaysAfter;
	
	/** 未消化日数 */
	@Column(name = "NOT_USED_DAYS")
	public double notUsedDays;

	/** 付与区分 */
	@Column(name = "GRANT_ATR")
	public int grantAtr;
	/** 付与日数 */
	@Column(name = "GRANT_DAYS")
	public Double grantDays;
	
	/** 積立年休月別残数明細 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonRsvleaRemain", orphanRemoval = true)
	public List<KrcdtMonRsvleaDetail> krcdtMonRsvleaDetails;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return 年休月別残数データ
	 */
	public RsvLeaRemNumEachMonth toDomain(){
		
		// 積立年休月別残数明細を分類する
		List<ReserveLeaveRemainingDetail> normalDetail = new ArrayList<>();
		List<ReserveLeaveRemainingDetail> normalDetailBefore = new ArrayList<>();
		List<ReserveLeaveRemainingDetail> normalDetailAfter = new ArrayList<>();
		List<ReserveLeaveRemainingDetail> realDetail = new ArrayList<>();
		List<ReserveLeaveRemainingDetail> realDetailBefore = new ArrayList<>();
		List<ReserveLeaveRemainingDetail> realDetailAfter = new ArrayList<>();
		if (this.krcdtMonRsvleaDetails != null){
			this.krcdtMonRsvleaDetails.sort((a, b) -> a.PK.grantDate.compareTo(b.PK.grantDate));
			for (val detail : this.krcdtMonRsvleaDetails){
				switch (detail.PK.remainingType){
				case 0:		// 残数
					normalDetail.add(detail.toDomainForNormal());
					realDetail.add(detail.toDomainForReal());
					break;
				case 1:		// 付与前残数
					normalDetailBefore.add(detail.toDomainForNormal());
					realDetailBefore.add(detail.toDomainForReal());
					break;
				case 2:		// 付与後残数
					normalDetailAfter.add(detail.toDomainForNormal());
					realDetailAfter.add(detail.toDomainForReal());
					break;
				}
			}
		}
		
		// 積立年休：残数付与後
		ReserveLeaveRemainingNumber valRemainAfter = null;
		if (this.remainingDaysAfter != null){
			valRemainAfter = ReserveLeaveRemainingNumber.of(
					new ReserveLeaveRemainingDayNumber(this.remainingDaysAfter),
					normalDetailAfter);
		}
		
		// 積立年休
		ReserveLeaveUsedDayNumber valUsedDaysAfter = null;
		if (this.usedDaysAfter != null){
			valUsedDaysAfter = new ReserveLeaveUsedDayNumber(this.usedDaysAfter);
		}
		ReserveLeave reserveLeave = ReserveLeave.of(
				ReserveLeaveUsedNumber.of(
						new ReserveLeaveUsedDayNumber(this.usedDays),
						new ReserveLeaveUsedDayNumber(this.usedDaysBefore),
						Optional.ofNullable(valUsedDaysAfter)),
				ReserveLeaveRemainingNumber.of(
						new ReserveLeaveRemainingDayNumber(this.remainingDays),
						normalDetail),
				ReserveLeaveRemainingNumber.of(
						new ReserveLeaveRemainingDayNumber(this.remainingDaysBefore),
						normalDetailBefore),
				Optional.ofNullable(valRemainAfter),
				ReserveLeaveUndigestedNumber.of(
						new ReserveLeaveRemainingDayNumber(this.notUsedDays)));

		// 実積立年休：残数付与後
		ReserveLeaveRemainingNumber valFactRemainAfter = null;
		if (this.factRemainingDaysAfter != null){
			valFactRemainAfter = ReserveLeaveRemainingNumber.of(
					new ReserveLeaveRemainingDayNumber(this.factRemainingDaysAfter),
					realDetailAfter);
		}
		
		// 実積立年休
		ReserveLeaveUsedDayNumber valFactUsedDaysAfter = null;
		if (this.factUsedDaysAfter != null){
			valFactUsedDaysAfter = new ReserveLeaveUsedDayNumber(this.factUsedDaysAfter);
		}
		RealReserveLeave realReserveLeave = RealReserveLeave.of(
				ReserveLeaveUsedNumber.of(
						new ReserveLeaveUsedDayNumber(this.factUsedDays),
						new ReserveLeaveUsedDayNumber(this.factUsedDaysBefore),
						Optional.ofNullable(valFactUsedDaysAfter)),
				ReserveLeaveRemainingNumber.of(
						new ReserveLeaveRemainingDayNumber(this.factRemainingDays),
						realDetail),
				ReserveLeaveRemainingNumber.of(
						new ReserveLeaveRemainingDayNumber(this.factRemainingDaysBefore),
						realDetailBefore),
				Optional.ofNullable(valFactRemainAfter));
		
		// 積立年休付与情報
		ReserveLeaveGrant reserveLeaveGrant = null;
		if (this.grantDays != null){
			reserveLeaveGrant = ReserveLeaveGrant.of(
					new ReserveLeaveGrantDayNumber(this.grantDays));
		}
		
		return RsvLeaRemNumEachMonth.of(
				this.PK.employeeId,
				new YearMonth(this.PK.yearMonth),
				EnumAdaptor.valueOf(this.PK.closureId, ClosureId.class),
				new ClosureDate(this.PK.closureDay, (this.PK.isLastDay != 0)),
				new DatePeriod(this.startDate, this.endDate),
				EnumAdaptor.valueOf(this.closureStatus, ClosureStatus.class),
				reserveLeave,
				realReserveLeave,
				Optional.ofNullable(reserveLeaveGrant),
				(this.grantAtr != 0));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param domain 年休月別残数データ
	 */
	public void fromDomainForPersist(RsvLeaRemNumEachMonth domain){
		
		this.PK = new KrcdtMonRsvleaRemainPK(
				domain.getEmployeeId(),
				domain.getYearMonth().v(),
				domain.getClosureId().value,
				domain.getClosureDate().getClosureDay().v(),
				(domain.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 年休月別残数データ
	 */
	public void fromDomainForUpdate(RsvLeaRemNumEachMonth domain){

		// Optional列の初期化
		this.usedDaysAfter = null;
		this.factUsedDaysAfter = null;
		this.remainingDaysAfter = null;
		this.factRemainingDaysAfter = null;
		this.grantDays = null;
		
		val normal = domain.getReserveLeave();
		val normalUsed = normal.getUsedNumber();
		val real = domain.getRealReserveLeave();
		val realUsed = real.getUsedNumber();
		
		this.closureStatus = domain.getClosureStatus().value;
		this.startDate = domain.getClosurePeriod().start();
		this.endDate = domain.getClosurePeriod().end();
		
		// 積立年休：使用数
		this.usedDays = normalUsed.getUsedDays().v();
		this.usedDaysBefore = normalUsed.getUsedDaysBeforeGrant().v();
		if (normalUsed.getUsedDaysAfterGrant().isPresent()){
			this.usedDaysAfter = normalUsed.getUsedDaysAfterGrant().get().v();
		}
		
		// 実積立年休：使用数
		this.factUsedDays = realUsed.getUsedDays().v();
		this.factUsedDaysBefore = realUsed.getUsedDaysBeforeGrant().v();
		if (realUsed.getUsedDaysAfterGrant().isPresent()){
			this.factUsedDaysAfter = realUsed.getUsedDaysAfterGrant().get().v();
		}
		
		// 積立年休：残数
		this.remainingDays = normal.getRemainingNumber().getTotalRemainingDays().v();
		this.remainingDaysBefore = normal.getRemainingNumberBeforeGrant().getTotalRemainingDays().v();
		if (normal.getRemainingNumberAfterGrant().isPresent()){
			val normalRemainAfter = normal.getRemainingNumberAfterGrant().get();
			this.remainingDaysAfter = normalRemainAfter.getTotalRemainingDays().v();
		}
		
		// 実積立年休：残数
		this.factRemainingDays = real.getRemainingNumber().getTotalRemainingDays().v();
		this.factRemainingDaysBefore = real.getRemainingNumberBeforeGrant().getTotalRemainingDays().v();
		if (real.getRemainingNumberAfterGrant().isPresent()){
			val realRemainAfter = real.getRemainingNumberAfterGrant().get();
			this.factRemainingDaysAfter = realRemainAfter.getTotalRemainingDays().v();
		}
		
		// 積立年休：未消化数
		val normalUndigest = normal.getUndigestedNumber();
		this.notUsedDays = normalUndigest.getUndigestedDays().v();
		
		// 付与区分
		this.grantAtr = (domain.isGrantAtr() ? 1 : 0);
		
		// 積立年休付与情報
		if (domain.getReserveLeaveGrant().isPresent()){
			val grantInfo = domain.getReserveLeaveGrant().get();
			this.grantDays = grantInfo.getGrantDays().v();
		}
		
		// 積立年休月別残数明細：残数
		List<GeneralDate> normalGrantDateList = new ArrayList<>();
		Map<GeneralDate, ReserveLeaveRemainingDetail> normalRemain = new HashMap<>();
		Map<GeneralDate, ReserveLeaveRemainingDetail> normalRealRemain = new HashMap<>();
		for (val detail : normal.getRemainingNumber().getDetails()){
			val grantDate = detail.getGrantDate();
			if (!normalGrantDateList.contains(grantDate)) normalGrantDateList.add(grantDate);
			normalRemain.putIfAbsent(grantDate, detail);
		}
		for (val detail : real.getRemainingNumber().getDetails()){
			val grantDate = detail.getGrantDate();
			if (!normalGrantDateList.contains(grantDate)) normalGrantDateList.add(grantDate);
			normalRealRemain.putIfAbsent(grantDate, detail);
		}
		normalGrantDateList.removeIf(
				c -> {return (!normalRemain.containsKey(c) || !normalRealRemain.containsKey(c));} );
		
		// 積立年休月別残数明細：残数付与前
		List<GeneralDate> beforeGrantDateList = new ArrayList<>();
		Map<GeneralDate, ReserveLeaveRemainingDetail> beforeRemain = new HashMap<>();
		Map<GeneralDate, ReserveLeaveRemainingDetail> beforeRealRemain = new HashMap<>();
		for (val detail : normal.getRemainingNumberBeforeGrant().getDetails()){
			val grantDate = detail.getGrantDate();
			if (!beforeGrantDateList.contains(grantDate)) beforeGrantDateList.add(grantDate);
			beforeRemain.putIfAbsent(grantDate, detail);
		}
		for (val detail : real.getRemainingNumberBeforeGrant().getDetails()){
			val grantDate = detail.getGrantDate();
			if (!beforeGrantDateList.contains(grantDate)) beforeGrantDateList.add(grantDate);
			beforeRealRemain.putIfAbsent(grantDate, detail);
		}
		beforeGrantDateList.removeIf(
				c -> {return (!beforeRemain.containsKey(c) || !beforeRealRemain.containsKey(c));} );
		
		// 積立年休月別残数明細：残数付与後
		List<GeneralDate> afterGrantDateList = new ArrayList<>();
		Map<GeneralDate, ReserveLeaveRemainingDetail> afterRemain = new HashMap<>();
		Map<GeneralDate, ReserveLeaveRemainingDetail> afterRealRemain = new HashMap<>();
		if (normal.getRemainingNumberAfterGrant().isPresent() &&
			real.getRemainingNumberAfterGrant().isPresent()){
			for (val detail : normal.getRemainingNumberAfterGrant().get().getDetails()){
				val grantDate = detail.getGrantDate();
				if (!afterGrantDateList.contains(grantDate)) afterGrantDateList.add(grantDate);
				afterRemain.putIfAbsent(grantDate, detail);
			}
			for (val detail : real.getRemainingNumberAfterGrant().get().getDetails()){
				val grantDate = detail.getGrantDate();
				if (!afterGrantDateList.contains(grantDate)) afterGrantDateList.add(grantDate);
				afterRealRemain.putIfAbsent(grantDate, detail);
			}
			afterGrantDateList.removeIf(
					c -> {return (!afterRemain.containsKey(c) || !afterRealRemain.containsKey(c));} );
		}
		
		// 積立年休月別残数明細　更新
		if (this.krcdtMonRsvleaDetails == null) this.krcdtMonRsvleaDetails = new ArrayList<>();
		val itrDetails =  this.krcdtMonRsvleaDetails.listIterator();
		while (itrDetails.hasNext()){
			val detail = itrDetails.next();
			switch (detail.PK.remainingType){
			case 0:
				if (!normalGrantDateList.contains(detail.PK.grantDate)) itrDetails.remove();
				break;
			case 1:
				if (!beforeGrantDateList.contains(detail.PK.grantDate)) itrDetails.remove();
				break;
			case 2:
				if (!afterGrantDateList.contains(detail.PK.grantDate)) itrDetails.remove();
				break;
			default:
				itrDetails.remove();
				break;
			}
		}
		for (val grantDate : normalGrantDateList){
			KrcdtMonRsvleaDetail detail = new KrcdtMonRsvleaDetail();
			val detailOpt = this.krcdtMonRsvleaDetails.stream()
					.filter(c -> {return c.PK.remainingType == 0 && c.PK.grantDate.equals(grantDate);})
					.findFirst();
			if (detailOpt.isPresent()){
				detailOpt.get().fromDomainForUpdate(normalRemain.get(grantDate), normalRealRemain.get(grantDate));
			}
			else {
				detail.fromDomainForPersist(domain, 0, normalRemain.get(grantDate), normalRealRemain.get(grantDate));
				this.krcdtMonRsvleaDetails.add(detail);
			}
		}
		for (val grantDate : beforeGrantDateList){
			KrcdtMonRsvleaDetail detail = new KrcdtMonRsvleaDetail();
			val detailOpt = this.krcdtMonRsvleaDetails.stream()
					.filter(c -> {return c.PK.remainingType == 1 && c.PK.grantDate.equals(grantDate);})
					.findFirst();
			if (detailOpt.isPresent()){
				detailOpt.get().fromDomainForUpdate(beforeRemain.get(grantDate), beforeRealRemain.get(grantDate));
			}
			else {
				detail.fromDomainForPersist(domain, 1, beforeRemain.get(grantDate), beforeRealRemain.get(grantDate));
				this.krcdtMonRsvleaDetails.add(detail);
			}
		}
		for (val grantDate : afterGrantDateList){
			KrcdtMonRsvleaDetail detail = new KrcdtMonRsvleaDetail();
			val detailOpt = this.krcdtMonRsvleaDetails.stream()
					.filter(c -> {return c.PK.remainingType == 2 && c.PK.grantDate.equals(grantDate);})
					.findFirst();
			if (detailOpt.isPresent()){
				detailOpt.get().fromDomainForUpdate(afterRemain.get(grantDate), afterRealRemain.get(grantDate));
			}
			else {
				detail.fromDomainForPersist(domain, 2, afterRemain.get(grantDate), afterRealRemain.get(grantDate));
				this.krcdtMonRsvleaDetails.add(detail);
			}
		}
	}
}
