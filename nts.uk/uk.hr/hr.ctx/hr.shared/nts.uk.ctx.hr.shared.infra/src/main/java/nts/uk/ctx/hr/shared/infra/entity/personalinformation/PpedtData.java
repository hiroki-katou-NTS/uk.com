package nts.uk.ctx.hr.shared.infra.entity.personalinformation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.personalinformation.PersonalInformation;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author chungnt
 *
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEDT_DATA")
public class PpedtData extends UkJpaEntity implements Serializable {
	
	@Id
	@Column(name = "HIST_ID")
	@Basic(optional = false)
	public String hisId;

	@Column(name = "CONTRACT_CD")
	@Basic(optional = false)
	public String contractCd;
	
	@Column(name = "CID")
	@Basic(optional = true)
	public String cId;
	
	@Column(name = "CCD")
	@Basic(optional = true)
	public String ccd;

	@Column(name = "PID")
	@Basic(optional = false)
	public String pId;
	
	@Column(name = "SID")
	@Basic(optional = true)
	public String sid;

	@Column(name = "SCD")
	@Basic(optional = true)
	public String scd;

	@Column(name = "PERSON_NAME")
	@Basic(optional = true)
	public String personName;
	
	@Column(name = "WORK_ID")
	@Basic(optional = false)
	public int workId;
	
	@Column(name = "WORK_NAME")
	@Basic(optional = true)
	public String workName;
	
	@Column(name = "START_DATE")
	@Basic(optional = false)
	public GeneralDate startDate;
	
	@Column(name = "END_DATE")
	@Basic(optional = false)
	public GeneralDate endDate;
	
	@Column(name = "RELEASE_DATE")
	@Basic(optional = true)
	public GeneralDate releaseDate;
	
	@Column(name = "REQUEST_FLG")
	@Basic(optional = true)
	public int requestFlg;
	
	@Column(name = "RPT_LAYOUT_ID")
	@Basic(optional = true)
	public int rptLa;
	
	@Column(name = "RPTID")
	@Basic(optional = true)
	public int rptId;
	
	@Column(name = "RPT_NUMBER")
	@Basic(optional = true)
	public String rptNumber;
	
	@Column(name = "STR_01")
	@Basic(optional = true)
	public String str01;
	
	@Column(name = "STR_02")
	@Basic(optional = true)
	public String str02;
	
	@Column(name = "STR_03")
	@Basic(optional = true)
	public String str03;
	
	@Column(name = "STR_04")
	@Basic(optional = true)
	public String str04;
	
	@Column(name = "STR_05")
	@Basic(optional = true)
	public String str05;
	
	@Column(name = "STR_06")
	@Basic(optional = true)
	public String str06;
	
	@Column(name = "STR_07")
	@Basic(optional = true)
	public String str07;
	
	@Column(name = "STR_08")
	@Basic(optional = true)
	public String str08;
	
	@Column(name = "STR_09")
	@Basic(optional = true)
	public String str09;
	
	@Column(name = "STR_10")
	@Basic(optional = true)
	public String str10;
	
	@Column(name = "STR_11")
	@Basic(optional = true)
	public String str11;
	
	@Column(name = "STR_12")
	@Basic(optional = true)
	public String str12;
	
	@Column(name = "STR_13")
	@Basic(optional = true)
	public String str13;
	
	@Column(name = "STR_14")
	@Basic(optional = true)
	public String str14;
	
	@Column(name = "STR_15")
	@Basic(optional = true)
	public String str15;
	
	@Column(name = "STR_16")
	@Basic(optional = true)
	public String str16;
	
	@Column(name = "STR_17")
	@Basic(optional = true)
	public String str17;
	
	@Column(name = "STR_18")
	@Basic(optional = true)
	public String str18;
	
	@Column(name = "STR_19")
	@Basic(optional = true)
	public String str19;
	
	@Column(name = "STR_20")
	@Basic(optional = true)
	public String str20;
	
	@Column(name = "STR_21")
	@Basic(optional = true)
	public String str21;
	
	@Column(name = "STR_22")
	@Basic(optional = true)
	public String str22;
	
