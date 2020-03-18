/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.databeforereflecting.common;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * 個人情報反映前データ
 * path : UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報反映前データ.個人情報反映前データ_共通.個人情報反映前データ
 */
@Getter
@Setter
@Builder
public class DataBeforeReflectingPerInfo extends AggregateRoot {

	
	public String historyId; // 履歴ID
	public String contractCode; // 契約コード
	public String companyId; //会社ID
	public String companyCode;  //会社コード
	public String pId; // 個人ID
	public String sId; // 社員ID
	public String scd; // 社員コード
	public Integer workId;  // 業務ID
	public String personName; // 社員名  個人名
	public String workName;  // 業務名称
	public RequestFlag requestFlag; //  届出区分
	public GeneralDate registerDate; // 入力日
	public GeneralDateTime releaseDate; // 公開日
	public OnHoldFlag onHoldFlag; // 保留フラグ
	public Status stattus; // ステータス
	public String histId_Refer; // 反映先_履歴ID

	public GeneralDateTime date_01; // retirementDate - 退職日
	public GeneralDateTime date_02; // dismissalNoticeDate - 解雇予告日
	public GeneralDateTime date_03; // dismissalNoticeDateAllow - 解雇予告手当支給日
	public GeneralDateTime date_04;
	public GeneralDateTime date_05;
	public GeneralDateTime date_06;
	public GeneralDateTime date_07;
	public GeneralDateTime date_08;
	public GeneralDateTime date_09;
	public GeneralDateTime date_10;

	public Integer int_01; // naturalUnaReasons_1 - 解雇の理由1_チェック
	public Integer int_02; // naturalUnaReasons_2 - 解雇の理由2_チェック
	public Integer int_03; // naturalUnaReasons_3 - 解雇の理由3_チェック
	public Integer int_04; // naturalUnaReasons_4 - 解雇の理由4_チェック
	public Integer int_05; // naturalUnaReasons_5 - 解雇の理由5_チェック
	public Integer int_06; // naturalUnaReasons_6 - 解雇の理由6_チェック
	public Integer int_07;
	public Integer int_08;
	public Integer int_09;
	public Integer int_10;
	public Integer int_11;
	public Integer int_12;
	public Integer int_13;
	public Integer int_14;
	public Integer int_15;
	public Integer int_16;
	public Integer int_17;
	public Integer int_18;
	public Integer int_19;
	public Integer int_20;

	public Float num_01;
	public Float num_02;
	public Float num_03;
	public Float num_04;
	public Float num_05;
	public Float num_06;
	public Float num_07;
	public Float num_08;
	public Float num_09;
	public Float num_10;
	public Float num_11;
	public Float num_12;
	public Float num_13;
	public Float num_14;
	public Float num_15;
	public Float num_16;
	public Float num_17;
	public Float num_18;
	public Float num_19;
	public Float num_20;

	public String select_code_01; // retirementCategory - 退職区分
	public String select_code_02; // retirementReasonCtgCode1 - 退職理由区分1_コード
	public String select_code_03; // retirementReasonCtgCode2 - 退職理由区分2_コード
	public String select_code_04;
	public String select_code_05;
	public String select_code_06;
	public String select_code_07;
	public String select_code_08;
	public String select_code_09;
	public String select_code_10;

	public BigInteger select_id_01;
	public BigInteger select_id_02;
	public BigInteger select_id_03;
	public BigInteger select_id_04;
	public BigInteger select_id_05;
	public BigInteger select_id_06;
	public BigInteger select_id_07;
	public BigInteger select_id_08;
	public BigInteger select_id_09;
	public BigInteger select_id_10;

	public String select_name_01; // retirementCategory - 退職区分
	public String select_name_02; // retirementReasonCtgName1 - 退職理由区分1_名称
	public String select_name_03; // retirementReasonCtgName2 - 退職理由区分2_名称
	public String select_name_04;
	public String select_name_05;
	public String select_name_06;
	public String select_name_07;
	public String select_name_08;
	public String select_name_09;
	public String select_name_10;

	public NoteRetiment str_01; // retirementRemarks - 退職の備考
	public NoteRetiment str_02; // retirementReasonVal - 自己都合退職の手続き
	public NoteRetiment str_03; // reaAndProForDis - 解雇の事由・手続き
	public NoteRetiment str_04; // naturalUnaReasons_1Val - 解雇の理由1_文字列
	public NoteRetiment str_05; // naturalUnaReasons_2Val - 解雇の理由2_文字列
	public NoteRetiment str_06; // naturalUnaReasons_3Val - 解雇の理由3_文字列
	public NoteRetiment str_07; // naturalUnaReasons_4Val - 解雇の理由4_文字列
	public NoteRetiment str_08; // naturalUnaReasons_5Val - 解雇の理由5_文字列
	public NoteRetiment str_09; // naturalUnaReasons_6Val - 解雇の理由6_文字列
	public NoteRetiment str_10;
	
	// ver 2
	// 承認者社員ID_1
	public String approveSid1;

	// 承認者社員名_1
	public String approvePerName1;

	// 承認ステータス_1
	public ApprovalStatus approveStatus1;

	// 承認コメント_1
	public String approveComment1;

	// 承認メール送信済みフラグ_1
	public boolean approveSendMailFlg1;

	// 承認日時_1
	public GeneralDateTime approveDateTime1;

	// 承認者社員ID_2
	public String approveSid2;

	// 承認者社員名_2
	public String approvePerName2;

	// 承認ステータス_2
	public ApprovalStatus approveStatus2;

	// 承認コメント_2
	public String approveComment2;

	// 承認メール送信済みフラグ_2
	public boolean approveSendMailFlg2;

	// 承認日時_2
	public GeneralDateTime approveDateTime2;

	// 個別届出種類ID
	public String rptLayoutId;

}
