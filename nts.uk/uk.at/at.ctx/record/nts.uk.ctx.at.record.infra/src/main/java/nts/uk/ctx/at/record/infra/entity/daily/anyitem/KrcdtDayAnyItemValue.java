package nts.uk.ctx.at.record.infra.entity.daily.anyitem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemAmount;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemNo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemTime;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemTimes;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValue;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_DAY_ANYITEMVALUE")
public class KrcdtDayAnyItemValue extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KrcdtDayAnyItemValuePK krcdtDayAnyItemValuePK;
	/* 時間 */
	@Column(name = "TIME_VALUE")
	public Integer timeValue;
	/* 回数 */
	@Column(name = "COUNT_VALUE")
	public Integer countValue;
	/* 金額 */
	@Column(name = "MONEY_VALUE")
	public BigDecimal moneyValue;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayAnyItemValuePK;
	}
	
	
	public static List<KrcdtDayAnyItemValue> create(AnyItemValueOfDaily domain) {
		return domain.getItems().stream().map(d -> {
			KrcdtDayAnyItemValue entity = new KrcdtDayAnyItemValue();
			entity.krcdtDayAnyItemValuePK = new KrcdtDayAnyItemValuePK(domain.getEmployeeId(),
					domain.getYmd(), d.getItemNo().v());
			entity.setData(d);
			return entity;
		}).collect(Collectors.toList());
		
	}
	
	public static KrcdtDayAnyItemValue create(String empId, GeneralDate date, AnyItemValue domain) {
		KrcdtDayAnyItemValue entity = new KrcdtDayAnyItemValue();
		entity.krcdtDayAnyItemValuePK = new KrcdtDayAnyItemValuePK(empId,
				date, domain.getItemNo().v());
		entity.setData(domain);
		return entity;
		
	}
	
	public void setData(AnyItemValue anyItemValue){
		anyItemValue.getAmount().ifPresent(v -> {
			this.moneyValue = v.v();
		});
		anyItemValue.getTime().ifPresent(v -> {
			this.timeValue = v.valueAsMinutes();
		});
		anyItemValue.getTimes().ifPresent(v -> {
			this.countValue = v.v();
		});
	}
	
	public AnyItemValue toDomain() {
		return new AnyItemValue(new AnyItemNo(krcdtDayAnyItemValuePK.itemNo),
				Optional.ofNullable(countValue == null ? null : new AnyItemTimes(countValue)), 
				Optional.ofNullable(moneyValue == null ? null : new AnyItemAmount(moneyValue)),
				Optional.ofNullable(timeValue == null ? null : new AnyItemTime(timeValue)));
	}
	
	
	
}
