/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.databeforereflecting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 *  退職者情報
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RetirementInformation extends DomainObject{

	// 履歴ID
	public String historyId;

	// 契約コード
	public String contractCode;
	
	//会社ID
	public String companyId;
	
	//会社コード
	public String companyCode;
	
	// 業務ID
	public Integer workId;
	
	// 業務名称
	public String workName;
	
	// 社員ID
	public String sId;
	
	// 社員コード
	public String scd;
	
	// 社員名  個人名
	public String personName;
	
	// 保留フラグ
	public int pendingFlag;
	
	// 入力日
	public GeneralDate inputDate;
	
	// ステータス
	public int status;
	
	// 退職日
	public GeneralDateTime retirementDate;
	
	// 公開日
	public GeneralDateTime releaseDate;
	
	// 退職区分
	public RetirementCategory retirementCategory;
	
	//  届出区分
	public int notificationCategory;
	
	//  退職理由区分1_ID
	public int retirementReasonCtgID1;
	
	//  退職理由区分1_コード
	public String retirementReasonCtgCode1;
	
	//  退職理由区分1_名称
	public String retirementReasonCtgName1;
	
	//  退職理由区分2_ID
	public int retirementReasonCtgID2;
	
	//  退職理由区分2_コード
	public String retirementReasonCtgCode2;
	
	//  退職理由区分2_名称
	public String retirementReasonCtgName2;
	
	// 退職の備考
	public String retirementRemarks;
	
	// 自己都合退職の手続き
	public String retirementReasonVal;
	
	// 解雇予告日
	public GeneralDate dismissalNoticeDate;
	
	// 解雇予告手当支給日
	public GeneralDate dismissalNoticeDateAllow;
	
	// 解雇の事由・手続き
	public String reaAndProForDis;
	
	// 解雇の理由1_チェック
	public int naturalUnaReasons_1;
	
	// 解雇の理由1_文字列
	public String naturalUnaReasons_1Val;
	
	// 解雇の理由2_チェック
	public int naturalUnaReasons_2;
	
	// 解雇の理由2_文字列
	public String naturalUnaReasons_2Val;
	
	// 解雇の理由3_チェック
	public int naturalUnaReasons_3;
	
	// 解雇の理由3_文字列
	public String naturalUnaReasons_3Val;
	
	// 解雇の理由4_チェック
	public int naturalUnaReasons_4;
	
	// 解雇の理由4_文字列
	public String naturalUnaReasons_4Val;
	
	// 解雇の理由5_チェック
	public int naturalUnaReasons_5;
	
	// 解雇の理由5_文字列
	public String naturalUnaReasons_5Val;
	
	// 解雇の理由6_チェック
	public int naturalUnaReasons_6;
	
	// 解雇の理由6_文字列
	public String naturalUnaReasons_6Val;
	
	// ===========
	
	// 反映先_履歴ID
	public String histId_Refer;
	
	// 個人ID
	public String pId;
	
	
}
