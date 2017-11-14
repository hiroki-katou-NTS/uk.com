package nts.uk.ctx.sys.auth.dom.role;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author dxthuong
 *
 */
@Getter
public class PersonRole extends AggregateRoot{
	
	/**
	 *  ロールID
	 */
	private String roleId;

	/**
	 * 未来日参照許可
	 */
	private Boolean referFutureDate;
	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public void setReferFutureDate(Boolean referFutureDate) {
		this.referFutureDate = referFutureDate;
	}

}
