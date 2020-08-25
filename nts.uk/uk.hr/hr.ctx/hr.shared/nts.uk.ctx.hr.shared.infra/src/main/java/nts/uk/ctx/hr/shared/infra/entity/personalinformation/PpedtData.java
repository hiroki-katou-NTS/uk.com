package nts.uk.ctx.hr.shared.infra.entity.personalinformation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author chungnt
 *
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEDT_DATA")
@Getter
public class PpedtData extends ContractUkJpaEntity implements Serializable {

	@Id
	@Column(name = "HIST_ID")
	@Basic(optional = false)
	private String hisId;

	@Column(name = "CONTRACT_CD")
	@Basic(optional = false)
	private String contractCd;

	@Column(name = "CID")
	@Basic(optional = true)
	private String cId;

	@Column(name = "CCD")
	@Basic(optional = true)
	private String ccd;

	@Column(name = "PID")
	@Basic(optional = false)
	private String pId;

	@Column(name = "SID")
	@Basic(optional = true)
	private String sid;

	@Column(name = "SCD")
	@Basic(optional = true)
	private String scd;

	@Column(name = "PERSON_NAME")
	@Basic(optional = true)
	private String personName;

	@Column(name = "WORK_ID")
	@Basic(optional = false)
	private int workId;

	@Column(name = "WORK_NAME")
	@Basic(optional = true)
	private String workName;

	@Column(name = "START_DATE")
	@Basic(optional = false)
	private GeneralDate startDate;

	@Column(name = "END_DATE")
	@Basic(optional = false)
	private GeneralDate endDate;

	@Column(name = "RELEASE_DATE")
	@Basic(optional = true)
	private GeneralDate releaseDate;

	@Column(name = "REQUEST_FLG")
	@Basic(optional = true)
	private int requestFlg;

	@Column(name = "RPT_LAYOUT_ID")
	@Basic(optional = true)
	private int rptLayoutId;

	@Column(name = "RPTID")
	@Basic(optional = true)
	private BigInteger rptId;

	@Column(name = "RPT_NUMBER")
	@Basic(optional = true)
	private String rptNumber;

	@Column(name = "STR_01")
	@Basic(optional = true)
	private String str01;

	@Column(name = "STR_02")
	@Basic(optional = true)
	private String str02;

	@Column(name = "STR_03")
	@Basic(optional = true)
	private String str03;

	@Column(name = "STR_04")
	@Basic(optional = true)
	private String str04;

	@Column(name = "STR_05")
	@Basic(optional = true)
	private String str05;

	@Column(name = "STR_06")
	@Basic(optional = true)
	private String str06;

	@Column(name = "STR_07")
	@Basic(optional = true)
	private String str07;

	@Column(name = "STR_08")
	@Basic(optional = true)
	private String str08;

	@Column(name = "STR_09")
	@Basic(optional = true)
	private String str09;

	@Column(name = "STR_10")
	@Basic(optional = true)
	private String str10;

	@Column(name = "STR_11")
	@Basic(optional = true)
	private String str11;

	@Column(name = "STR_12")
	@Basic(optional = true)
	private String str12;

	@Column(name = "STR_13")
	@Basic(optional = true)
	private String str13;

	@Column(name = "STR_14")
	@Basic(optional = true)
	private String str14;

	@Column(name = "STR_15")
	@Basic(optional = true)
	private String str15;

	@Column(name = "STR_16")
	@Basic(optional = true)
	private String str16;

	@Column(name = "STR_17")
	@Basic(optional = true)
	private String str17;

	@Column(name = "STR_18")
	@Basic(optional = true)
	private String str18;

	@Column(name = "STR_19")
	@Basic(optional = true)
	private String str19;

	@Column(name = "STR_20")
	@Basic(optional = true)
	private String str20;

	@Column(name = "STR_21")
	@Basic(optional = true)
	private String str21;

	@Column(name = "STR_22")
	@Basic(optional = true)
	private String str22;

	@Column(name = "STR_23")
	@Basic(optional = true)
	private String str23;

	@Column(name = "STR_24")
	@Basic(optional = true)
	private String str24;

	@Column(name = "STR_25")
	@Basic(optional = true)
	private String str25;

	@Column(name = "STR_26")
	@Basic(optional = true)
	private String str26;

