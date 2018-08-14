package nts.uk.ctx.at.record.infra.entity.byperiod;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.byperiod.AttendanceTimeOfAnyPeriodKey;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AggregateAnyItem;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：集計任意項目
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_ANP_ANYITEMVALUE")
@NoArgsConstructor
public class KrcdtAnpAnyItemValue extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtAnpAnyItemValuePK PK;
	
	/** 時間 */
	@Column(name = "TIME_VALUE")
	public Integer timeValue;
	/** 回数 */
	@Column(name = "COUNT_VALUE")
	public Double countValue;
	/** 金額 */
	@Column(name = "MONEY_VALUE")
	public Integer moneyValue;
	
	/** マッチング：任意期間別実績の勤怠時間 */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "FRAME_CODE", referencedColumnName = "FRAME_CODE", insertable = false, updatable = false)
	})
	public KrcdtAnpAttendanceTime krcdtAnpAttendanceTime;
	
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
	 * @param key キー値：任意期間別実績の勤怠時間
	 * @param domain 集計任意項目
	 */
	public void fromDomainForPersist(AttendanceTimeOfAnyPeriodKey key, AggregateAnyItem domain){
		
		this.PK = new KrcdtAnpAnyItemValuePK(
				key.getEmployeeId(),
				key.getAnyAggrFrameCode().v(),
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
