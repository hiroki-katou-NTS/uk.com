
package nts.uk.ctx.hr.shared.infra.entity.databeforereflecting;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.ApprovalStatus;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.DataBeforeReflectingPerInfo;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.NoteRetiment;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.OnHoldFlag;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.RequestFlag;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.Status;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PPEDT_PRE_REFLECT_DATA")
public class PreReflecData extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PreReflecDataPk preReflecDataPk;

	@Column(name = "CONTRACT_CD")
	public String contractCode;

	@Column(name = "CID")
	public String companyId;

	@Column(name = "CCD")
	public String companyCode;

	@Column(name = "PID")
	public String pId;

	@Column(name = "SID")
	public String sId;

	@Column(name = "SCD")
	public String scd;

	@Column(name = "WORK_ID")
	public Integer workId;

	@Column(name = "PERSON_NAME")
	public String personName;

	@Column(name = "WORK_NAME")
	public String workName;

	@Column(name = "REQUEST_FLG")
	public int requestFlag;

	@Column(name = "REGIST_DATE")
	public GeneralDate registerDate;

	@Column(name = "RELEASE_DATE")
	public GeneralDateTime releaseDate;

	@Column(name = "ON_HOLD_FLG")
	public int onHoldFlag;

	@Column(name = "STATUS")
	public int stattus;

	@Column(name = "DST_HIST_ID")
	public String histId_Refer;

	// ------------

	@Column(name = "DATE_01")
	public GeneralDateTime date_01;

	@Column(name = "DATE_02")
	public GeneralDateTime date_02;

	@Column(name = "DATE_03")
	public GeneralDateTime date_03;

	@Column(name = "DATE_04")
	public GeneralDateTime date_04;

	@Column(name = "DATE_05")
	public GeneralDateTime date_05;

	@Column(name = "DATE_06")
	public GeneralDateTime date_06;

	@Column(name = "DATE_07")
	public GeneralDateTime date_07;

	@Column(name = "DATE_08")
	public GeneralDateTime date_08;

	@Column(name = "DATE_09")
	public GeneralDateTime date_09;

	@Column(name = "DATE_10")
	public GeneralDateTime date_10;

	// -----------------//

	@Column(name = "INT_01")
	public Integer int_01;

	@Column(name = "INT_02")
	public Integer int_02;

	@Column(name = "INT_03")
	public Integer int_03;

	@Column(name = "INT_04")
	public Integer int_04;

	@Column(name = "INT_05")
	public Integer int_05;

	@Column(name = "INT_06")
	public Integer int_06;

	@Column(name = "INT_07")
	public Integer int_07;

	@Column(name = "INT_08")
	public Integer int_08;

	@Column(name = "INT_09")
	public Integer int_09;

	@Column(name = "INT_10")
	public Integer int_10;

	@Column(name = "INT_11")
	public Integer int_11;

	@Column(name = "INT_12")
	public Integer int_12;

	@Column(name = "INT_13")
	public Integer int_13;

	@Column(name = "INT_14")
	public Integer int_14;

	@Column(name = "INT_15")
	public Integer int_15;

	@Column(name = "INT_16")
	public Integer int_16;

	@Column(name = "INT_17")
	public Integer int_17;

	@Column(name = "INT_18")
	public Integer int_18;

	@Column(name = "INT_19")
	public Integer int_19;

	@Column(name = "INT_20")
	public Integer int_20;

	// -------------------------//

	@Column(name = "NUM_01")
	public Float num_01;

	@Column(name = "NUM_02")
	public Float num_02;

	@Column(name = "NUM_03")
	public Float num_03;

	@Column(name = "NUM_04")
	public Float num_04;

	@Column(name = "NUM_05")
	public Float num_05;

	@Column(name = "NUM_06")
	public Float num_06;

	@Column(name = "NUM_07")
	public Float num_07;

	@Column(name = "NUM_08")
	public Float num_08;

	@Column(name = "NUM_09")
	public Float num_09;

	@Column(name = "NUM_10")
	public Float num_10;

	@Column(name = "NUM_11")
	public Float num_11;

	@Column(name = "NUM_12")
	public Float num_12;

	@Column(name = "NUM_13")
	public Float num_13;

	@Column(name = "NUM_14")
	public Float num_14;

	@Column(name = "NUM_15")
	public Float num_15;

	@Column(name = "NUM_16")
	public Float num_16;

	@Column(name = "NUM_17")
	public Float num_17;

	@Column(name = "NUM_18")
	public Float num_18;

	@Column(name = "NUM_19")
	public Float num_19;

	@Column(name = "NUM_20")
	public Float num_20;

	// -------------------------//

	@Column(name = "SELECT_CODE_01")
	public String select_code_01;

	@Column(name = "SELECT_CODE_02")
	public String select_code_02;

	@Column(name = "SELECT_CODE_03")
	public String select_code_03;

	@Column(name = "SELECT_CODE_04")
	public String select_code_04;

	@Column(name = "SELECT_CODE_05")
	public String select_code_05;

	@Column(name = "SELECT_CODE_06")
	public String select_code_06;

	@Column(name = "SELECT_CODE_07")
	public String select_code_07;

	@Column(name = "SELECT_CODE_08")
	public String select_code_08;

	@Column(name = "SELECT_CODE_09")
	public String select_code_09;

	@Column(name = "SELECT_CODE_10")
	public String select_code_10;

	// ----------------------------------//

	@Column(name = "SELECT_ID_01")
	public BigInteger select_id_01;

	@Column(name = "SELECT_ID_02")
	public BigInteger select_id_02;

	@Column(name = "SELECT_ID_03")
	public BigInteger select_id_03;

	@Column(name = "SELECT_ID_04")
	public BigInteger select_id_04;

	@Column(name = "SELECT_ID_05")
	public BigInteger select_id_05;

	@Column(name = "SELECT_ID_06")
	public BigInteger select_id_06;

	@Column(name = "SELECT_ID_07")
	public BigInteger select_id_07;

	@Column(name = "SELECT_ID_08")
	public BigInteger select_id_08;

	@Column(name = "SELECT_ID_09")
	public BigInteger select_id_09;

	@Column(name = "SELECT_ID_10")
	public BigInteger select_id_10;

	// -------------------------------//

	@Column(name = "SELECT_NAME_01")
	public String select_name_01;

	@Column(name = "SELECT_NAME_02")
	public String select_name_02;

	@Column(name = "SELECT_NAME_03")
	public String select_name_03;

	@Column(name = "SELECT_NAME_04")
	public String select_name_04;

	@Column(name = "SELECT_NAME_05")
	public String select_name_05;

	@Column(name = "SELECT_NAME_06")
	public String select_name_06;

	@Column(name = "SELECT_NAME_07")
	public String select_name_07;

	@Column(name = "SELECT_NAME_08")
	public String select_name_08;

	@Column(name = "SELECT_NAME_09")
	public String select_name_09;

	@Column(name = "SELECT_NAME_10")
	public String select_name_10;

	// --------------------------------//

	@Column(name = "STR_01")
	public String str_01;

	@Column(name = "STR_02")
	public String str_02;

	@Column(name = "STR_03")
	public String str_03;

	@Column(name = "STR_04")
	public String str_04;

	@Column(name = "STR_05")
	public String str_05;

	@Column(name = "STR_06")
	public String str_06;

	@Column(name = "STR_07")
	public String str_07;

	@Column(name = "STR_08")
	public String str_08;

	@Column(name = "STR_09")
	public String str_09;

	@Column(name = "STR_10")
	public String str_10;

	// ???????????????ID_1
	@Column(name = "APPROVE_S_ID_1")
	public String approveSid1;

	// ??????????????????_1
	@Column(name = "APPROVE_PERSON_NAME_1")
	public String approvePerName1;

	// ?????????????????????_1
	@Column(name = "APPROVE_STATUS_1")
	public Integer approveStatus1;

	// ??????????????????_1
	@Column(name = "APPROVE_COMMENT_1")
	public String approveComment1;

	// ????????????????????????????????????_1
	@Column(name = "APPROVE_SEND_MAIL_FLG_1")
	public boolean approveSendMailFlg1;

	// ????????????_1
	@Column(name = "APPROVE_DATETIME_1")
	public GeneralDateTime approveDateTime1;

	// ???????????????ID_2
	@Column(name = "APPROVE_S_ID_2")
	public String approveSid2;

	// ??????????????????_2
	@Column(name = "APPROVE_PERSON_NAME_2")
	public String approvePerName2;

	// ?????????????????????_2
	@Column(name = "APPROVE_STATUS_2")
	public Integer approveStatus2;

	// ??????????????????_2
	@Column(name = "APPROVE_COMMENT_2")
	public String approveComment2;

	// ????????????????????????????????????_2
	@Column(name = "APPROVE_SEND_MAIL_FLG_2")
	public boolean approveSendMailFlg2;

	// ????????????_2
	@Column(name = "APPROVE_DATETIME_2")
	public GeneralDateTime approveDateTime2;

	// ??????????????????ID
	@Column(name = "RPT_LAYOUT_ID")
	public String rptLayoutId;

	@Override
	protected Object getKey() {
		return this.preReflecDataPk;
	}

	public DataBeforeReflectingPerInfo toDomain() {

		return DataBeforeReflectingPerInfo.builder().historyId(this.preReflecDataPk.historyId)
				.companyCode(this.contractCode).companyId(this.companyId).companyCode(this.companyCode).pId(this.pId)
				.sId(this.sId).scd(this.scd).workId(this.workId).personName(this.personName).workName(this.workName)
				.requestFlag(EnumAdaptor.valueOf(this.requestFlag, RequestFlag.class)).registerDate(this.registerDate)
				.releaseDate(this.releaseDate).onHoldFlag(EnumAdaptor.valueOf(this.onHoldFlag, OnHoldFlag.class))
				.stattus(EnumAdaptor.valueOf(this.stattus, Status.class)).histId_Refer(this.histId_Refer)
				.date_01(this.date_01).date_02(this.date_02).date_03(this.date_03).date_04(this.date_04)
				.date_05(this.date_05).date_06(this.date_06).date_07(this.date_07).date_08(this.date_08)
				.date_09(this.date_09).date_10(this.date_10).int_01(this.int_01).int_02(this.int_02).int_03(this.int_03)
				.int_04(this.int_04).int_05(this.int_05).int_06(this.int_06).int_07(this.int_07).int_08(this.int_08)
				.int_09(this.int_09).int_10(this.int_10).int_11(this.int_11).int_12(this.int_12).int_13(this.int_13)
				.int_14(this.int_14).int_15(this.int_15).int_16(this.int_16).int_17(this.int_17).int_18(this.int_18)
				.int_19(this.int_19).int_20(this.int_20).num_01(this.num_01).num_02(this.num_02).num_03(this.num_03)
				.num_04(this.num_04).num_05(this.num_05).num_06(this.num_06).num_07(this.num_07).num_08(this.num_08)
				.num_09(this.num_09).num_10(this.num_10).num_11(this.num_11).num_12(this.num_12).num_13(this.num_13)
				.num_14(this.num_14).num_15(this.num_15).num_16(this.num_16).num_17(this.num_17).num_18(this.num_18)
				.num_19(this.num_19).num_20(this.num_20).select_code_01(this.select_code_01)
				.select_code_02(this.select_code_02).select_code_03(this.select_code_03)
				.select_code_04(this.select_code_04).select_code_05(this.select_code_05)
				.select_code_06(this.select_code_06).select_code_07(this.select_code_07)
				.select_code_08(this.select_code_08).select_code_09(this.select_code_09)
				.select_code_10(this.select_code_10).select_id_01(this.select_id_01).select_id_02(this.select_id_02)
				.select_id_03(this.select_id_03).select_id_04(this.select_id_04).select_id_05(this.select_id_05)
				.select_id_06(this.select_id_06).select_id_07(this.select_id_07).select_id_08(this.select_id_08)
				.select_id_09(this.select_id_09).select_id_10(this.select_id_10).select_name_01(this.select_name_01)
				.select_name_02(this.select_name_02).select_name_03(this.select_name_03)
				.select_name_04(this.select_name_04).select_name_05(this.select_name_05)
				.select_name_06(this.select_name_06).select_name_07(this.select_name_07)
				.select_name_08(this.select_name_08).select_name_09(this.select_name_09)
				.select_name_10(this.select_name_10).str_01(new NoteRetiment(this.str_01))
				.str_02(new NoteRetiment(this.str_02)).str_03(new NoteRetiment(this.str_03))
				.str_04(new NoteRetiment(this.str_04)).str_05(new NoteRetiment(this.str_05))
				.str_06(new NoteRetiment(this.str_06)).str_07(new NoteRetiment(this.str_07))
				.str_08(new NoteRetiment(this.str_08)).str_09(new NoteRetiment(this.str_09))
				.str_10(new NoteRetiment(this.str_10)).approveSid1(this.approveSid1)
				.approvePerName1(this.approvePerName1)
				.approveStatus1(this.approveStatus1 == null ? null : EnumAdaptor.valueOf(this.approveStatus1, ApprovalStatus.class))
				.approveComment1(this.approveComment1)
				.approveSendMailFlg1(this.approveSendMailFlg1)
				.approveDateTime1(this.approveDateTime1)
				.approveSid2(this.approveSid2)
				.approvePerName2(this.approvePerName2)
				.approveStatus2(this.approveStatus2 == null ? null : EnumAdaptor.valueOf(this.approveStatus2, ApprovalStatus.class))
				.approveComment2(this.approveComment2)
				.approveSendMailFlg2(this.approveSendMailFlg2)
				.approveDateTime2(this.approveDateTime2)
				.rptLayoutId(this.rptLayoutId).build();
	}
	
	public void updateEntity(DataBeforeReflectingPerInfo domain) {
		this.stattus = domain.stattus.value;

		this.releaseDate = domain.releaseDate;
		this.onHoldFlag = domain.onHoldFlag.value;
		this.registerDate = domain.registerDate;

		this.select_id_01 = domain.select_id_01; // retirementCategory
		this.select_id_02 = domain.select_id_02; // retirementCategory
		this.select_id_03 = domain.select_id_03; // retirementCategory
		this.select_id_04 = domain.select_id_04; // retirementCategory
		this.select_id_05 = domain.select_id_05; // retirementCategory
		this.select_id_06 = domain.select_id_06; // retirementCategory
		this.select_id_07 = domain.select_id_07; // retirementCategory
		this.select_id_08 = domain.select_id_08; // retirementCategory
		this.select_id_09 = domain.select_id_09; // retirementCategory
		this.select_id_10 = domain.select_id_10; // retirementCategory

		this.select_code_01 = domain.select_code_01; // retirementCategory
		this.select_code_02 = domain.select_code_02; // retirementCategory
		this.select_code_03 = domain.select_code_03; // retirementReasonCtgCode1
		this.select_code_04 = domain.select_code_04; // retirementReasonCtgCode1
		this.select_code_05 = domain.select_code_05; // retirementReasonCtgCode1
		this.select_code_06 = domain.select_code_06; // retirementReasonCtgCode1
		this.select_code_07 = domain.select_code_07; // retirementReasonCtgCode1
		this.select_code_08 = domain.select_code_08; // retirementReasonCtgCode1
		this.select_code_09 = domain.select_code_09; // retirementReasonCtgCode1
		this.select_code_10 = domain.select_code_10; // retirementReasonCtgCode1

		this.select_name_01 = domain.select_name_01; // retirementReasonCtgName1
		this.select_name_02 = domain.select_name_02; // retirementReasonCtgName2
		this.select_name_03 = domain.select_name_03; // retirementReasonCtgName2
		this.select_name_04 = domain.select_name_04; // retirementReasonCtgName1
		this.select_name_05 = domain.select_name_05; // retirementReasonCtgName2
		this.select_name_06 = domain.select_name_06; // retirementReasonCtgName2
		this.select_name_07 = domain.select_name_07; // retirementReasonCtgName1
		this.select_name_08 = domain.select_name_08; // retirementReasonCtgName2
		this.select_name_09 = domain.select_name_09; // retirementReasonCtgName2
		this.select_name_10 = domain.select_name_10; // retirementReasonCtgName2

		this.str_01 = domain.str_01 == null ? null : domain.str_01.toString(); // retirementRemarks
		this.str_02 = domain.str_02 == null ? null : domain.str_02.toString(); // retirementReasonVal
		this.str_03 = domain.str_03 == null ? null : domain.str_03.toString(); // retirementRemarks
		this.str_04 = domain.str_04 == null ? null : domain.str_04.toString(); // retirementReasonVal
		this.str_05 = domain.str_05 == null ? null : domain.str_05.toString(); // retirementRemarks
		this.str_06 = domain.str_06 == null ? null : domain.str_06.toString(); // retirementReasonVal
		this.str_07 = domain.str_07 == null ? null : domain.str_07.toString(); // retirementRemarks
		this.str_08 = domain.str_08 == null ? null : domain.str_08.toString(); // retirementReasonVal
		this.str_09 = domain.str_09 == null ? null : domain.str_09.toString(); // retirementRemarks
		this.str_10 = domain.str_10 == null ? null : domain.str_10.toString(); // retirementReasonVal

		this.date_01 = domain.date_01; // retirementDate
		this.date_02 = domain.date_02; // dismissalNoticeDate
		this.date_03 = domain.date_03; // dismissalNoticeDateAllow
		this.date_04 = domain.date_04; // dismissalNoticeDate
		this.date_05 = domain.date_05; // dismissalNoticeDateAllow
		this.date_06 = domain.date_06; // dismissalNoticeDate
		this.date_07 = domain.date_07; // dismissalNoticeDateAllow
		this.date_08 = domain.date_08; // dismissalNoticeDate
		this.date_09 = domain.date_09; // dismissalNoticeDateAllow
		this.date_10 = domain.date_10; // dismissalNoticeDate

		this.int_01 = domain.int_01;
		this.int_02 = domain.int_02;
		this.int_03 = domain.int_03;
		this.int_04 = domain.int_04;
		this.int_05 = domain.int_05;
		this.int_06 = domain.int_06;
		this.int_07 = domain.int_07;
		this.int_08 = domain.int_08;
		this.int_09 = domain.int_09;
		this.int_10 = domain.int_10;

		// ver 2
		this.approveSid1 = domain.approveSid1;
		this.approvePerName1 = domain.approvePerName1;
		this.approveStatus1 = domain.approveStatus1 == null ? null : domain.approveStatus1.value;
		this.approveComment1 = domain.approveComment1;
		this.approveSendMailFlg1 = domain.approveSendMailFlg1;
		this.approveDateTime1 = domain.approveDateTime1;
		this.approveSid2 = domain.approveSid2;
		this.approvePerName2 = domain.approvePerName2;
		this.approveStatus2 = domain.approveStatus2 == null ? null : domain.approveStatus2.value;
		this.approveComment2 = domain.approveComment2;
		this.approveSendMailFlg2 = domain.approveSendMailFlg2;
		this.approveDateTime2 = domain.approveDateTime2;
		this.rptLayoutId = domain.rptLayoutId;
	}
	
	public static PreReflecData fromDomain(DataBeforeReflectingPerInfo domain) {
		PreReflecData entity = PreReflecData.builder().build();
		entity.preReflecDataPk = new PreReflecDataPk(domain.historyId);
		entity.contractCode = domain.contractCode;
		entity.companyId = domain.companyId;
		entity.companyCode = domain.companyCode;
		entity.pId = domain.pId;
		entity.sId = domain.sId;
		entity.scd = domain.scd;
		entity.workId = domain.workId;
		entity.personName = domain.personName;
		entity.workName = domain.workName;
		entity.requestFlag = domain.requestFlag.value;
		entity.registerDate = domain.registerDate;
		entity.releaseDate = domain.releaseDate;
		entity.onHoldFlag = domain.onHoldFlag.value;
		entity.stattus = domain.stattus.value;
		entity.histId_Refer = domain.histId_Refer;
		entity.date_01 = domain.date_01;
		entity.date_02 = domain.date_02;
		entity.date_03 = domain.date_03;
		entity.date_04 = domain.date_04;
		entity.date_05 = domain.date_05;
		entity.date_06 = domain.date_06;
		entity.date_07 = domain.date_07;
		entity.date_08 = domain.date_08;
		entity.date_09 = domain.date_09;
		entity.date_10 = domain.date_10;
		entity.int_01 = domain.int_01;
		entity.int_02 = domain.int_02;
		entity.int_03 = domain.int_03;
		entity.int_04 = domain.int_04;
		entity.int_05 = domain.int_05;
		entity.int_06 = domain.int_06;
		entity.int_07 = domain.int_07;
		entity.int_08 = domain.int_08;
		entity.int_09 = domain.int_09;
		entity.int_10 = domain.int_10;
		entity.int_11 = domain.int_11;
		entity.int_12 = domain.int_12;
		entity.int_13 = domain.int_13;
		entity.int_14 = domain.int_14;
		entity.int_15 = domain.int_15;
		entity.int_16 = domain.int_16;
		entity.int_17 = domain.int_17;
		entity.int_18 = domain.int_18;
		entity.int_19 = domain.int_19;
		entity.int_20 = domain.int_20;

		entity.num_01 = domain.num_01;
		entity.num_02 = domain.num_02;
		entity.num_03 = domain.num_03;
		entity.num_04 = domain.num_04;
		entity.num_05 = domain.num_05;
		entity.num_06 = domain.num_06;
		entity.num_07 = domain.num_07;
		entity.num_08 = domain.num_08;
		entity.num_09 = domain.num_09;
		entity.num_10 = domain.num_10;
		entity.num_11 = domain.num_11;
		entity.num_12 = domain.num_12;
		entity.num_13 = domain.num_13;
		entity.num_14 = domain.num_14;
		entity.num_15 = domain.num_15;
		entity.num_16 = domain.num_16;
		entity.num_17 = domain.num_17;
		entity.num_18 = domain.num_18;
		entity.num_19 = domain.num_19;
		entity.num_20 = domain.num_20;

		entity.select_code_01 = domain.select_code_01;
		entity.select_code_02 = domain.select_code_02;
		entity.select_code_03 = domain.select_code_03;
		entity.select_code_04 = domain.select_code_04;
		entity.select_code_05 = domain.select_code_05;
		entity.select_code_06 = domain.select_code_06;
		entity.select_code_07 = domain.select_code_07;
		entity.select_code_08 = domain.select_code_08;
		entity.select_code_09 = domain.select_code_09;
		entity.select_code_10 = domain.select_code_10;
		entity.select_id_01 = domain.select_id_01;
		entity.select_id_02 = domain.select_id_02;
		entity.select_id_03 = domain.select_id_03;
		entity.select_id_04 = domain.select_id_04;
		entity.select_id_05 = domain.select_id_05;
		entity.select_id_06 = domain.select_id_06;
		entity.select_id_07 = domain.select_id_07;
		entity.select_id_08 = domain.select_id_08;
		entity.select_id_09 = domain.select_id_09;
		entity.select_id_10 = domain.select_id_10;
		entity.select_name_01 = domain.select_name_01;
		entity.select_name_02 = domain.select_name_02;
		entity.select_name_03 = domain.select_name_03;
		entity.select_name_04 = domain.select_name_04;
		entity.select_name_05 = domain.select_name_05;
		entity.select_name_06 = domain.select_name_06;
		entity.select_name_07 = domain.select_name_07;
		entity.select_name_08 = domain.select_name_08;
		entity.select_name_09 = domain.select_name_09;
		entity.select_name_10 = domain.select_name_10;
		entity.str_01 = domain.str_01 == null ? null : domain.str_01.toString();
		entity.str_02 = domain.str_02 == null ? null : domain.str_02.toString();
		entity.str_03 = domain.str_03 == null ? null : domain.str_03.toString();
		entity.str_04 = domain.str_04 == null ? null : domain.str_04.toString();
		entity.str_05 = domain.str_05 == null ? null : domain.str_05.toString();
		entity.str_06 = domain.str_06 == null ? null : domain.str_06.toString();
		entity.str_07 = domain.str_07 == null ? null : domain.str_07.toString();
		entity.str_08 = domain.str_08 == null ? null : domain.str_08.toString();
		entity.str_09 = domain.str_09 == null ? null : domain.str_09.toString();
		entity.str_10 = domain.str_10 == null ? null : domain.str_09.toString();

		// ver 2
		entity.approveSid1 = domain.approveSid1;
		entity.approvePerName1 = domain.approvePerName1;
		entity.approveStatus1 = domain.approveStatus1 == null ? null : domain.approveStatus1.value;
		entity.approveComment1 = domain.approveComment1;
		entity.approveSendMailFlg1 = domain.approveSendMailFlg1;
		entity.approveDateTime1 = domain.approveDateTime1;
		entity.approveSid2 = domain.approveSid2;
		entity.approvePerName2 = domain.approvePerName2;
		entity.approveStatus2 = domain.approveStatus2 == null ? null : domain.approveStatus2.value;
		entity.approveComment2 = domain.approveComment2;
		entity.approveSendMailFlg2 = domain.approveSendMailFlg2;
		entity.approveDateTime2 = domain.approveDateTime2;
		entity.rptLayoutId = domain.rptLayoutId;
		return entity;

	}
	
}