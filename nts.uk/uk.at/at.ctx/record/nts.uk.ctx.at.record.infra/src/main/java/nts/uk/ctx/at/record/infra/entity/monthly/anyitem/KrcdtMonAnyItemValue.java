package nts.uk.ctx.at.record.infra.entity.monthly.anyitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyTimesMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
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
	public int timeValue;
	
	/** 回数 */
	@Column(name = "COUNT_VALUE")
	public double countValue;
	
	/** 金額 */
	@Column(name = "MONEY_VALUE")
	public int moneyValue;
	
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
				new AnyTimeMonth(this.timeValue),
				new AnyTimesMonth(this.countValue),
				new AnyAmountMonth(this.moneyValue));
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

		this.timeValue = domain.getTime().v();
		this.countValue = domain.getTimes().v().doubleValue();
		this.moneyValue = domain.getAmount().v();
	}
}
