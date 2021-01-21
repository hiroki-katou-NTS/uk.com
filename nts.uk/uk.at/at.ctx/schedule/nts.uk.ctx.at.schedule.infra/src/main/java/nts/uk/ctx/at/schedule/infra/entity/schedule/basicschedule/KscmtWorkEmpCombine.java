package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 
 * @author trungtran
 *勤務就業組み合わせ
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_WORK_EMP_COMBINE")
public class KscmtWorkEmpCombine extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtWorkEmpCombinePK kscmtWorkEmpCombinePK;

	/** 勤務種類コード */
	@Column(name = "WORKTYPE_CD")
	public String workTypeCode;

	/** 就業時間帯コード */
	@Column(name = "WORKTIME_CD")
	public String workTimeCode;

	/** 勤務就業記号 */
	@Column(name = "SYNAME")
	public String symbolName;

	@Override
	protected Object getKey() {
		return kscmtWorkEmpCombinePK;
	}

}
