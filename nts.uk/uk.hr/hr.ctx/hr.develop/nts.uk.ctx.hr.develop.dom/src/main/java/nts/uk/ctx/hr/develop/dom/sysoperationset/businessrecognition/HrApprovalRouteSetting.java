/**
 * 
 */
package nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author laitv Domain 人事承認ルート設定
 */
@NoArgsConstructor
@Getter
@Setter
public class HrApprovalRouteSetting extends AggregateRoot {

	public String cid; // 会社ID
	public boolean comMode; // 会社
	public boolean devMode; // 部門
	public boolean empMode; // 個人

	public HrApprovalRouteSetting(String cid, boolean comMode, boolean devMode, boolean empMode) {
		super();
		this.cid = cid;
		this.comMode = comMode;
		this.devMode = devMode;
		this.empMode = empMode;
	}

	public static HrApprovalRouteSetting createFromJavaType(String cid, boolean comMode, boolean devMode,
			boolean empMode) {
		return new HrApprovalRouteSetting(cid, comMode, devMode, empMode);
	}

}
