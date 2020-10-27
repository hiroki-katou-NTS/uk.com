package nts.uk.ctx.at.record.infra.entity.workrecord.actualsituation.createapproval.monthlyperformance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
//import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDaily;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.ErrorMessageRC;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthly;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name="KRCDT_APP_INST_ERR_MON")
@NoArgsConstructor
public class KrcdtAppInstErrMonthly extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcdtAppInstErrMonthlyPK krcdtAppInstErrMonthlyPK;
	
	@Column(name="ERROR_MESSAGE")
	public String errorMessage;

	@Override
	protected Object getKey() {
		return krcdtAppInstErrMonthlyPK;
	}
	
	public KrcdtAppInstErrMonthly(KrcdtAppInstErrMonthlyPK krcdtAppInstErrMonthlyPK, String errorMessage) {
		super();
		this.krcdtAppInstErrMonthlyPK = krcdtAppInstErrMonthlyPK;
		this.errorMessage = errorMessage;
	}

	public static KrcdtAppInstErrMonthly toEntity(AppDataInfoMonthly domain) {
		return new KrcdtAppInstErrMonthly(
				new KrcdtAppInstErrMonthlyPK(
						domain.getEmployeeId(),
						domain.getExecutionId()
						),
				domain.getErrorMessage().v()
				);
	}
	
	public AppDataInfoMonthly toDomain() {
		return new AppDataInfoMonthly(
				this.krcdtAppInstErrMonthlyPK.employeeId,
				this.krcdtAppInstErrMonthlyPK.executionId,
				new ErrorMessageRC(this.errorMessage)
				);
	}
}
