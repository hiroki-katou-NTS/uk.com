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
	public int rptLayoutId;
	
	@Column(name = "RPTID")
	@Basic(optional = true)
	public BigInteger rptId;
	
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
	
	@Override
	protected Object getKey() {
		return hisId;
	}
	
	public PpedtData toEntity(PersonalInformation domain) {
		PpedtData entity = new PpedtData();
		
		entity.hisId = domain.getHistId();
		entity.contractCd = domain.getContractCd();
		entity.cId = domain.getCid().map(m -> m).orElse(null);
		entity.ccd = domain.getCompanyCode().map(m -> m).orElse(null);
		entity.pId = domain.getPId();
		entity.sid = domain.getScd().map(m -> m).orElse(null);
		entity.scd = domain.getScd().map(m -> m).orElse(null);
		entity.personName = domain.getPersonName().map(m -> m).orElse(null);
		entity.workId = domain.getWorkId();
		entity.workName = domain.getWorkName().map(m -> m).orElse(null);
		entity.startDate = domain.getStartDate();
		entity.endDate = domain.getEndDate();
		entity.releaseDate = domain.getReleaseDate().map(m -> m).orElse(null);
		entity.requestFlg = domain.getRequestFlg();
		entity.rptLayoutId = domain.getRptLayoutId();
		entity.rptId = BigInteger.valueOf(domain.getRptId());
		entity.rptNumber = domain.getRptNumber().map(m -> m).orElse(null);
		
		entity.str01 = domain.getStr01().map(m -> m).orElse(null);
		entity.str02 = domain.getStr02().map(m -> m).orElse(null);
		entity.str03 = domain.getStr03().map(m -> m).orElse(null);
		entity.str04 = domain.getStr04().map(m -> m).orElse(null);
		entity.str05 = domain.getStr05().map(m -> m).orElse(null);
		entity.str06 = domain.getStr06().map(m -> m).orElse(null);
		entity.str07 = domain.getStr07().map(m -> m).orElse(null);
		entity.str08 = domain.getStr08().map(m -> m).orElse(null);
		entity.str09 = domain.getStr09().map(m -> m).orElse(null);
		entity.str10 = domain.getStr10().map(m -> m).orElse(null);
		entity.str11 = domain.getStr11().map(m -> m).orElse(null);
		entity.str12 = domain.getStr12().map(m -> m).orElse(null);
		entity.str13 = domain.getStr13().map(m -> m).orElse(null);
		entity.str14 = domain.getStr14().map(m -> m).orElse(null);
		entity.str15 = domain.getStr15().map(m -> m).orElse(null);
		entity.str16 = domain.getStr16().map(m -> m).orElse(null);
		entity.str17 = domain.getStr17().map(m -> m).orElse(null);
		entity.str18 = domain.getStr18().map(m -> m).orElse(null);
		entity.str19 = domain.getStr19().map(m -> m).orElse(null);
		entity.str20 = domain.getStr20().map(m -> m).orElse(null);
		entity.str21 = domain.getStr21().map(m -> m).orElse(null);
		entity.str22 = domain.getStr22().map(m -> m).orElse(null);
		entity.str23 = domain.getStr23().map(m -> m).orElse(null);
		entity.str24 = domain.getStr24().map(m -> m).orElse(null);
		entity.str25 = domain.getStr25().map(m -> m).orElse(null);
		entity.str26 = domain.getStr26().map(m -> m).orElse(null);
		entity.str27 = domain.getStr27().map(m -> m).orElse(null);
		entity.str28 = domain.getStr28().map(m -> m).orElse(null);
		entity.str29 = domain.getStr29().map(m -> m).orElse(null);
		entity.str30 = domain.getStr30().map(m -> m).orElse(null);
		entity.str31 = domain.getStr31().map(m -> m).orElse(null);
		entity.str32 = domain.getStr32().map(m -> m).orElse(null);
		entity.str33 = domain.getStr33().map(m -> m).orElse(null);
		entity.str34 = domain.getStr34().map(m -> m).orElse(null);
		entity.str35 = domain.getStr35().map(m -> m).orElse(null);
		entity.str36 = domain.getStr36().map(m -> m).orElse(null);
		entity.str37 = domain.getStr37().map(m -> m).orElse(null);
		entity.str38 = domain.getStr38().map(m -> m).orElse(null);
		entity.str39 = domain.getStr39().map(m -> m).orElse(null);
		entity.str40 = domain.getStr40().map(m -> m).orElse(null);
		entity.str41 = domain.getStr41().map(m -> m).orElse(null);
		entity.str42 = domain.getStr42().map(m -> m).orElse(null);
		entity.str43 = domain.getStr43().map(m -> m).orElse(null);
		entity.str44 = domain.getStr44().map(m -> m).orElse(null);
		entity.str45 = domain.getStr45().map(m -> m).orElse(null);
		entity.str46 = domain.getStr46().map(m -> m).orElse(null);
		entity.str47 = domain.getStr47().map(m -> m).orElse(null);
		entity.str48 = domain.getStr48().map(m -> m).orElse(null);
		entity.str49 = domain.getStr49().map(m -> m).orElse(null);
		entity.str50 = domain.getStr50().map(m -> m).orElse(null);
		entity.str51 = domain.getStr51().map(m -> m).orElse(null);
		entity.str52 = domain.getStr52().map(m -> m).orElse(null);
		entity.str53 = domain.getStr53().map(m -> m).orElse(null);
		entity.str54 = domain.getStr54().map(m -> m).orElse(null);
		entity.str55 = domain.getStr55().map(m -> m).orElse(null);
		entity.str56 = domain.getStr56().map(m -> m).orElse(null);
		entity.str57 = domain.getStr57().map(m -> m).orElse(null);
		entity.str58 = domain.getStr58().map(m -> m).orElse(null);
		entity.str59 = domain.getStr59().map(m -> m).orElse(null);
		entity.str60 = domain.getStr60().map(m -> m).orElse(null);
		
		entity.date01 = domain.getDate01().map(m -> m).orElse(null);
		entity.date02 = domain.getDate02().map(m -> m).orElse(null);
		entity.date03 = domain.getDate03().map(m -> m).orElse(null);
		entity.date04 = domain.getDate04().map(m -> m).orElse(null);
		entity.date05 = domain.getDate05().map(m -> m).orElse(null);
		entity.date06 = domain.getDate06().map(m -> m).orElse(null);
		entity.date07 = domain.getDate07().map(m -> m).orElse(null);
		entity.date08 = domain.getDate08().map(m -> m).orElse(null);
		entity.date09 = domain.getDate09().map(m -> m).orElse(null);
		entity.date10 = domain.getDate10().map(m -> m).orElse(null);
		
		entity.int01 = domain.getInt01();
		entity.int02 = domain.getInt02();
		entity.int03 = domain.getInt03();
		entity.int04 = domain.getInt04();
		entity.int05 = domain.getInt05();
		entity.int06 = domain.getInt06();
		entity.int07 = domain.getInt07();
		entity.int08 = domain.getInt08();
		entity.int09 = domain.getInt09();
		entity.int10 = domain.getInt10();
		entity.int11 = domain.getInt11();
		entity.int12 = domain.getInt12();
		entity.int13 = domain.getInt13();
		entity.int14 = domain.getInt14();
		entity.int15 = domain.getInt15();
		entity.int16 = domain.getInt16();
		entity.int17 = domain.getInt17();
		entity.int18 = domain.getInt18();
		entity.int19 = domain.getInt19();
		entity.int20 = domain.getInt20();
		entity.int21 = domain.getInt21();
		entity.int22 = domain.getInt22();
		entity.int23 = domain.getInt23();
		entity.int24 = domain.getInt24();
		entity.int25 = domain.getInt25();
		entity.int26 = domain.getInt26();
		entity.int27 = domain.getInt27();
		entity.int28 = domain.getInt28();
		entity.int29 = domain.getInt29();
		entity.int30 = domain.getInt30();
		
		entity.number01 = BigDecimal.valueOf(domain.getNumber01());
		entity.number02 = BigDecimal.valueOf(domain.getNumber02());
		entity.number03 = BigDecimal.valueOf(domain.getNumber03());
		entity.number04 = BigDecimal.valueOf(domain.getNumber04());
		entity.number05 = BigDecimal.valueOf(domain.getNumber05());
		entity.number06 = BigDecimal.valueOf(domain.getNumber06());
		entity.number07 = BigDecimal.valueOf(domain.getNumber07());
		entity.number08 = BigDecimal.valueOf(domain.getNumber08());
		entity.number09 = BigDecimal.valueOf(domain.getNumber09());
		entity.number10 = BigDecimal.valueOf(domain.getNumber10());
		entity.number11 = BigDecimal.valueOf(domain.getNumber11());
		entity.number12 = BigDecimal.valueOf(domain.getNumber12());
		entity.number13 = BigDecimal.valueOf(domain.getNumber13());
		entity.number14 = BigDecimal.valueOf(domain.getNumber14());
		entity.number15 = BigDecimal.valueOf(domain.getNumber15());
		entity.number16 = BigDecimal.valueOf(domain.getNumber16());
		entity.number17 = BigDecimal.valueOf(domain.getNumber17());
		entity.number18 = BigDecimal.valueOf(domain.getNumber18());
		entity.number19 = BigDecimal.valueOf(domain.getNumber19());
		entity.number20 = BigDecimal.valueOf(domain.getNumber20());
		entity.number21 = BigDecimal.valueOf(domain.getNumber21());
		entity.number22 = BigDecimal.valueOf(domain.getNumber22());
		entity.number23 = BigDecimal.valueOf(domain.getNumber23());
		entity.number24 = BigDecimal.valueOf(domain.getNumber24());
		entity.number25 = BigDecimal.valueOf(domain.getNumber25());
		entity.number26 = BigDecimal.valueOf(domain.getNumber26());
		entity.number27 = BigDecimal.valueOf(domain.getNumber27());
		entity.number28 = BigDecimal.valueOf(domain.getNumber28());
		entity.number29 = BigDecimal.valueOf(domain.getNumber29());
		entity.number30 = BigDecimal.valueOf(domain.getNumber30());
		
		entity.selectId01 = BigInteger.valueOf(domain.getSelectId01());
		entity.selectCode01 = domain.getSelectCode01().map(m -> m).orElse(null);
		entity.selectName01 = domain.getSelectName01().map(m -> m).orElse(null);
		entity.selectId02 = BigInteger.valueOf(domain.getSelectId02());
		entity.selectCode02 = domain.getSelectCode02().map(m -> m).orElse(null);
		entity.selectName02 = domain.getSelectName02().map(m -> m).orElse(null);
		entity.selectId03 = BigInteger.valueOf(domain.getSelectId03());
		entity.selectCode03 = domain.getSelectCode03().map(m -> m).orElse(null);
		entity.selectName03 = domain.getSelectName03().map(m -> m).orElse(null);
		entity.selectId04 = BigInteger.valueOf(domain.getSelectId04());
		entity.selectCode04 = domain.getSelectCode04().map(m -> m).orElse(null);
		entity.selectName04 = domain.getSelectName04().map(m -> m).orElse(null);
		entity.selectId05 = BigInteger.valueOf(domain.getSelectId05());
		entity.selectCode05 = domain.getSelectCode05().map(m -> m).orElse(null);
		entity.selectName05 = domain.getSelectName05().map(m -> m).orElse(null);
		entity.selectId06 = BigInteger.valueOf(domain.getSelectId06());
		entity.selectCode06 = domain.getSelectCode06().map(m -> m).orElse(null);
		entity.selectName06 = domain.getSelectName06().map(m -> m).orElse(null);
		entity.selectId07 = BigInteger.valueOf(domain.getSelectId07());
		entity.selectCode07 = domain.getSelectCode07().map(m -> m).orElse(null);
		entity.selectName07 = domain.getSelectName07().map(m -> m).orElse(null);
		entity.selectId08 = BigInteger.valueOf(domain.getSelectId08());
		entity.selectCode08 = domain.getSelectCode08().map(m -> m).orElse(null);
		entity.selectName08 = domain.getSelectName08().map(m -> m).orElse(null);
		entity.selectId09 = BigInteger.valueOf(domain.getSelectId09());
		entity.selectCode09 = domain.getSelectCode09().map(m -> m).orElse(null);
		entity.selectName09 = domain.getSelectName09().map(m -> m).orElse(null);
		entity.selectId10 = BigInteger.valueOf(domain.getSelectId10());
		entity.selectCode10 = domain.getSelectCode10().map(m -> m).orElse(null);
		entity.selectName10 = domain.getSelectName10().map(m -> m).orElse(null);
		entity.selectId11 = BigInteger.valueOf(domain.getSelectId11());
		entity.selectCode11 = domain.getSelectCode01().map(m -> m).orElse(null);
		entity.selectName11 = domain.getSelectName01().map(m -> m).orElse(null);
		entity.selectId12 = BigInteger.valueOf(domain.getSelectId12());
		entity.selectCode12 = domain.getSelectCode02().map(m -> m).orElse(null);
		entity.selectName12 = domain.getSelectName02().map(m -> m).orElse(null);
		entity.selectId13 = BigInteger.valueOf(domain.getSelectId13());
		entity.selectCode13 = domain.getSelectCode03().map(m -> m).orElse(null);
		entity.selectName13 = domain.getSelectName03().map(m -> m).orElse(null);
		entity.selectId14 = BigInteger.valueOf(domain.getSelectId14());
		entity.selectCode14 = domain.getSelectCode04().map(m -> m).orElse(null);
		entity.selectName14 = domain.getSelectName04().map(m -> m).orElse(null);
		entity.selectId15 = BigInteger.valueOf(domain.getSelectId15());
		entity.selectCode15 = domain.getSelectCode05().map(m -> m).orElse(null);
		entity.selectName15 = domain.getSelectName05().map(m -> m).orElse(null);
		entity.selectId16 = BigInteger.valueOf(domain.getSelectId16());
		entity.selectCode16 = domain.getSelectCode06().map(m -> m).orElse(null);
		entity.selectName16 = domain.getSelectName06().map(m -> m).orElse(null);
		entity.selectId17 = BigInteger.valueOf(domain.getSelectId17());
		entity.selectCode17 = domain.getSelectCode07().map(m -> m).orElse(null);
		entity.selectName17 = domain.getSelectName07().map(m -> m).orElse(null);
		entity.selectId18 = BigInteger.valueOf(domain.getSelectId18());
		entity.selectCode18 = domain.getSelectCode08().map(m -> m).orElse(null);
		entity.selectName18 = domain.getSelectName08().map(m -> m).orElse(null);
		entity.selectId19 = BigInteger.valueOf(domain.getSelectId19());
		entity.selectCode19 = domain.getSelectCode09().map(m -> m).orElse(null);
		entity.selectName19 = domain.getSelectName09().map(m -> m).orElse(null);
		entity.selectId20 = BigInteger.valueOf(domain.getSelectId20());
		
		return entity;
	}
}