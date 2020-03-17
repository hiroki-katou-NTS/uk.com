package nts.uk.ctx.hr.shared.infra.entity.databeforereflecting;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.DataBeforeReflectingPerInfo;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.OnHoldFlag;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.RequestFlag;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.Status;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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
	
	@Override
	protected Object getKey() {
		return this.preReflecDataPk;
	}

	
	public DataBeforeReflectingPerInfo toDomain() {
		return DataBeforeReflectingPerInfo.createFromJavaType(this.preReflecDataPk.historyId, this.contractCode, this.companyId, this.companyCode,
				this.pId, this.sId, this.scd, this.workId, this.personName, this.workName, EnumAdaptor.valueOf(this.requestFlag, RequestFlag.class), 
				this.registerDate, this.releaseDate,EnumAdaptor.valueOf(this.onHoldFlag, OnHoldFlag.class), 
				EnumAdaptor.valueOf(this.stattus, Status.class), this.histId_Refer, 
				this.date_01, this.date_02, this.date_03, this.date_04, this.date_05, 
				this.date_06, this.date_07, this.date_08, this.date_09, this.date_10, 
				this.int_01, this.int_02, this.int_03, this.int_04, this.int_05, 
				this.int_06, this.int_07, this.int_08, this.int_09, this.int_10, 
				this.int_11, this.int_12, this.int_13, this.int_14, this.int_15, 
				this.int_16, this.int_17, this.int_18, this.int_19, this.int_20, 
				this.num_01, this.num_02, this.num_03, this.num_04, this.num_05, 
				this.num_06, this.num_07, this.num_08, this.num_09, this.num_10, 
				this.num_11, this.num_12, this.num_13, this.num_14, this.num_15, 
				this.num_16, this.num_17, this.num_18, this.num_19, this.num_20, 
				this.select_code_01, this.select_code_02, this.select_code_03, this.select_code_04, this.select_code_05, 
				this.select_code_06, this.select_code_07, this.select_code_08, this.select_code_09, this.select_code_10, 
				this.select_id_01, this.select_id_02, this.select_id_03, this.select_id_04, this.select_id_05, 
				this.select_id_06, this.select_id_07, this.select_id_08, this.select_id_09, this.select_id_10, 
				this.select_name_01, this.select_name_02, this.select_name_03, this.select_name_04, this.select_name_05, 
				this.select_name_06, this.select_name_07, this.select_name_08, this.select_name_09, this.select_name_10, 
				this.str_01, this.str_02, this.str_03, this.str_04, this.str_05, 
				this.str_06, this.str_07, this.str_08, this.str_09, this.str_10);
	}

}