	@Column(name = "STR_27")
	@Basic(optional = true)
	private String str27;

	@Column(name = "STR_28")
	@Basic(optional = true)
	private String str28;

	@Column(name = "STR_29")
	@Basic(optional = true)
	private String str29;

	@Column(name = "STR_30")
	@Basic(optional = true)
	private String str30;

	@Column(name = "STR_31")
	@Basic(optional = true)
	private String str31;

	@Column(name = "STR_32")
	@Basic(optional = true)
	private String str32;

	@Column(name = "STR_33")
	@Basic(optional = true)
	private String str33;

	@Column(name = "STR_34")
	@Basic(optional = true)
	private String str34;

	@Column(name = "STR_35")
	@Basic(optional = true)
	private String str35;

	@Column(name = "STR_36")
	@Basic(optional = true)
	private String str36;

	@Column(name = "STR_37")
	@Basic(optional = true)
	private String str37;

	@Column(name = "STR_38")
	@Basic(optional = true)
	private String str38;

	@Column(name = "STR_39")
	@Basic(optional = true)
	private String str39;

	@Column(name = "STR_40")
	@Basic(optional = true)
	private String str40;

	@Column(name = "STR_41")
	@Basic(optional = true)
	private String str41;

	@Column(name = "STR_42")
	@Basic(optional = true)
	private String str42;

	@Column(name = "STR_43")
	@Basic(optional = true)
	private String str43;

	@Column(name = "STR_44")
	@Basic(optional = true)
	private String str44;

	@Column(name = "STR_45")
	@Basic(optional = true)
	private String str45;

	@Column(name = "STR_46")
	@Basic(optional = true)
	private String str46;

	@Column(name = "STR_47")
	@Basic(optional = true)
	private String str47;

	@Column(name = "STR_48")
	@Basic(optional = true)
	private String str48;

	@Column(name = "STR_49")
	@Basic(optional = true)
	private String str49;

	@Column(name = "STR_50")
	@Basic(optional = true)
	private String str50;

	@Column(name = "STR_51")
	@Basic(optional = true)
	private String str51;

	@Column(name = "STR_52")
	@Basic(optional = true)
	private String str52;

	@Column(name = "STR_53")
	@Basic(optional = true)
	private String str53;

	@Column(name = "STR_54")
	@Basic(optional = true)
	private String str54;

	@Column(name = "STR_55")
	@Basic(optional = true)
	private String str55;

	@Column(name = "STR_56")
	@Basic(optional = true)
	private String str56;

	@Column(name = "STR_57")
	@Basic(optional = true)
	private String str57;

	@Column(name = "STR_58")
	@Basic(optional = true)
	private String str58;

	@Column(name = "STR_59")
	@Basic(optional = true)
	private String str59;

	@Column(name = "STR_60")
	@Basic(optional = true)
	private String str60;

	@Column(name = "DATE_01")
	@Basic(optional = true)
	private GeneralDate date01;

	@Column(name = "DATE_02")
	@Basic(optional = true)
	private GeneralDate date02;

	@Column(name = "DATE_03")
	@Basic(optional = true)
	private GeneralDate date03;

	@Column(name = "DATE_04")
	@Basic(optional = true)
	private GeneralDate date04;

	@Column(name = "DATE_05")
	@Basic(optional = true)
	private GeneralDate date05;

	@Column(name = "DATE_06")
	@Basic(optional = true)
	private GeneralDate date06;

	@Column(name = "DATE_07")
	@Basic(optional = true)
	private GeneralDate date07;

	@Column(name = "DATE_08")
	@Basic(optional = true)
	private GeneralDate date08;

	@Column(name = "DATE_09")
	@Basic(optional = true)
	private GeneralDate date09;

	@Column(name = "DATE_10")
	@Basic(optional = true)
	private GeneralDate date10;

	@Column(name = "INT_01")
	@Basic(optional = true)
	private int int01;

	@Column(name = "INT_02")
	@Basic(optional = true)
	private int int02;

	@Column(name = "INT_03")
	@Basic(optional = true)
	private int int03;

	@Column(name = "INT_04")
	@Basic(optional = true)
	private int int04;

	@Column(name = "INT_05")
	@Basic(optional = true)
	private int int05;

	@Column(name = "INT_06")
	@Basic(optional = true)
	private int int06;

