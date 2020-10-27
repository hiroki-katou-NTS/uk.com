package nts.uk.ctx.at.record.infra.entity.weekly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AggregateAnyItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeeklyKey;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * エンティティ：集計任意項目
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_WEK_ANYITEMVALUE")
@NoArgsConstructor
public class KrcdtWekAnyItemValue extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtWekAnyItemValuePK PK;
	
	/** 時間 */
	@Column(name = "TIME_VALUE")
	public Integer timeValue;
	/** 回数 */
	@Column(name = "COUNT_VALUE")
	public Double countValue;
	/** 金額 */
	@Column(name = "MONEY_VALUE")
	public Integer moneyValue;
	
	/** マッチング：週別実績の勤怠時間 */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "IS_LAST_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "WEEK_NO", referencedColumnName = "WEEK_NO", insertable = false, updatable = false)
	})
	public KrcdtWekAttendanceTime krcdtWekAttendanceTime;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return 集計任意項目
	 */
	public AggregateAnyItem toDomain(){
		
		return AggregateAnyItem.of(
				this.PK.anyItemId,
				(this.timeValue == null ? null : new AnyTimeMonth(this.timeValue)),
				(this.countValue == null ? null : new AnyTimesMonth(this.countValue)),
				(this.moneyValue == null ? null : new AnyAmountMonth(this.moneyValue)));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：週別実績の勤怠時間
	 * @param domain 集計任意項目
	 */
	public void fromDomainForPersist(AttendanceTimeOfWeeklyKey key, AggregateAnyItem domain){
		
		this.PK = new KrcdtWekAnyItemValuePK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				key.getWeekNo(),
				domain.getAnyItemNo());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 集計任意項目
	 */
	public void fromDomainForUpdate(AggregateAnyItem domain){

		this.timeValue = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}
}