	@Column(name = "STR_23")
	@Basic(optional = true)
	public String str23;
	
	@Column(name = "STR_24")
	@Basic(optional = true)
	public String str24;
	
	@Column(name = "STR_25")
	@Basic(optional = true)
	public String str25;
	
	@Column(name = "STR_26")
	@Basic(optional = true)
	public String str26;
	
	@Column(name = "STR_27")
	@Basic(optional = true)
	public String str27;
	
	@Column(name = "STR_28")
	@Basic(optional = true)
	public String str28;
	
	@Column(name = "STR_29")
	@Basic(optional = true)
	public String str29;
	
	@Column(name = "STR_30")
	@Basic(optional = true)
	public String str30;
	
	@Column(name = "STR_31")
	@Basic(optional = true)
	public String str31;
	
	@Column(name = "STR_32")
	@Basic(optional = true)
	public String str32;
	
	@Column(name = "STR_33")
	@Basic(optional = true)
	public String str33;
	
	@Column(name = "STR_34")
	@Basic(optional = true)
	public String str34;
	
	@Column(name = "STR_35")
	@Basic(optional = true)
	public String str35;
	
	@Column(name = "STR_36")
	@Basic(optional = true)
	public String str36;
	
	@Column(name = "STR_37")
	@Basic(optional = true)
	public String str37;
	
	@Column(name = "STR_38")
	@Basic(optional = true)
	public String str38;
	
	@Column(name = "STR_39")
	@Basic(optional = true)
	public String str39;
	
	@Column(name = "STR_40")
	@Basic(optional = true)
	public String str40;
	
	@Column(name = "STR_41")
	@Basic(optional = true)
	public String str41;
	
	@Column(name = "STR_42")
	@Basic(optional = true)
	public String str42;
	
	@Column(name = "STR_43")
	@Basic(optional = true)
	public String str43;
	
	@Column(name = "STR_44")
	@Basic(optional = true)
	public String str44;
	
	@Column(name = "STR_45")
	@Basic(optional = true)
	public String str45;
	
	@Column(name = "STR_46")
	@Basic(optional = true)
	public String str46;
	
	@Column(name = "STR_47")
	@Basic(optional = true)
	public String str47;
	
	@Column(name = "STR_48")
	@Basic(optional = true)
	public String str48;
	
	@Column(name = "STR_49")
	@Basic(optional = true)
	public String str49;
	
	@Column(name = "STR_50")
	@Basic(optional = true)
	public String str50;
	
	@Column(name = "STR_51")
	@Basic(optional = true)
	public String str51;
	
	@Column(name = "STR_52")
	@Basic(optional = true)
	public String str52;
	
	@Column(name = "STR_53")
	@Basic(optional = true)
	public String str53;
	
	@Column(name = "STR_54")
	@Basic(optional = true)
	public String str54;
	
	@Column(name = "STR_55")
	@Basic(optional = true)
	public String str55;
	
	@Column(name = "STR_56")
	@Basic(optional = true)
	public String str56;
	
	@Column(name = "STR_57")
	@Basic(optional = true)
	public String str57;
	
	@Column(name = "STR_58")
	@Basic(optional = true)
	public String str58;
	
	@Column(name = "STR_59")
	@Basic(optional = true)
	public String str59;
	
	@Column(name = "STR_60")
	@Basic(optional = true)
	public String str60;
	
	@Column(name = "DATE_01")
	@Basic(optional = true)
	public GeneralDate date01;
	
	@Column(name = "DATE_02")
	@Basic(optional = true)
	public GeneralDate date02;
	
	@Column(name = "DATE_03")
	@Basic(optional = true)
	public GeneralDate date03;
	
	@Column(name = "DATE_04")
	@Basic(optional = true)
	public GeneralDate date04;
	
	@Column(name = "DATE_05")
	@Basic(optional = true)
	public GeneralDate date05;
	
	@Column(name = "DATE_06")
	@Basic(optional = true)
	public GeneralDate date06;
	
