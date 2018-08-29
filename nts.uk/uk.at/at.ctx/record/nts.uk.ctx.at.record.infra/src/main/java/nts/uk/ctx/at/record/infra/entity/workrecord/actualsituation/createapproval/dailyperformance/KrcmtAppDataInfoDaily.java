package nts.uk.ctx.at.record.infra.entity.workrecord.actualsituation.createapproval.dailyperformance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDaily;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.ErrorMessageRC;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name="KRCMT_APP_DATA_INFO_DAI")
@NoArgsConstructor
public class KrcmtAppDataInfoDaily extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtAppDataInfoDailyPK krcmtAppDataInfoDailyPK;
	
	@Column(name="ERROR_MESSAGE")
	public String errorMessage;

	@Override
	protected Object getKey() {
		return krcmtAppDataInfoDailyPK;
	}
	
	public KrcmtAppDataInfoDaily(KrcmtAppDataInfoDailyPK krcmtAppDataInfoDailyPK, String errorMessage) {
		super();
		this.krcmtAppDataInfoDailyPK = krcmtAppDataInfoDailyPK;
		this.errorMessage = errorMessage;
	}

	public static KrcmtAppDataInfoDaily toEntity(AppDataInfoDaily domain) {
		return new KrcmtAppDataInfoDaily(
				new KrcmtAppDataInfoDailyPK(
						domain.getEmployeeId(),
						domain.getExecutionId()
						),
				domain.getErrorMessage().v()
				);
	}
	
	public AppDataInfoDaily toDomain() {
		return new AppDataInfoDaily(
				this.krcmtAppDataInfoDailyPK.employeeId,
				this.krcmtAppDataInfoDailyPK.executionId,
				new ErrorMessageRC(this.errorMessage)
				);
	}
}