	@Column(name = "INT_07")
	@Basic(optional = true)
	private int int07;

	@Column(name = "INT_08")
	@Basic(optional = true)
	private int int08;

	@Column(name = "INT_09")
	@Basic(optional = true)
	private int int09;

	@Column(name = "INT_10")
	@Basic(optional = true)
	private int int10;

	@Column(name = "INT_11")
	@Basic(optional = true)
	private int int11;

	@Column(name = "INT_12")
	@Basic(optional = true)
	private int int12;

	@Column(name = "INT_13")
	@Basic(optional = true)
	private int int13;

	@Column(name = "INT_14")
	@Basic(optional = true)
	private int int14;

	@Column(name = "INT_15")
	@Basic(optional = true)
	private int int15;

	@Column(name = "INT_16")
	@Basic(optional = true)
	private int int16;

	@Column(name = "INT_17")
	@Basic(optional = true)
	private int int17;

	@Column(name = "INT_18")
	@Basic(optional = true)
	private int int18;

	@Column(name = "INT_19")
	@Basic(optional = true)
	private int int19;

	@Column(name = "INT_20")
	@Basic(optional = true)
	private int int20;

	@Column(name = "INT_21")
	@Basic(optional = true)
	private int int21;

	@Column(name = "INT_22")
	@Basic(optional = true)
	private int int22;

	@Column(name = "INT_23")
	@Basic(optional = true)
	private int int23;

	@Column(name = "INT_24")
	@Basic(optional = true)
	private int int24;

	@Column(name = "INT_25")
	@Basic(optional = true)
	private int int25;

	@Column(name = "INT_26")
	@Basic(optional = true)
	private int int26;

	@Column(name = "INT_27")
	@Basic(optional = true)
	private int int27;

	@Column(name = "INT_28")
	@Basic(optional = true)
	private int int28;

	@Column(name = "INT_29")
	@Basic(optional = true)
	private int int29;

	@Column(name = "INT_30")
	@Basic(optional = true)
	private int int30;

	@Column(name = "NUM_01")
	@Basic(optional = true)
	private BigDecimal number01;

	@Column(name = "NUM_02")
	@Basic(optional = true)
	private BigDecimal number02;

	@Column(name = "NUM_03")
	@Basic(optional = true)
	private BigDecimal number03;

	@Column(name = "NUM_04")
	@Basic(optional = true)
	private BigDecimal number04;

	@Column(name = "NUM_05")
	@Basic(optional = true)
	private BigDecimal number05;

	@Column(name = "NUM_06")
	@Basic(optional = true)
	private BigDecimal number06;

	@Column(name = "NUM_07")
	@Basic(optional = true)
	private BigDecimal number07;

	@Column(name = "NUM_08")
	@Basic(optional = true)
	private BigDecimal number08;

	@Column(name = "NUM_09")
	@Basic(optional = true)
	private BigDecimal number09;

	@Column(name = "NUM_10")
	@Basic(optional = true)
	private BigDecimal number10;

	@Column(name = "NUM_11")
	@Basic(optional = true)
	private BigDecimal number11;

	@Column(name = "NUM_12")
	@Basic(optional = true)
	private BigDecimal number12;

	@Column(name = "NUM_13")
	@Basic(optional = true)
	private BigDecimal number13;

	@Column(name = "NUM_14")
	@Basic(optional = true)
	private BigDecimal number14;

	@Column(name = "NUM_15")
	@Basic(optional = true)
	private BigDecimal number15;

	@Column(name = "NUM_16")
	@Basic(optional = true)
	private BigDecimal number16;

	@Column(name = "NUM_17")
	@Basic(optional = true)
	private BigDecimal number17;

	@Column(name = "NUM_18")
	@Basic(optional = true)
	private BigDecimal number18;

	@Column(name = "NUM_19")
	@Basic(optional = true)
	private BigDecimal number19;

	@Column(name = "NUM_20")
	@Basic(optional = true)
	private BigDecimal number20;

	@Column(name = "NUM_21")
	@Basic(optional = true)
	private BigDecimal number21;

	@Column(name = "NUM_22")
	@Basic(optional = true)
	private BigDecimal number22;

	@Column(name = "NUM_23")
	@Basic(optional = true)
	private BigDecimal number23;