	@Column(name = "DATE_07")
	@Basic(optional = true)
	public GeneralDate date07;
	
	@Column(name = "DATE_08")
	@Basic(optional = true)
	public GeneralDate date08;
	
	@Column(name = "DATE_09")
	@Basic(optional = true)
	public GeneralDate date09;
	
	@Column(name = "DATE_10")
	@Basic(optional = true)
	public GeneralDate date10;
	
	@Column(name = "INT_01")
	@Basic(optional = true)
	public int int01;
	
	@Column(name = "INT_02")
	@Basic(optional = true)
	public int int02;
	
	@Column(name = "INT_03")
	@Basic(optional = true)
	public int int03;
	
	@Column(name = "INT_04")
	@Basic(optional = true)
	public int int04;
	
	@Column(name = "INT_05")
	@Basic(optional = true)
	public int int05;
	
	@Column(name = "INT_06")
	@Basic(optional = true)
	public int int06;
	
	@Column(name = "INT_07")
	@Basic(optional = true)
	public int int07;
	
	@Column(name = "INT_08")
	@Basic(optional = true)
	public int int08;
	
	@Column(name = "INT_09")
	@Basic(optional = true)
	public int int09;
	
	@Column(name = "INT_10")
	@Basic(optional = true)
	public int int10;
	
	@Column(name = "INT_11")
	@Basic(optional = true)
	public int int11;
	
	@Column(name = "INT_12")
	@Basic(optional = true)
	public int int12;
	
	@Column(name = "INT_13")
	@Basic(optional = true)
	public int int13;
	
	@Column(name = "INT_14")
	@Basic(optional = true)
	public int int14;
	
	@Column(name = "INT_15")
	@Basic(optional = true)
	public int int15;
	
	@Column(name = "INT_16")
	@Basic(optional = true)
	public int int16;
	
	@Column(name = "INT_17")
	@Basic(optional = true)
	public int int17;
	
	@Column(name = "INT_18")
	@Basic(optional = true)
	public int int18;
	
	@Column(name = "INT_19")
	@Basic(optional = true)
	public int int19;
	
	@Column(name = "INT_20")
	@Basic(optional = true)
	public int int20;
	
	@Column(name = "INT_21")
	@Basic(optional = true)
	public int int21;
	
	@Column(name = "INT_22")
	@Basic(optional = true)
	public int int22;
	
	@Column(name = "INT_23")
	@Basic(optional = true)
	public int int23;
	
	@Column(name = "INT_24")
	@Basic(optional = true)
	public int int24;
	
	@Column(name = "INT_25")
	@Basic(optional = true)
	public int int25;
	
	@Column(name = "INT_26")
	@Basic(optional = true)
	public int int26;
	
	@Column(name = "INT_27")
	@Basic(optional = true)
	public int int27;
	
	@Column(name = "INT_28")
	@Basic(optional = true)
	public int int28;
	
	@Column(name = "INT_29")
	@Basic(optional = true)
	public int int29;
	
	@Column(name = "INT_30")
	@Basic(optional = true)
	public int int30;
	
	@Column(name = "NUM_01")
	@Basic(optional = true)
	public BigDecimal number01;
	
	@Column(name = "NUM_02")
	@Basic(optional = true)
	public BigDecimal number02;
	
	@Column(name = "NUM_03")
	@Basic(optional = true)
	public BigDecimal number03;
	
	@Column(name = "NUM_04")
	@Basic(optional = true)
	public BigDecimal number04;
	
	@Column(name = "NUM_05")
	@Basic(optional = true)
	public BigDecimal number05;
	
	@Column(name = "NUM_06")
	@Basic(optional = true)
	public BigDecimal number06;
	
	@Column(name = "NUM_07")
	@Basic(optional = true)
	public BigDecimal number07;
	
	@Column(name = "NUM_08")
	@Basic(optional = true)
	public BigDecimal number08;
	
	@Column(name = "NUM_09")
	@Basic(optional = true)
	public BigDecimal number09;
	
	@Column(name = "NUM_10")
	@Basic(optional = true)
	public BigDecimal number10;
	
