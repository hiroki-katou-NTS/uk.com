package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.LastExecDateTime;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@Entity
@Table(name="KFNMT_LAST_EXEC_DATETIME")
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtLastExecDateTime extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
    public KfnmtLastExecDateTimePK kfnmtLastDateTimePK;
	
	/* 最終日 */
	@Column(name = "LAST_EXEC_DATE_TIME")
	public GeneralDateTime lastExecDateTime;
	
	@Override
	protected Object getKey() {
		return this.kfnmtLastDateTimePK;
	}
	
	public static KfnmtLastExecDateTime toEntity(LastExecDateTime domain) {
		return new KfnmtLastExecDateTime(
				new KfnmtLastExecDateTimePK(domain.getCompanyId(), domain.getExecItemCd().v()),
				domain.getLastExecDateTime());
	}
	
	public LastExecDateTime toDomain() {
		return new LastExecDateTime(this.kfnmtLastDateTimePK.companyId,
									new ExecutionCode(this.kfnmtLastDateTimePK.execItemCd),
									this.lastExecDateTime);
	}
}
