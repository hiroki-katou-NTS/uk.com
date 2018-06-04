package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InforSpecialLeaveOfEmployee {
	//・状態：付与あり or 付与なし or 特別休暇不使用 or 利用不可
	private InforStatus status;
	//・蓄積上限日数：日数
	private Integer upLimiDays;	
	//・分割使用可否：boolean
	private boolean chkUser;
	//・特別休暇情報一覧
	
	//・エラーフラグ
	private boolean chkError;
}