	@Column(name = "NUM_24")
	@Basic(optional = true)
	private BigDecimal number24;

	@Column(name = "NUM_25")
	@Basic(optional = true)
	private BigDecimal number25;

	@Column(name = "NUM_26")
	@Basic(optional = true)
	private BigDecimal number26;

	@Column(name = "NUM_27")
	@Basic(optional = true)
	private BigDecimal number27;

	@Column(name = "NUM_28")
	@Basic(optional = true)
	private BigDecimal number28;

	@Column(name = "NUM_29")
	@Basic(optional = true)
	private BigDecimal number29;

	@Column(name = "NUM_30")
	@Basic(optional = true)
	private BigDecimal number30;

	@Column(name = "SELECT_ID_01")
	@Basic(optional = true)
	private BigInteger selectId01;

	@Column(name = "SELECT_CODE_01")
	@Basic(optional = true)
	private String selectCode01;

	@Column(name = "SELECT_NAME_01")
	@Basic(optional = true)
	private String selectName01;

	@Column(name = "SELECT_ID_02")
	@Basic(optional = true)
	private BigInteger selectId02;

	@Column(name = "SELECT_CODE_02")
	@Basic(optional = true)
	private String selectCode02;

	@Column(name = "SELECT_NAME_02")
	@Basic(optional = true)
	private String selectName02;

	@Column(name = "SELECT_ID_03")
	@Basic(optional = true)
	private BigInteger selectId03;

	@Column(name = "SELECT_CODE_03")
	@Basic(optional = true)
	private String selectCode03;

	@Column(name = "SELECT_NAME_03")
	@Basic(optional = true)
	private String selectName03;

	@Column(name = "SELECT_ID_04")
	@Basic(optional = true)
	private BigInteger selectId04;

	@Column(name = "SELECT_CODE_04")
	@Basic(optional = true)
	private String selectCode04;

	@Column(name = "SELECT_NAME_04")
	@Basic(optional = true)
	private String selectName04;

	@Column(name = "SELECT_ID_05")
	@Basic(optional = true)
	private BigInteger selectId05;

	@Column(name = "SELECT_CODE_05")
	@Basic(optional = true)
	private String selectCode05;

	@Column(name = "SELECT_NAME_05")
	@Basic(optional = true)
	private String selectName05;

	@Column(name = "SELECT_ID_06")
	@Basic(optional = true)
	private BigInteger selectId06;

	@Column(name = "SELECT_CODE_06")
	@Basic(optional = true)
	private String selectCode06;

	@Column(name = "SELECT_NAME_06")
	@Basic(optional = true)
	private String selectName06;

	@Column(name = "SELECT_ID_07")
	@Basic(optional = true)
	private BigInteger selectId07;

	@Column(name = "SELECT_CODE_07")
	@Basic(optional = true)
	private String selectCode07;

	@Column(name = "SELECT_NAME_07")
	@Basic(optional = true)
	private String selectName07;

	@Column(name = "SELECT_ID_08")
	@Basic(optional = true)
	private BigInteger selectId08;

	@Column(name = "SELECT_CODE_08")
	@Basic(optional = true)
	private String selectCode08;

	@Column(name = "SELECT_NAME_08")
	@Basic(optional = true)
	private String selectName08;

	@Column(name = "SELECT_ID_09")
	@Basic(optional = true)
	private BigInteger selectId09;

	@Column(name = "SELECT_CODE_09")
	@Basic(optional = true)
	private String selectCode09;

	@Column(name = "SELECT_NAME_09")
	@Basic(optional = true)
	private String selectName09;

	@Column(name = "SELECT_ID_10")
	@Basic(optional = true)
	private BigInteger selectId10;

	@Column(name = "SELECT_CODE_10")
	@Basic(optional = true)
	private String selectCode10;

	@Column(name = "SELECT_NAME_10")
	@Basic(optional = true)
	private String selectName10;

	@Column(name = "SELECT_ID_11")
	@Basic(optional = true)
	private BigInteger selectId11;

	@Column(name = "SELECT_CODE_11")
	@Basic(optional = true)
	private String selectCode11;

	@Column(name = "SELECT_NAME_11")
	@Basic(optional = true)
	private String selectName11;

	@Column(name = "SELECT_ID_12")
	@Basic(optional = true)
	private BigInteger selectId12;

