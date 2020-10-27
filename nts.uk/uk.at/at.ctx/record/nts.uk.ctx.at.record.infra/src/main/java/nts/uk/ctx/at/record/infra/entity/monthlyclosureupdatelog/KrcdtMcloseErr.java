package nts.uk.ctx.at.record.infra.entity.monthlyclosureupdatelog;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInfor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author HungTT - 月締め更新エラー情報
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "KRCDT_MCLOSE_ERR")
public class KrcdtMcloseErr extends ContractUkJpaEntity {

	@EmbeddedId
	public KrcdtMcloseErrPk pk;

	// エラーメッセージ
	@Column(name = "ERROR_MESSAGE")
	public String errorMessage;

	// 区分
	@Column(name = "ATR")
	public int atr;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KrcdtMcloseErr(String employeeId, String monthlyClosureUpdateLogId, GeneralDate actualClosureEndDate,
			String resourceId, String errorMessage, int atr) {
		super();
		this.pk = new KrcdtMcloseErrPk(employeeId, monthlyClosureUpdateLogId, actualClosureEndDate, resourceId);
		this.errorMessage = errorMessage;
		this.atr = atr;
	}

	public static KrcdtMcloseErr fromDomain(MonthlyClosureUpdateErrorInfor domain) {
		return new KrcdtMcloseErr(domain.getEmployeeId(), domain.getMonthlyClosureUpdateLogId(),
				domain.getActualClosureEndDate(), domain.getResourceId(), domain.getErrorMessage(),
				domain.getAtr().value);
	}

	public MonthlyClosureUpdateErrorInfor toDomain() {
		return new MonthlyClosureUpdateErrorInfor(this.pk.employeeId, this.pk.monthlyClosureUpdateLogId,
				this.pk.actualClosureEndDate, this.pk.resourceId, this.errorMessage, this.atr);
	}

}
