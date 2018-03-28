package nts.uk.ctx.at.record.infra.entity.monthlyclosureupdatelog;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInfor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - 月締め更新エラー情報
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "KRCDT_MCLOSE_UPD_ERR_INF")
public class KrcdtMcloseUpdErrInf extends UkJpaEntity {

	@EmbeddedId
	public KrcdtMcloseUpdErrInfPk pk;

	// リソースID
	@Column(name = "RESOURCE_ID")
	public String resourceId;

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

	public KrcdtMcloseUpdErrInf(String employeeId, String monthlyClosureUpdateLogId, String resourceId,
			String errorMessage, int atr) {
		super();
		this.pk = new KrcdtMcloseUpdErrInfPk(employeeId, monthlyClosureUpdateLogId);
		this.resourceId = resourceId;
		this.errorMessage = errorMessage;
		this.atr = atr;
	}

	public KrcdtMcloseUpdErrInf fromDomain(MonthlyClosureUpdateErrorInfor domain) {
		return new KrcdtMcloseUpdErrInf(domain.getEmployeeId(), domain.getMonthlyClosureUpdateLogId(),
				domain.getResourceId(), domain.getErrorMessage(), domain.getAtr().value);
	}

	public MonthlyClosureUpdateErrorInfor toDomain() {
		return new MonthlyClosureUpdateErrorInfor(this.pk.employeeId, this.pk.monthlyClosureUpdateLogId,
				this.resourceId, this.errorMessage, this.atr);
	}

}
