package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.refersToInformation;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.refersToInformation.EmployeeVeinInformation;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author chungnt
 * UKDesign.データベース.ER図.就業.勤務実績.<<core>> 勤務実績.打刻管理.指情報.KRCMT_FINGER_VEIN_INFO
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KRCMT_FINGER_VEIN_INFO")
public class KrmctFingerVeinInfo extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 社員ID
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "SID")
	public String employeeId;

	/**
	 * 指種類
	 */
	@Basic(optional = false)
	@Column(name = "FINGER_TYPE")
	public int fingerType;
	
	/**
	 * 静脈NO
	 */
	@Basic(optional = false)
	@Column(name = "VEIN_NO")
	public int veinNo;
	
	/**
	 * 静脈内容
	 */
	@Basic(optional = false)
	@Column(name = "VEIN_DETAIL")
	public String veinDetail;
	

	@Override
	protected Object getKey() {
		return this.employeeId;
	}
	
	public void update(EmployeeVeinInformation domain) {
		this.employeeId = domain.getSid();
		this.fingerType = domain.getVeininformations().get(0).getFingerType().value;
//		this.veinNo = domain.getVeininformations().get(0).get
		this.veinDetail = domain.getVeininformations().get(0).getVeinInformation().v();
	}

}
