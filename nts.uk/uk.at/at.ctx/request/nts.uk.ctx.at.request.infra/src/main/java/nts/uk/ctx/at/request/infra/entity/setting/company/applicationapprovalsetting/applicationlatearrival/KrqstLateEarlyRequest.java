package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationlatearrival;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequest_Old;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * TanLV
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "KRQST_LATE_EARLY_REQUESET")
public class KrqstLateEarlyRequest extends UkJpaEntity implements Serializable {
	public static final long serialVersionUID = 1L;
	
	/** * 会社ID */
	@Id
	@Column(name="CID")
	public String companyId;
	
	/** * 実績を表示する */
	@Column(name = "SHOW_RESULT")
	public int showResult;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return companyId;
	}
	
	/**
	 * To Domain
	 * @return
	 */
	public LateEarlyRequest_Old toDomain(){
		return new LateEarlyRequest_Old(
				this.companyId,
				this.showResult);
	}
}
