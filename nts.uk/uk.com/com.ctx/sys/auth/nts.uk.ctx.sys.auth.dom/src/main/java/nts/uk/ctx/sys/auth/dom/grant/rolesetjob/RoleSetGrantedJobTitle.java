package nts.uk.ctx.sys.auth.dom.grant.rolesetjob;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * ロールセット職位別付与
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.権限管理.付与.ロールセット職位別付与.ロールセット職位別付与
 * @author HungTT
 *
 */

@Getter
@Setter
@AllArgsConstructor
public class RoleSetGrantedJobTitle extends AggregateRoot {

	// 会社ID
	private String companyId;

	//ロールセット職位別付与詳細
	private List<RoleSetGrantedJobTitleDetail> details;
	
	public boolean isRoleSetCdExist(String roleSetCd){
		if (this.details == null || this.details.isEmpty()) return false;
		for (RoleSetGrantedJobTitleDetail d : this.details){
			if (d.getRoleSetCd().v().equals(roleSetCd)) {
				return true;
			}
		}
		return false;
	}
	
}
