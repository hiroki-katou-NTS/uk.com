package nts.uk.ctx.at.schedule.infra.entity.employeeinfo.employeesort;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author HieuLt
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtSyaOrderPriorityPk implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/**
	 * 並び替えの優先順 OrderedListの順
	 */
	@Column(name = "PRIORITY")
	public int priority;

}
