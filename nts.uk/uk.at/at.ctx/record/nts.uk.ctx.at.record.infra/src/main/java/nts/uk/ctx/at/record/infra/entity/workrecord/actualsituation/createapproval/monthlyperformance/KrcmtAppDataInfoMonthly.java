package nts.uk.ctx.at.record.infra.entity.workrecord.actualsituation.createapproval.monthlyperformance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDaily;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.ErrorMessageRC;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthly;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name="KRCMT_APP_DATA_INFO_MON")
@NoArgsConstructor
public class KrcmtAppDataInfoMonthly extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtAppDataInfoMonthlyPK krcmtAppDataInfoMonthlyPK;
	
	@Column(name="ERROR_MESSAGE")
	public String errorMessage;

	@Override
	protected Object getKey() {
		return krcmtAppDataInfoMonthlyPK;
	}
	
	public KrcmtAppDataInfoMonthly(KrcmtAppDataInfoMonthlyPK krcmtAppDataInfoMonthlyPK, String errorMessage) {
		super();
		this.krcmtAppDataInfoMonthlyPK = krcmtAppDataInfoMonthlyPK;
		this.errorMessage = errorMessage;
	}

	public static KrcmtAppDataInfoMonthly toEntity(AppDataInfoMonthly domain) {
		return new KrcmtAppDataInfoMonthly(
				new KrcmtAppDataInfoMonthlyPK(
						domain.getEmployeeId(),
						domain.getExecutionId()
						),
				domain.getErrorMessage().v()
				);
	}
	
	public AppDataInfoMonthly toDomain() {
		return new AppDataInfoMonthly(
				this.krcmtAppDataInfoMonthlyPK.employeeId,
				this.krcmtAppDataInfoMonthlyPK.executionId,
				new ErrorMessageRC(this.errorMessage)
				);
	}
}
