package nts.uk.ctx.sys.auth.dom.grant;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author HungTT - ロールセット職位別付与
 *
 */

@NoArgsConstructor
@Getter
@Setter
public class RoleSetGrantedJobTitle extends AggregateRoot {

	// 会社ID
	private String companyId;

	//兼務者にも適用する
	private boolean applyToConcurrentPerson;

	//ロールセット職位別付与詳細
	private List<RoleSetGrantedJobTitleDetail> details;

	public RoleSetGrantedJobTitle(String companyId, boolean applyToConcurrentPerson, List<RoleSetGrantedJobTitleDetail> details) {
		super();
		this.companyId = companyId;
		this.applyToConcurrentPerson = applyToConcurrentPerson;
		this.details = details;
	}
	
}
