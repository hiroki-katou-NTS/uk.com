package nts.uk.ctx.exio.infra.entity.exo.authset;

import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.ctx.exio.dom.exo.authset.ExOutCtgAuthSet;
import nts.uk.shr.com.permit.RestoreAvailabilityPermission;
import nts.uk.shr.infra.permit.data.JpaEntityOfAvailabilityPermissionBase;

/**
 * 外部出力カテゴリ利用権限の設定
 */
@Entity
@Table(name = "OIOMT_EX_OUT_CTG_AUTH_SET")
public class OiomtExOutCtgAuthSet extends JpaEntityOfAvailabilityPermissionBase<ExOutCtgAuthSet>
		implements RestoreAvailabilityPermission {
	
	@Override
	public ExOutCtgAuthSet toDomain() {
		return new ExOutCtgAuthSet(this);
	}

}