	@Column(name = "SELECT_CODE_12")
	@Basic(optional = true)
	private String selectCode12;

	@Column(name = "SELECT_NAME_12")
	@Basic(optional = true)
	private String selectName12;

	@Column(name = "SELECT_ID_13")
	@Basic(optional = true)
	private BigInteger selectId13;

	@Column(name = "SELECT_CODE_13")
	@Basic(optional = true)
	private String selectCode13;

	@Column(name = "SELECT_NAME_13")
	@Basic(optional = true)
	private String selectName13;

	@Column(name = "SELECT_ID_14")
	@Basic(optional = true)
	private BigInteger selectId14;

	@Column(name = "SELECT_CODE_14")
	@Basic(optional = true)
	private String selectCode14;

	@Column(name = "SELECT_NAME_14")
	@Basic(optional = true)
	private String selectName14;

	@Column(name = "SELECT_ID_15")
	@Basic(optional = true)
	private BigInteger selectId15;

	@Column(name = "SELECT_CODE_15")
	@Basic(optional = true)
	private String selectCode15;

	@Column(name = "SELECT_NAME_15")
	@Basic(optional = true)
	private String selectName15;

	@Column(name = "SELECT_ID_16")
	@Basic(optional = true)
	private BigInteger selectId16;

	@Column(name = "SELECT_CODE_16")
	@Basic(optional = true)
	private String selectCode16;

	@Column(name = "SELECT_NAME_16")
	@Basic(optional = true)
	private String selectName16;

	@Column(name = "SELECT_ID_17")
	@Basic(optional = true)
	private BigInteger selectId17;

	@Column(name = "SELECT_CODE_17")
	@Basic(optional = true)
	private String selectCode17;

	@Column(name = "SELECT_NAME_17")
	@Basic(optional = true)
	private String selectName17;

	@Column(name = "SELECT_ID_18")
	@Basic(optional = true)
	private BigInteger selectId18;

	@Column(name = "SELECT_CODE_18")
	@Basic(optional = true)
	private String selectCode18;

	@Column(name = "SELECT_NAME_18")
	@Basic(optional = true)
	private String selectName18;

	@Column(name = "SELECT_ID_19")
	@Basic(optional = true)
	private BigInteger selectId19;

	@Column(name = "SELECT_CODE_19")
	@Basic(optional = true)
	private String selectCode19;

	@Column(name = "SELECT_NAME_19")
	@Basic(optional = true)
	private String selectName19;

	@Column(name = "SELECT_ID_20")
	@Basic(optional = true)
	private BigInteger selectId20;

	@Column(name = "SELECT_CODE_20")
	@Basic(optional = true)
	private String selectCode20;

	@Column(name = "SELECT_NAME_20")
	@Basic(optional = true)
	private String selectName20;

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
		entity.rptId = BigInteger.valueOf(domain.getRptId().map(m -> m).orElse(null));
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
		entity.selectCode20 = domain.getSelectCode20().map(m -> m).orElse(null);
		entity.selectName20 = domain.getSelectName20().map(m -> m).orElse(null);

