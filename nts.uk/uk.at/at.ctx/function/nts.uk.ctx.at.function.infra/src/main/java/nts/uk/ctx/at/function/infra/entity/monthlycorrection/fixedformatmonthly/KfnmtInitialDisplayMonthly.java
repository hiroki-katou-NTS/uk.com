package nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthly;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyPerformanceFormatCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_MON_PERFORMANCE_DIS")
public class KfnmtInitialDisplayMonthly extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtInitialDisplayMonthlyPK kfnmtInitialDisplayMonthlyPK; 
	
	@Override
	protected Object getKey() {
		return kfnmtInitialDisplayMonthlyPK;
	}

	public KfnmtInitialDisplayMonthly(KfnmtInitialDisplayMonthlyPK kfnmtInitialDisplayMonthlyPK) {
		super();
		this.kfnmtInitialDisplayMonthlyPK = kfnmtInitialDisplayMonthlyPK;
	}
	
	public static KfnmtInitialDisplayMonthly toEntity(InitialDisplayMonthly domain) {
		return new KfnmtInitialDisplayMonthly(
				new KfnmtInitialDisplayMonthlyPK(
					domain.getCompanyID(),
					domain.getMonthlyPfmFormatCode().v())
				);
	}
	
	public InitialDisplayMonthly toDomain() {
		return new InitialDisplayMonthly(
				this.kfnmtInitialDisplayMonthlyPK.companyID,
				new MonthlyPerformanceFormatCode(this.kfnmtInitialDisplayMonthlyPK.monthlyPfmFormatCode)
				);
	}

}
