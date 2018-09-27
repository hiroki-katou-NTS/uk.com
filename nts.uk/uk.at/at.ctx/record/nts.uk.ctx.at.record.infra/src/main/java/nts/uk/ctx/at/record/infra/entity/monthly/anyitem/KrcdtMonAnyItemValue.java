package nts.uk.ctx.at.record.infra.entity.monthly.anyitem;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：月別実績の任意項目
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_ANYITEMVALUE")
@NoArgsConstructor
public class KrcdtMonAnyItemValue extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAnyItemValuePK PK;
	
	/** 時間 */
	@Column(name = "TIME_VALUE")
	public Integer timeValue;
	
	/** 回数 */
	@Column(name = "COUNT_VALUE")
	public Double countValue;
	
	/** 金額 */
	@Column(name = "MONEY_VALUE")
	public Integer moneyValue;
	
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
	public AnyItemOfMonthly toDomain(){
		
		return AnyItemOfMonthly.of(
				this.PK.employeeId,
				new YearMonth(this.PK.yearMonth),
				EnumAdaptor.valueOf(this.PK.closureId, ClosureId.class),
				new ClosureDate(this.PK.closureDay, (this.PK.isLastDay == 1)),
				this.PK.anyItemId,
				(this.timeValue == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue))),
				(this.countValue == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue))),
				(this.moneyValue == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue))));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param domain 月別実績の任意項目
	 */
	public void fromDomainForPersist(AnyItemOfMonthly domain){
		
		this.PK = new KrcdtMonAnyItemValuePK(
				domain.getEmployeeId(),
				domain.getYearMonth().v(),
				domain.getClosureId().value,
				domain.getClosureDate().getClosureDay().v(),
				(domain.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				domain.getAnyItemId());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 月別実績の任意項目
	 */
	public void fromDomainForUpdate(AnyItemOfMonthly domain){

		this.timeValue = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}
}
