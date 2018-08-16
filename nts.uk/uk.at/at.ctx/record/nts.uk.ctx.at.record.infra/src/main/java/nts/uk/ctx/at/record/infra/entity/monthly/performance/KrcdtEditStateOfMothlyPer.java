package nts.uk.ctx.at.record.infra.entity.monthly.performance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.performance.EditStateOfMonthlyPerformance;
import nts.uk.ctx.at.record.dom.monthly.performance.enums.StateOfEditMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：月別実績の編集状態
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_EDIT_STATE_MONTH")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KrcdtEditStateOfMothlyPer extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtEditStateOfMothlyPerPK krcdtEditStateOfMothlyPerPK;
	
	/** 処理年月 */
	@Column(name = "YM")
	public int processDate;
	
	/** 締めID */
	@Column(name = "CLOSURE_ID")
	public int closureID;
	
	/** 締め日 */
	@Column(name = "CLOSURE_DAY")
	public Integer closeDay;
	
	/** 末日とする */
	@Column(name = "IS_LAST_DAY")
	public Integer isLastDay;
	
	/** 編集状態 **/
	@Column(name = "STATE_OF_EDIT")
	public Integer stateOfEdit;
	
	@Override
	protected Object getKey() {
		return this.krcdtEditStateOfMothlyPerPK;
	}

	/**
	 * ドメインに変換
	 * @return 月別実績の編集状態
	 */
	public EditStateOfMonthlyPerformance toDomain(){
		
		return new EditStateOfMonthlyPerformance(
				this.krcdtEditStateOfMothlyPerPK.employeeID,
				new YearMonth(this.processDate),
				new DatePeriod(
						this.krcdtEditStateOfMothlyPerPK.startYmd,
						this.krcdtEditStateOfMothlyPerPK.endYmd),
				this.krcdtEditStateOfMothlyPerPK.attendanceItemID,
				EnumAdaptor.valueOf(this.closureID, ClosureId.class),
				new ClosureDate(
						this.closeDay,
						(this.isLastDay == 1)),
				EnumAdaptor.valueOf(this.stateOfEdit, StateOfEditMonthly.class));
	}
}
