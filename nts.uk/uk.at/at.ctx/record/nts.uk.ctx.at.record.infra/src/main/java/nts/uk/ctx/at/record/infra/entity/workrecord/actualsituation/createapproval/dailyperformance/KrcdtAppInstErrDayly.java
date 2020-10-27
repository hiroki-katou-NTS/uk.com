package nts.uk.ctx.at.record.infra.entity.workrecord.actualsituation.createapproval.dailyperformance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDaily;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.ErrorMessageRC;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name="KRCDT_APP_INST_ERR_DAY")
@NoArgsConstructor
public class KrcdtAppInstErrDayly extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcdtAppInstErrDaylyPK krcdtAppInstErrDaylyPK;
	
	@Column(name="ERROR_MESSAGE")
	public String errorMessage;

	@Override
	protected Object getKey() {
		return krcdtAppInstErrDaylyPK;
	}
	
	public KrcdtAppInstErrDayly(KrcdtAppInstErrDaylyPK krcdtAppInstErrDaylyPK, String errorMessage) {
		super();
		this.krcdtAppInstErrDaylyPK = krcdtAppInstErrDaylyPK;
		this.errorMessage = errorMessage;
	}

	public static KrcdtAppInstErrDayly toEntity(AppDataInfoDaily domain) {
		return new KrcdtAppInstErrDayly(
				new KrcdtAppInstErrDaylyPK(
						domain.getEmployeeId(),
						domain.getExecutionId()
						),
				domain.getErrorMessage().v()
				);
	}
	
	public AppDataInfoDaily toDomain() {
		return new AppDataInfoDaily(
				this.krcdtAppInstErrDaylyPK.employeeId,
				this.krcdtAppInstErrDaylyPK.executionId,
				new ErrorMessageRC(this.errorMessage)
				);
	}
}
