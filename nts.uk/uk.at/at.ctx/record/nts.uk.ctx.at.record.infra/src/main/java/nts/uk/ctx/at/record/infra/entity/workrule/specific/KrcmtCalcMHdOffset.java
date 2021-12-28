package nts.uk.ctx.at.record.infra.entity.workrule.specific;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.HolidayPriorityOrder;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

//import java.math.BigDecimal;


/**
 * The persistent class for the KRCMT_CALC_M_HD_OFFSET database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="KRCMT_CALC_M_HD_OFFSET")
public class KrcmtCalcMHdOffset extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CID")
	private String cid;
	
	@Column(name="SIXTY_HOUR")
	private int sixtyHour;

	@Column(name="SUBSTITUTE")
	private int substitute;

	@Column(name="ANNUAL")
	private int annual;

	@Column(name="SPECIAL")
	private int special;

	@Column(name="CHILD_CARE")
	private int childCare;

	@Column(name="CARE")
	private int care;

	@Override
	protected Object getKey() {
		return cid;
	}
	
	public static KrcmtCalcMHdOffset from(CompanyHolidayPriorityOrder setting) {
		KrcmtCalcMHdOffset entity = new KrcmtCalcMHdOffset();
		entity.cid = setting.getCompanyId();
		for (val order : setting.getHolidayPriorityOrders()) {
			switch (order) {
			case ANNUAL_HOLIDAY:
				entity.annual = setting.getHolidayPriorityOrders().indexOf(order);
				break;
			case CARE:
				entity.care = setting.getHolidayPriorityOrders().indexOf(order);
				break;
			case CHILD_CARE:
				entity.childCare = setting.getHolidayPriorityOrders().indexOf(order);
				break;
			case SIXTYHOUR_HOLIDAY:
				entity.sixtyHour = setting.getHolidayPriorityOrders().indexOf(order);
				break;
			case SPECIAL_HOLIDAY:
				entity.special = setting.getHolidayPriorityOrders().indexOf(order);
				break;
			case SUB_HOLIDAY:
				entity.substitute = setting.getHolidayPriorityOrders().indexOf(order);
				break;
			default:
				break;
			}
		}
		return entity;
	}
	
	public CompanyHolidayPriorityOrder domain() {
		
		val orders = Arrays.asList(Pair.of(this.annual, HolidayPriorityOrder.ANNUAL_HOLIDAY),
									Pair.of(this.care, HolidayPriorityOrder.CARE),
									Pair.of(this.childCare, HolidayPriorityOrder.CHILD_CARE),
									Pair.of(this.sixtyHour, HolidayPriorityOrder.SIXTYHOUR_HOLIDAY),
									Pair.of(this.special, HolidayPriorityOrder.SPECIAL_HOLIDAY),
									Pair.of(this.substitute, HolidayPriorityOrder.SUB_HOLIDAY))
				.stream().sorted((c1, c2) -> c1.getKey().compareTo(c2.getKey()))
				.map(c -> c.getValue()).collect(Collectors.toList());
		
		return new CompanyHolidayPriorityOrder(this.cid, orders);
	}
}