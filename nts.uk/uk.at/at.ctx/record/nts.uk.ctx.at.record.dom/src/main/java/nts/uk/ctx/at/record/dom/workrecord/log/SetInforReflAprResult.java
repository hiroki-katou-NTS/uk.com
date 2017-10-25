/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.Getter;

/**
 * @author danpv
 *
 *承認結果反映用の設定情報
 */
@Getter
public class SetInforReflAprResult extends CalExeSettingInfor{
	
	private boolean forciblyReflect;
	
	public SetInforReflAprResult() {
		super();
	}

}