	@Column(name = "NUM_11")
	@Basic(optional = true)
	public BigDecimal number11;
	
	@Column(name = "NUM_12")
	@Basic(optional = true)
	public BigDecimal number12;
	
	@Column(name = "NUM_13")
	@Basic(optional = true)
	public BigDecimal number13;
	
	@Column(name = "NUM_14")
	@Basic(optional = true)
	public BigDecimal number14;
	
	@Column(name = "NUM_15")
	@Basic(optional = true)
	public BigDecimal number15;
	
	@Column(name = "NUM_16")
	@Basic(optional = true)
	public BigDecimal number16;
	
	@Column(name = "NUM_17")
	@Basic(optional = true)
	public BigDecimal number17;
	
	@Column(name = "NUM_18")
	@Basic(optional = true)
	public BigDecimal number18;
	
	@Column(name = "NUM_19")
	@Basic(optional = true)
	public BigDecimal number19;
	
	@Column(name = "NUM_20")
	@Basic(optional = true)
	public BigDecimal number20;
	
	@Column(name = "NUM_21")
	@Basic(optional = true)
	public BigDecimal number21;
	
	@Column(name = "NUM_22")
	@Basic(optional = true)
	public BigDecimal number22;
	
	@Column(name = "NUM_23")
	@Basic(optional = true)
	public BigDecimal number23;
	
	@Column(name = "NUM_24")
	@Basic(optional = true)
	public BigDecimal number24;
	
	@Column(name = "NUM_25")
	@Basic(optional = true)
	public BigDecimal number25;
	
	@Column(name = "NUM_26")
	@Basic(optional = true)
	public BigDecimal number26;
	
	@Column(name = "NUM_27")
	@Basic(optional = true)
	public BigDecimal number27;
	
	@Column(name = "NUM_28")
	@Basic(optional = true)
	public BigDecimal number28;
	
	@Column(name = "NUM_29")
	@Basic(optional = true)
	public BigDecimal number29;
	
	@Column(name = "NUM_30")
	@Basic(optional = true)
	public BigDecimal number30;
	
	@Column(name = "SELECT_ID_01")
	@Basic(optional = true)
	public BigInteger selectId01;
	
	@Column(name = "SELECT_CODE_01")
	@Basic(optional = true)
	public String selectCode01;
	
	@Column(name = "SELECT_NAME_01")
	@Basic(optional = true)
	public String selectName01;
	
	@Column(name = "SELECT_ID_02")
	@Basic(optional = true)
	public BigInteger selectId02;
	
	@Column(name = "SELECT_CODE_02")
	@Basic(optional = true)
	public String selectCode02;
	
	@Column(name = "SELECT_NAME_02")
	@Basic(optional = true)
	public String selectName02;
	
	@Column(name = "SELECT_ID_03")
	@Basic(optional = true)
	public BigInteger selectId03;
	
	@Column(name = "SELECT_CODE_03")
	@Basic(optional = true)
	public String selectCode03;
	
	@Column(name = "SELECT_NAME_03")
	@Basic(optional = true)
	public String selectName03;
	
	@Column(name = "SELECT_ID_04")
	@Basic(optional = true)
	public BigInteger selectId04;
	
	@Column(name = "SELECT_CODE_04")
	@Basic(optional = true)
	public String selectCode04;
	
	@Column(name = "SELECT_NAME_04")
	@Basic(optional = true)
	public String selectName04;
	
	@Column(name = "SELECT_ID_05")
	@Basic(optional = true)
	public BigInteger selectId05;
	
	@Column(name = "SELECT_CODE_05")
	@Basic(optional = true)
	public String selectCode05;
	
	@Column(name = "SELECT_NAME_05")
	@Basic(optional = true)
	public String selectName05;
	
	@Column(name = "SELECT_ID_06")
	@Basic(optional = true)
	public BigInteger selectId06;
	
	@Column(name = "SELECT_CODE_06")
	@Basic(optional = true)
	public String selectCode06;
	
	@Column(name = "SELECT_NAME_06")
	@Basic(optional = true)
	public String selectName06;
	
