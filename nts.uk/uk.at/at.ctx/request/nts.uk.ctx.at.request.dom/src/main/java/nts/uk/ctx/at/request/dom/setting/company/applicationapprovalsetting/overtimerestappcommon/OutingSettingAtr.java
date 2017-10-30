package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon;

/**
 * @author loivt
 * 外出区分設定
 */
public enum OutingSettingAtr {
		 
		 /**
		 * 0:組合
		 */
		UNION(0),
		 
		 /**
		 * 1:私用
		 */
		USER(1),
		 
		 /**
		 * 2:申請時に選択（初期選択：組合）
		 */
		SELECTAPPUNION(2),
		 
		 /**
		 * 3:申請時に選択（初期選択：私用）
		 */
		SELECTAPPUSER(3);
		
		public final int value;
		OutingSettingAtr(int value){
			this.value = value;
		}
		 

}
