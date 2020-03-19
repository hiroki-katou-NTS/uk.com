/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.databeforereflecting.common;

import java.math.BigInteger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * 個人情報反映前データ
 * path : UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報反映前データ.個人情報反映前データ_共通.個人情報反映前データ
 */
@NoArgsConstructor
@Getter
@Setter
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
	public String str_10;

	public DataBeforeReflectingPerInfo(String historyId, String contractCode, String companyId, String companyCode,
			String pId, String sId, String scd, Integer workId, String personName, String workName,
			RequestFlag requestFlag, GeneralDate registerDate, GeneralDateTime releaseDate, OnHoldFlag onHoldFlag,
			Status stattus, String histId_Refer, GeneralDateTime date_01, GeneralDateTime date_02,
			GeneralDateTime date_03, GeneralDateTime date_04, GeneralDateTime date_05, GeneralDateTime date_06,
			GeneralDateTime date_07, GeneralDateTime date_08, GeneralDateTime date_09, GeneralDateTime date_10,
			Integer int_01, Integer int_02, Integer int_03, Integer int_04, Integer int_05, Integer int_06,
			Integer int_07, Integer int_08, Integer int_09, Integer int_10, Integer int_11, Integer int_12,
			Integer int_13, Integer int_14, Integer int_15, Integer int_16, Integer int_17, Integer int_18,
			Integer int_19, Integer int_20, Float num_01, Float num_02, Float num_03, Float num_04, Float num_05,
			Float num_06, Float num_07, Float num_08, Float num_09, Float num_10, Float num_11, Float num_12,
			Float num_13, Float num_14, Float num_15, Float num_16, Float num_17, Float num_18, Float num_19,
			Float num_20, String select_code_01, String select_code_02, String select_code_03, String select_code_04,
			String select_code_05, String select_code_06, String select_code_07, String select_code_08,
			String select_code_09, String select_code_10, BigInteger select_id_01, BigInteger select_id_02,
			BigInteger select_id_03, BigInteger select_id_04, BigInteger select_id_05, BigInteger select_id_06,
			BigInteger select_id_07, BigInteger select_id_08, BigInteger select_id_09, BigInteger select_id_10,
			String select_name_01, String select_name_02, String select_name_03, String select_name_04,
			String select_name_05, String select_name_06, String select_name_07, String select_name_08,
			String select_name_09, String select_name_10, String str_01, String str_02, String str_03, String str_04,
			String str_05, String str_06, String str_07, String str_08, String str_09, String str_10) {
		super();
		this.historyId = historyId;
		this.contractCode = contractCode;
		this.companyId = companyId;
		this.companyCode = companyCode;
		this.pId = pId;
		this.sId = sId;
		this.scd = scd;
		this.workId = workId;
		this.personName = personName;
		this.workName = workName;
		this.requestFlag = requestFlag;
		this.registerDate = registerDate;
		this.releaseDate = releaseDate;
		this.onHoldFlag = onHoldFlag;
		this.stattus = stattus;
		this.histId_Refer = histId_Refer;
		this.date_01 = date_01;
		this.date_02 = date_02;
		this.date_03 = date_03;
		this.date_04 = date_04;
		this.date_05 = date_05;
		this.date_06 = date_06;
		this.date_07 = date_07;
		this.date_08 = date_08;
		this.date_09 = date_09;
		this.date_10 = date_10;
		this.int_01 = int_01;
		this.int_02 = int_02;
		this.int_03 = int_03;
		this.int_04 = int_04;
		this.int_05 = int_05;
		this.int_06 = int_06;
		this.int_07 = int_07;
		this.int_08 = int_08;
		this.int_09 = int_09;
		this.int_10 = int_10;
		this.int_11 = int_11;
		this.int_12 = int_12;
		this.int_13 = int_13;
		this.int_14 = int_14;
		this.int_15 = int_15;
		this.int_16 = int_16;
		this.int_17 = int_17;
		this.int_18 = int_18;
		this.int_19 = int_19;
		this.int_20 = int_20;
		this.num_01 = num_01;
		this.num_02 = num_02;
		this.num_03 = num_03;
		this.num_04 = num_04;
		this.num_05 = num_05;
		this.num_06 = num_06;
		this.num_07 = num_07;
		this.num_08 = num_08;
		this.num_09 = num_09;
		this.num_10 = num_10;
		this.num_11 = num_11;
		this.num_12 = num_12;
		this.num_13 = num_13;
		this.num_14 = num_14;
		this.num_15 = num_15;
		this.num_16 = num_16;
		this.num_17 = num_17;
		this.num_18 = num_18;
		this.num_19 = num_19;
		this.num_20 = num_20;
		this.select_code_01 = select_code_01;
		this.select_code_02 = select_code_02;
		this.select_code_03 = select_code_03;
		this.select_code_04 = select_code_04;
		this.select_code_05 = select_code_05;
		this.select_code_06 = select_code_06;
		this.select_code_07 = select_code_07;
		this.select_code_08 = select_code_08;
		this.select_code_09 = select_code_09;
		this.select_code_10 = select_code_10;
		this.select_id_01 = select_id_01;
		this.select_id_02 = select_id_02;
		this.select_id_03 = select_id_03;
		this.select_id_04 = select_id_04;
		this.select_id_05 = select_id_05;
		this.select_id_06 = select_id_06;
		this.select_id_07 = select_id_07;
		this.select_id_08 = select_id_08;
		this.select_id_09 = select_id_09;
		this.select_id_10 = select_id_10;
		this.select_name_01 = select_name_01;
		this.select_name_02 = select_name_02;
		this.select_name_03 = select_name_03;
		this.select_name_04 = select_name_04;
		this.select_name_05 = select_name_05;
		this.select_name_06 = select_name_06;
		this.select_name_07 = select_name_07;
		this.select_name_08 = select_name_08;
		this.select_name_09 = select_name_09;
		this.select_name_10 = select_name_10;
		this.str_01 = new NoteRetiment(str_01);
		this.str_02 = new NoteRetiment(str_02);
		this.str_03 = new NoteRetiment(str_03);
		this.str_04 = new NoteRetiment(str_04);
		this.str_05 = new NoteRetiment(str_05);
		this.str_06 = new NoteRetiment(str_06);
		this.str_07 = new NoteRetiment(str_07);
		this.str_08 = new NoteRetiment(str_08);
		this.str_09 = new NoteRetiment(str_09);
		this.str_10 = str_10;
	}

	public static DataBeforeReflectingPerInfo createFromJavaType(String historyId, String contractCode,
			String companyId, String companyCode, String pId, String sId, String scd, Integer workId, String personName,
			String workName, RequestFlag requestFlag, GeneralDate registerDate, GeneralDateTime releaseDate,
			OnHoldFlag onHoldFlag, Status stattus, String histId_Refer, GeneralDateTime date_01,
			GeneralDateTime date_02, GeneralDateTime date_03, GeneralDateTime date_04, GeneralDateTime date_05,
			GeneralDateTime date_06, GeneralDateTime date_07, GeneralDateTime date_08, GeneralDateTime date_09,
			GeneralDateTime date_10, Integer int_01, Integer int_02, Integer int_03, Integer int_04, Integer int_05,
			Integer int_06, Integer int_07, Integer int_08, Integer int_09, Integer int_10, Integer int_11,
			Integer int_12, Integer int_13, Integer int_14, Integer int_15, Integer int_16, Integer int_17,
			Integer int_18, Integer int_19, Integer int_20, Float num_01, Float num_02, Float num_03, Float num_04,
			Float num_05, Float num_06, Float num_07, Float num_08, Float num_09, Float num_10, Float num_11,
			Float num_12, Float num_13, Float num_14, Float num_15, Float num_16, Float num_17, Float num_18,
			Float num_19, Float num_20, String select_code_01, String select_code_02, String select_code_03,
			String select_code_04, String select_code_05, String select_code_06, String select_code_07,
			String select_code_08, String select_code_09, String select_code_10, BigInteger select_id_01,
			BigInteger select_id_02, BigInteger select_id_03, BigInteger select_id_04, BigInteger select_id_05,
			BigInteger select_id_06, BigInteger select_id_07, BigInteger select_id_08, BigInteger select_id_09,
			BigInteger select_id_10, String select_name_01, String select_name_02, String select_name_03,
			String select_name_04, String select_name_05, String select_name_06, String select_name_07,
			String select_name_08, String select_name_09, String select_name_10, String str_01, String str_02,
			String str_03, String str_04, String str_05, String str_06, String str_07, String str_08, String str_09,
			String str_10) {
		return new DataBeforeReflectingPerInfo(historyId, contractCode, companyId, companyCode, pId, sId, scd, workId,
				personName, workName, requestFlag, registerDate, releaseDate, onHoldFlag, stattus, histId_Refer,
				date_01, date_02, date_03, date_04, date_05, date_06, date_07, date_08, date_09, date_10,
				int_01,int_02, int_03, int_04, int_05, int_06, int_07, int_08, int_09, int_10, int_11, int_12, int_13, int_14,
				int_15, int_16, int_17, int_18, int_19, int_20,
				num_01, num_02, num_03, num_04, num_05, num_06, num_07,num_08, num_09, num_10, 
				num_11, num_12, num_13, num_14, num_15, num_16, num_17, num_18, num_19, num_20,
				select_code_01, select_code_02, select_code_03, select_code_04, select_code_05, select_code_06,
				select_code_07, select_code_08, select_code_09, select_code_10, 
				select_id_01, select_id_02, select_id_03, select_id_04, select_id_05,
				select_id_06, select_id_07, select_id_08, select_id_09,select_id_10, 
				select_name_01, select_name_02, select_name_03, select_name_04, select_name_05,
				select_name_06, select_name_07, select_name_08, select_name_09, select_name_10, 
				str_01, str_02, str_03,str_04, str_05, str_06, str_07, str_08, str_09, str_10);
	}

	public DataBeforeReflectingPerInfo(String historyId, String contractCode, String companyId, String companyCode,
			String pId, String sId, String scd, Integer workId, String personName, String workName,
			int requestFlag, GeneralDate registerDate, GeneralDateTime releaseDate, int onHoldFlag, int status,
			String histId_Refer, GeneralDateTime date_01, String select_code_01, String select_code_02,
			String select_code_03, String select_code_04, String select_name_01, String select_name_02,
			String select_name_03) {
		this.historyId = historyId;
		this.contractCode = contractCode;
		this.companyId = companyId;
		this.companyCode = companyCode;
		this.pId = pId;
		this.sId = sId;
		this.scd = scd;
		this.workId = workId;
		this.personName = personName;
		this.workName = workName;
		this.requestFlag = EnumAdaptor.valueOf(requestFlag, RequestFlag.class) ;
		this.registerDate = registerDate;
		this.releaseDate = releaseDate;
		this.onHoldFlag = EnumAdaptor.valueOf(onHoldFlag,OnHoldFlag.class) ;
		this.stattus =   EnumAdaptor.valueOf(status,Status.class) ;
		this.histId_Refer = histId_Refer;
		this.date_01 = date_01;
		this.select_code_01 = select_code_01;
		this.select_code_02 = select_code_02;
		this.select_code_03 = select_code_03;
		this.select_code_04 = select_code_04;
		this.select_name_01 = select_name_01;
		this.select_name_02 = select_name_02;
		this.select_name_03 = select_name_03;
	}

}