	@Column(name = "SELECT_ID_07")
	@Basic(optional = true)
	public BigInteger selectId07;
	
	@Column(name = "SELECT_CODE_07")
	@Basic(optional = true)
	public String selectCode07;
	
	@Column(name = "SELECT_NAME_07")
	@Basic(optional = true)
	public String selectName07;
	
	@Column(name = "SELECT_ID_08")
	@Basic(optional = true)
	public BigInteger selectId08;
	
	@Column(name = "SELECT_CODE_08")
	@Basic(optional = true)
	public String selectCode08;
	
	@Column(name = "SELECT_NAME_08")
	@Basic(optional = true)
	public String selectName08;
	
	@Column(name = "SELECT_ID_09")
	@Basic(optional = true)
	public BigInteger selectId09;
	
	@Column(name = "SELECT_CODE_09")
	@Basic(optional = true)
	public String selectCode09;
	
	@Column(name = "SELECT_NAME_09")
	@Basic(optional = true)
	public String selectName09;
	
	@Column(name = "SELECT_ID_10")
	@Basic(optional = true)
	public BigInteger selectId10;
	
	@Column(name = "SELECT_CODE_10")
	@Basic(optional = true)
	public String selectCode10;
	
	@Column(name = "SELECT_NAME_10")
	@Basic(optional = true)
	public String selectName10;
	
	@Column(name = "SELECT_ID_11")
	@Basic(optional = true)
	public BigInteger selectId11;
	
	@Column(name = "SELECT_CODE_11")
	@Basic(optional = true)
	public String selectCode11;
	
	@Column(name = "SELECT_NAME_11")
	@Basic(optional = true)
	public String selectName11;
	
	@Column(name = "SELECT_ID_12")
	@Basic(optional = true)
	public BigInteger selectId12;
	
	@Column(name = "SELECT_CODE_12")
	@Basic(optional = true)
	public String selectCode12;
	
	@Column(name = "SELECT_NAME_12")
	@Basic(optional = true)
	public String selectName12;
	
	@Column(name = "SELECT_ID_13")
	@Basic(optional = true)
	public BigInteger selectId13;
	
	@Column(name = "SELECT_CODE_13")
	@Basic(optional = true)
	public String selectCode13;
	
	@Column(name = "SELECT_NAME_13")
	@Basic(optional = true)
	public String selectName13;
	
	@Column(name = "SELECT_ID_14")
	@Basic(optional = true)
	public BigInteger selectId14;
	
	@Column(name = "SELECT_CODE_14")
	@Basic(optional = true)
	public String selectCode14;
	
	@Column(name = "SELECT_NAME_14")
	@Basic(optional = true)
	public String selectName14;
	
	@Column(name = "SELECT_ID_15")
	@Basic(optional = true)
	public BigInteger selectId15;
	
	@Column(name = "SELECT_CODE_15")
	@Basic(optional = true)
	public String selectCode15;
	
	@Column(name = "SELECT_NAME_15")
	@Basic(optional = true)
	public String selectName15;
	
	@Column(name = "SELECT_ID_16")
	@Basic(optional = true)
	public BigInteger selectId16;
	
	@Column(name = "SELECT_CODE_16")
	@Basic(optional = true)
	public String selectCode16;
	
	@Column(name = "SELECT_NAME_16")
	@Basic(optional = true)
	public String selectName16;
	
	@Column(name = "SELECT_ID_17")
	@Basic(optional = true)
	public BigInteger selectId17;
	
	@Column(name = "SELECT_CODE_17")
	@Basic(optional = true)
	public String selectCode17;
	
	@Column(name = "SELECT_NAME_17")
	@Basic(optional = true)
	public String selectName17;
	
	@Column(name = "SELECT_ID_18")
	@Basic(optional = true)
	public BigInteger selectId18;
	
	@Column(name = "SELECT_CODE_18")
	@Basic(optional = true)
	public String selectCode18;
	
	@Column(name = "SELECT_NAME_18")
	@Basic(optional = true)
	public String selectName18;
	
