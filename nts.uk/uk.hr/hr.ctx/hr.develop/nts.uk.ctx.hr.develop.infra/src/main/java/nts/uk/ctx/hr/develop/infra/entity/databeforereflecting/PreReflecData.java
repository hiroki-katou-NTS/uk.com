package nts.uk.ctx.hr.develop.infra.entity.databeforereflecting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PPEDT_PRE_REFLECT_DATA")
public class PreReflecData extends UkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "HIST_ID")
	public String historyId;
	
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
	
	//------------
	
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
	
	//-------------------------//
	
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
	
	//-------------------------//
	
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
	
	//----------------------------------//
	
	@Column(name = "SELECT_ID_01")
	public Integer select_id_01;
	
	@Column(name = "SELECT_ID_02")
	public Integer select_id_02;
	
	@Column(name = "SELECT_ID_03")
	public Integer select_id_03;
	
	@Column(name = "SELECT_ID_04")
	public Integer select_id_04;
	
	@Column(name = "SELECT_ID_05")
	public Integer select_id_05;
	
	@Column(name = "SELECT_ID_06")
	public Integer select_id_06;
	
	@Column(name = "SELECT_ID_07")
	public Integer select_id_07;
	
	@Column(name = "SELECT_ID_08")
	public Integer select_id_08;
	
	@Column(name = "SELECT_ID_09")
	public Integer select_id_09;
	
	@Column(name = "SELECT_ID_10")
	public Integer select_id_10;
	
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
		return this.historyId;
	}
	
}
