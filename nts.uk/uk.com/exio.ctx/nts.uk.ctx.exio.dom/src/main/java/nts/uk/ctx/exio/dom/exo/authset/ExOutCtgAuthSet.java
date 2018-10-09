package nts.uk.ctx.exio.dom.exo.authset;

import nts.uk.shr.com.permit.AvailabilityPermissionBase;
import nts.uk.shr.com.permit.RestoreAvailabilityPermission;

/**
 * 外部出力カテゴリ利用権限の設定
 */
public class ExOutCtgAuthSet extends AvailabilityPermissionBase {

	public ExOutCtgAuthSet(RestoreAvailabilityPermission restore) {
		super(restore);
	}

}
