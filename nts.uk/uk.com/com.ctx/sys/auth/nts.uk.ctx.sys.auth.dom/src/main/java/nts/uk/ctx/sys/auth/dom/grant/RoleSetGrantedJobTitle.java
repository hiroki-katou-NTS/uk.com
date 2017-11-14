package nts.uk.ctx.sys.auth.dom.grant;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetCode;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT - ロールセット職位別付与
 *
 */

@NoArgsConstructor
@Getter
public class RoleSetGrantedJobTitle extends AggregateRoot {

	// 会社ID
	private String companyId;

	//兼務者にも適用する
	private boolean applyToConcurrentPerson;

	//ロールセット職位別付与詳細
	private List<RoleSetGrantedJobTitleDetail> details;

	public RoleSetGrantedJobTitle(String companyId, boolean applyToConcurrentPerson) {
		super();
		this.companyId = companyId;
		this.applyToConcurrentPerson = applyToConcurrentPerson;
	}

	public void setDetails(List<RoleSetGrantedJobTitleDetail> details) {
		this.details = details;
	} 
	
}