		return entity;
	}

	public PersonalInformation toDomain() {
		PersonalInformation domain = new PersonalInformation();

		domain.setHistId(this.hisId);
		domain.setContractCd(this.contractCd);
		domain.setCid(Optional.of(this.cId));
		domain.setCompanyCode(Optional.of(this.ccd));
		domain.setPId(this.pId);
		domain.setSid(Optional.of(this.scd));
		domain.setScd(Optional.of(this.scd));
		domain.setPersonName(Optional.of(this.personName));
		domain.setWorkId(this.workId);
		domain.setWorkName(Optional.of(this.workName));
		domain.setStartDate(this.startDate);
		domain.setEndDate(this.endDate);
		domain.setReleaseDate(Optional.of(this.releaseDate));
		domain.setRequestFlg(this.requestFlg);
		domain.setRptLayoutId(this.rptLayoutId);
		domain.setRptId(Optional.ofNullable(this.rptId != null ? this.rptId.longValue() : null));
		domain.setRptNumber(Optional.ofNullable(this.rptNumber));

		domain.setStr01(Optional.ofNullable(this.str01));
		domain.setStr02(Optional.ofNullable(this.str02));
		domain.setStr03(Optional.ofNullable(this.str03));
		domain.setStr04(Optional.ofNullable(this.str04));
		domain.setStr05(Optional.ofNullable(this.str05));
		domain.setStr06(Optional.ofNullable(this.str06));
		domain.setStr07(Optional.ofNullable(this.str07));
		domain.setStr08(Optional.ofNullable(this.str08));
		domain.setStr09(Optional.ofNullable(this.str09));
		domain.setStr10(Optional.ofNullable(this.str10));
		domain.setStr11(Optional.ofNullable(this.str11));
		domain.setStr12(Optional.ofNullable(this.str12));
		domain.setStr13(Optional.ofNullable(this.str13));
		domain.setStr14(Optional.ofNullable(this.str14));
		domain.setStr15(Optional.ofNullable(this.str15));
		domain.setStr16(Optional.ofNullable(this.str16));
		domain.setStr17(Optional.ofNullable(this.str17));
		domain.setStr18(Optional.ofNullable(this.str18));
		domain.setStr19(Optional.ofNullable(this.str19));
		domain.setStr20(Optional.ofNullable(this.str20));
		domain.setStr21(Optional.ofNullable(this.str21));
		domain.setStr22(Optional.ofNullable(this.str22));
		domain.setStr23(Optional.ofNullable(this.str23));
		domain.setStr24(Optional.ofNullable(this.str24));
		domain.setStr25(Optional.ofNullable(this.str25));
		domain.setStr26(Optional.ofNullable(this.str26));
		domain.setStr27(Optional.ofNullable(this.str27));
		domain.setStr28(Optional.ofNullable(this.str28));
		domain.setStr29(Optional.ofNullable(this.str29));
		domain.setStr30(Optional.ofNullable(this.str30));
		domain.setStr31(Optional.ofNullable(this.str31));
		domain.setStr32(Optional.ofNullable(this.str32));
		domain.setStr33(Optional.ofNullable(this.str33));
		domain.setStr34(Optional.ofNullable(this.str34));
		domain.setStr35(Optional.ofNullable(this.str35));
		domain.setStr36(Optional.ofNullable(this.str36));
		domain.setStr37(Optional.ofNullable(this.str37));
		domain.setStr38(Optional.ofNullable(this.str38));
		domain.setStr39(Optional.ofNullable(this.str39));
		domain.setStr40(Optional.ofNullable(this.str40));
		domain.setStr41(Optional.ofNullable(this.str41));
		domain.setStr42(Optional.ofNullable(this.str42));
		domain.setStr43(Optional.ofNullable(this.str43));
		domain.setStr44(Optional.ofNullable(this.str44));
		domain.setStr45(Optional.ofNullable(this.str45));
		domain.setStr46(Optional.ofNullable(this.str46));
		domain.setStr47(Optional.ofNullable(this.str47));
		domain.setStr48(Optional.ofNullable(this.str48));
		domain.setStr49(Optional.ofNullable(this.str49));
		domain.setStr50(Optional.ofNullable(this.str50));
		domain.setStr51(Optional.ofNullable(this.str51));
		domain.setStr52(Optional.ofNullable(this.str52));
		domain.setStr53(Optional.ofNullable(this.str53));
		domain.setStr54(Optional.ofNullable(this.str54));
		domain.setStr55(Optional.ofNullable(this.str55));
		domain.setStr56(Optional.ofNullable(this.str56));
		domain.setStr57(Optional.ofNullable(this.str57));
		domain.setStr58(Optional.ofNullable(this.str58));
		domain.setStr59(Optional.ofNullable(this.str59));
		domain.setStr60(Optional.ofNullable(this.str60));

		domain.setDate01(Optional.ofNullable(this.date01));
		domain.setDate02(Optional.ofNullable(this.date02));
		domain.setDate03(Optional.ofNullable(this.date03));
		domain.setDate04(Optional.ofNullable(this.date04));
		domain.setDate05(Optional.ofNullable(this.date05));
		domain.setDate06(Optional.ofNullable(this.date06));
		domain.setDate07(Optional.ofNullable(this.date07));
		domain.setDate08(Optional.ofNullable(this.date08));
		domain.setDate09(Optional.ofNullable(this.date09));
		domain.setDate10(Optional.ofNullable(this.date10));

		domain.setInt01(this.int01);
		domain.setInt02(this.int02);
		domain.setInt03(this.int03);
		domain.setInt04(this.int04);
		domain.setInt05(this.int05);
		domain.setInt06(this.int06);
		domain.setInt07(this.int07);
		domain.setInt08(this.int08);
		domain.setInt09(this.int09);
		domain.setInt10(this.int10);
		domain.setInt11(this.int11);
		domain.setInt12(this.int12);
		domain.setInt13(this.int13);
		domain.setInt14(this.int14);
		domain.setInt15(this.int15);
		domain.setInt16(this.int16);
		domain.setInt17(this.int17);
		domain.setInt18(this.int18);
		domain.setInt19(this.int19);
		domain.setInt20(this.int20);
		domain.setInt21(this.int21);
		domain.setInt22(this.int22);
		domain.setInt23(this.int23);
		domain.setInt24(this.int24);
		domain.setInt25(this.int25);
		domain.setInt26(this.int26);
		domain.setInt27(this.int27);
		domain.setInt28(this.int28);
		domain.setInt29(this.int29);
		domain.setInt30(this.int30);

		domain.setNumber01(this.number01 == null ? null : this.number01.doubleValue());
		domain.setNumber02(this.number02 == null ? null : this.number02.doubleValue());
		domain.setNumber03(this.number03 == null ? null : this.number03.doubleValue());
		domain.setNumber04(this.number04 == null ? null : this.number04.doubleValue());
		domain.setNumber05(this.number05 == null ? null : this.number05.doubleValue());
		domain.setNumber06(this.number06 == null ? null : this.number06.doubleValue());
		domain.setNumber07(this.number07 == null ? null : this.number07.doubleValue());
		domain.setNumber08(this.number08 == null ? null : this.number08.doubleValue());
		domain.setNumber09(this.number09 == null ? null : this.number09.doubleValue());
		domain.setNumber10(this.number10 == null ? null : this.number10.doubleValue());
		domain.setNumber11(this.number11 == null ? null : this.number11.doubleValue());
		domain.setNumber12(this.number12 == null ? null : this.number12.doubleValue());
		domain.setNumber13(this.number13 == null ? null : this.number13.doubleValue());
		domain.setNumber14(this.number14 == null ? null : this.number14.doubleValue());
		domain.setNumber15(this.number15 == null ? null : this.number15.doubleValue());
		domain.setNumber16(this.number16 == null ? null : this.number16.doubleValue());
		domain.setNumber17(this.number17 == null ? null : this.number17.doubleValue());
		domain.setNumber18(this.number18 == null ? null : this.number18.doubleValue());
		domain.setNumber19(this.number19 == null ? null : this.number19.doubleValue());
		domain.setNumber20(this.number20 == null ? null : this.number20.doubleValue());
		domain.setNumber21(this.number21 == null ? null : this.number21.doubleValue());
		domain.setNumber22(this.number22 == null ? null : this.number22.doubleValue());
		domain.setNumber23(this.number23 == null ? null : this.number23.doubleValue());
		domain.setNumber24(this.number24 == null ? null : this.number24.doubleValue());
		domain.setNumber25(this.number25 == null ? null : this.number25.doubleValue());
		domain.setNumber26(this.number26 == null ? null : this.number26.doubleValue());
		domain.setNumber27(this.number27 == null ? null : this.number27.doubleValue());
		domain.setNumber28(this.number28 == null ? null : this.number28.doubleValue());
		domain.setNumber29(this.number29 == null ? null : this.number29.doubleValue());
		domain.setNumber30(this.number30 == null ? null : this.number30.doubleValue());

		domain.setSelectId01(this.selectId01 == null ? null : this.selectId01.longValue());
		domain.setSelectCode01(Optional.ofNullable(this.selectCode01));
		domain.setSelectName01(Optional.ofNullable(this.selectName01));
		
		domain.setSelectId02(this.selectId02 == null ? null : this.selectId02.longValue());
		domain.setSelectCode02(Optional.ofNullable(this.selectCode02));
		domain.setSelectName02(Optional.ofNullable(this.selectName02));
		
		domain.setSelectId03(this.selectId03 == null ? null : this.selectId03.longValue());
		domain.setSelectCode03(Optional.ofNullable(this.selectCode03));
		domain.setSelectName03(Optional.ofNullable(this.selectName03));
		
		domain.setSelectId04(this.selectId04 == null ? null : this.selectId04.longValue());
		domain.setSelectCode04(Optional.ofNullable(this.selectCode04));
		domain.setSelectName04(Optional.ofNullable(this.selectName04));
		
		domain.setSelectId05(this.selectId05 == null ? null : this.selectId05.longValue());
		domain.setSelectCode05(Optional.ofNullable(this.selectCode05));
		domain.setSelectName05(Optional.ofNullable(this.selectName05));
		
		domain.setSelectId06(this.selectId06 == null ? null : this.selectId06.longValue());
		domain.setSelectCode06(Optional.ofNullable(this.selectCode06));
		domain.setSelectName06(Optional.ofNullable(this.selectName06));
		
		domain.setSelectId07(this.selectId07 == null ? null : this.selectId07.longValue());
		domain.setSelectCode07(Optional.ofNullable(this.selectCode07));
		domain.setSelectName07(Optional.ofNullable(this.selectName07));
		
		domain.setSelectId08(this.selectId08 == null ? null : this.selectId08.longValue());
		domain.setSelectCode08(Optional.ofNullable(this.selectCode08));
		domain.setSelectName08(Optional.ofNullable(this.selectName08));
		
		domain.setSelectId09(this.selectId09 == null ? null : this.selectId09.longValue());
		domain.setSelectCode09(Optional.ofNullable(this.selectCode09));
		domain.setSelectName09(Optional.ofNullable(this.selectName09));
		
		domain.setSelectId10(this.selectId10 == null ? null : this.selectId10.longValue());
		domain.setSelectCode10(Optional.ofNullable(this.selectCode10));
		domain.setSelectName10(Optional.ofNullable(this.selectName10));
		
		domain.setSelectId11(this.selectId11 == null ? null : this.selectId11.longValue());
		domain.setSelectCode11(Optional.ofNullable(this.selectCode11));
		domain.setSelectName11(Optional.ofNullable(this.selectName11));
		
		domain.setSelectId12(this.selectId12 == null ? null : this.selectId12.longValue());
		domain.setSelectCode12(Optional.ofNullable(this.selectCode12));
		domain.setSelectName12(Optional.ofNullable(this.selectName12));
		
		domain.setSelectId13(this.selectId13 == null ? null : this.selectId13.longValue());
		domain.setSelectCode13(Optional.ofNullable(this.selectCode13));
		domain.setSelectName13(Optional.ofNullable(this.selectName13));
		
		domain.setSelectId14(this.selectId14 == null ? null : this.selectId14.longValue());
		domain.setSelectCode14(Optional.ofNullable(this.selectCode14));
		domain.setSelectName14(Optional.ofNullable(this.selectName14));
		
		domain.setSelectId15(this.selectId15 == null ? null : this.selectId15.longValue());
		domain.setSelectCode15(Optional.ofNullable(this.selectCode15));
		domain.setSelectName15(Optional.ofNullable(this.selectName15));
		
		domain.setSelectId16(this.selectId16 == null ? null : this.selectId16.longValue());
		domain.setSelectCode16(Optional.ofNullable(this.selectCode16));
		domain.setSelectName16(Optional.ofNullable(this.selectName16));
		
		domain.setSelectId17(this.selectId17 == null ? null : this.selectId17.longValue());
		domain.setSelectCode17(Optional.ofNullable(this.selectCode17));
		domain.setSelectName17(Optional.ofNullable(this.selectName17));
		
		domain.setSelectId18(this.selectId18 == null ? null : this.selectId18.longValue());
		domain.setSelectCode18(Optional.ofNullable(this.selectCode18));
		domain.setSelectName18(Optional.ofNullable(this.selectName18));
		
		domain.setSelectId19(this.selectId19 == null ? null : this.selectId19.longValue());
		domain.setSelectCode19(Optional.ofNullable(this.selectCode19));
		domain.setSelectName19(Optional.ofNullable(this.selectName19));
		
		domain.setSelectId20(this.selectId20 == null ? null : this.selectId20.longValue());
		domain.setSelectCode20(Optional.ofNullable(this.selectCode20));
		domain.setSelectName20(Optional.ofNullable(this.selectName20));

		return domain;
	}
}