	@Column(name = "SELECT_ID_19")
	@Basic(optional = true)
	public BigInteger selectId19;
	
	@Column(name = "SELECT_CODE_19")
	@Basic(optional = true)
	public String selectCode19;
	
	@Column(name = "SELECT_NAME_19")
	@Basic(optional = true)
	public String selectName19;
	
	@Column(name = "SELECT_ID_20")
	@Basic(optional = true)
	public BigInteger selectId20;
	
	@Column(name = "SELECT_CODE_20")
	@Basic(optional = true)
	public String selectCode20;
	
	@Column(name = "SELECT_NAME_20")
	@Basic(optional = true)
	public String selectName20;
	
	@Column(name = "SELECT_ID_21")
	@Basic(optional = true)
	public BigInteger selectId21;
	
	@Column(name = "SELECT_CODE_21")
	@Basic(optional = true)
	public String selectCode21;
	
	@Column(name = "SELECT_NAME_21")
	@Basic(optional = true)
	public String selectName21;
	
	@Column(name = "SELECT_ID_22")
	@Basic(optional = true)
	public BigInteger selectId22;
	
	@Column(name = "SELECT_CODE_22")
	@Basic(optional = true)
	public String selectCode22;
	
	@Column(name = "SELECT_NAME_22")
	@Basic(optional = true)
	public String selectName22;
	
	@Column(name = "SELECT_ID_23")
	@Basic(optional = true)
	public BigInteger selectId23;
	
	@Column(name = "SELECT_CODE_23")
	@Basic(optional = true)
	public String selectCode23;
	
	@Column(name = "SELECT_NAME_23")
	@Basic(optional = true)
	public String selectName23;
	
	@Column(name = "SELECT_ID_24")
	@Basic(optional = true)
	public BigInteger selectId24;
	
	@Column(name = "SELECT_CODE_24")
	@Basic(optional = true)
	public String selectCode24;
	
	@Column(name = "SELECT_NAME_24")
	@Basic(optional = true)
	public String selectName24;
	
	@Column(name = "SELECT_ID_25")
	@Basic(optional = true)
	public BigInteger selectId25;
	
	@Column(name = "SELECT_CODE_25")
	@Basic(optional = true)
	public String selectCode25;
	
	@Column(name = "SELECT_NAME_25")
	@Basic(optional = true)
	public String selectName25;
	
	@Column(name = "SELECT_ID_26")
	@Basic(optional = true)
	public BigInteger selectId26;
	
	@Column(name = "SELECT_CODE_26")
	@Basic(optional = true)
	public String selectCode26;
	
	@Column(name = "SELECT_NAME_26")
	@Basic(optional = true)
	public String selectName26;
	
	@Column(name = "SELECT_ID_27")
	@Basic(optional = true)
	public BigInteger selectId27;
	
	@Column(name = "SELECT_CODE_27")
	@Basic(optional = true)
	public String selectCode27;
	
	@Column(name = "SELECT_NAME_27")
	@Basic(optional = true)
	public String selectName27;
	
	@Column(name = "SELECT_ID_28")
	@Basic(optional = true)
	public BigInteger selectId28;
	
	@Column(name = "SELECT_CODE_28")
	@Basic(optional = true)
	public String selectCode28;
	
	@Column(name = "SELECT_NAME_28")
	@Basic(optional = true)
	public String selectName28;
	
	@Column(name = "SELECT_ID_29")
	@Basic(optional = true)
	public BigInteger selectId29;
	
	@Column(name = "SELECT_CODE_29")
	@Basic(optional = true)
	public String selectCode29;
	
	@Column(name = "SELECT_NAME_29")
	@Basic(optional = true)
	public String selectName29;
	
	@Column(name = "SELECT_ID_30")
	@Basic(optional = true)
	public BigInteger selectId30;
	
	@Column(name = "SELECT_CODE_30")
	@Basic(optional = true)
	public String selectCode30;
	
	@Column(name = "SELECT_NAME_30")
	@Basic(optional = true)
	public String selectName30;
	
	@Override
	protected Object getKey() {
		return hisId;
	}
}