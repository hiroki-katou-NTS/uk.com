package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 *個人情報追加データ
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddPersonInfoHRInput {

	// 契約コード
	private String contractCd;
	
	// 終了日
	private GeneralDate endDate;
	
	// 履歴ID
	private String histId;
	
	// 個人ID
	private String pId;
	
	// 開始日
	private GeneralDate startDate;
	
	// 業務区分
	private int workId;
	
	// Optional
	
	// 会社コード
	private String companyCode;
	
	// 会社ID
	private String cid;

	// 日付項目01
	private GeneralDate date01;

	// 日付項目02
	private GeneralDate date02;

	// 日付項目03
	private GeneralDate date03;

	// 日付項目04
	private GeneralDate date04;

	// 日付項目05
	private GeneralDate date05;

	// 日付項目06
	private GeneralDate date06;

	// 日付項目07
	private GeneralDate date07;

	// 日付項目08
	private GeneralDate date08;

	// 日付項目09
	private GeneralDate date09;

	// 日付項目10
	private GeneralDate date10;
	
	// 整数項目01
	private Integer int01;

	// 整数項目02
	private Integer int02;

	// 整数項目03
	private Integer int03;

	// 整数項目04
	private Integer int04;

	// 整数項目05
	private Integer int05;

	// 整数項目06
	private Integer int06;

	// 整数項目07
	private Integer int07;

	// 整数項目08
	private Integer int08;

	// 整数項目09
	private Integer int09;

	// 整数項目10
	private Integer int10;

	// 整数項目11
	private Integer int11;

	// 整数項目12
	private Integer int12;

	// 整数項目13
	private Integer int13;

	// 整数項目14
	private Integer int14;

	// 整数項目15
	private Integer int15;

	// 整数項目16
	private Integer int16;

	// 整数項目17
	private Integer int17;

	// 整数項目18
	private Integer int18;

	// 整数項目19
	private Integer int19;

	// 整数項目20
	private Integer int20;

	// 整数項目21
	private Integer int21;

	// 整数項目22
	private Integer int22;

	// 整数項目23
	private Integer int23;

	// 整数項目24
	private Integer int24;

	// 整数項目25
	private Integer int25;

	// 整数項目26
	private Integer int26;

	// 整数項目27
	private Integer int27;

	// 整数項目28
	private Integer int28;

	// 整数項目29
	private Integer int29;

	// 整数項目30
	private Integer int30;
	
	// 実数項目01
	private Double number01;
	
	// 実数項目02
	private Double number02;
	
	// 実数項目03
	private Double number03;
	
	// 実数項目04
	private Double number04;
	
	// 実数項目05
	private Double number05;
	
	// 実数項目06
	private Double number06;
	
	// 実数項目07
	private Double number07;
	
	// 実数項目08
	private Double number08;
	
	// 実数項目09
	private Double number09;
	
	// 実数項目10
	private Double number10;
	
	// 実数項目11
	private Double number11;
	
	// 実数項目12
	private Double number12;
	
	// 実数項目13
	private Double number13;
	
	// 実数項目14
	private Double number14;
	
	// 実数項目15
	private Double number15;
	
	// 実数項目16
	private Double number16;
	
	// 実数項目17
	private Double number17;
	
	// 実数項目18
	private Double number18;
	
	// 実数項目19
	private Double number19;
	
	// 実数項目20
	private Double number20;
	
	// 実数項目21
	private Double number21;
	
	// 実数項目22
	private Double number22;
	
	// 実数項目23
	private Double number23;
	
	// 実数項目24
	private Double number24;
	
	// 実数項目25
	private Double number25;
	
	// 実数項目26
	private Double number26;
	
	// 実数項目27
	private Double number27;
	
	// 実数項目28
	private Double number28;
	
	// 実数項目29
	private Double number29;
	
	// 実数項目30
	private Double number30;
	
	// 社員名
	private String personName;
	
	// 公開日
	private GeneralDate releaseDate;
	
	// 届出区分
	private Integer requestFlg;
	
	// 届出種類ID
	private Integer rptLayoutId;
	
	// 届出番号
	private String rptNumber;
	
	// 届出ID
	private Long rptId;
	
	// 社員コード
	private String scd;
	
	// 選択項目CD01
	private String selectCode01;
	
	// 選択項目CD02
	private String selectCode02;
	
	// 選択項目CD03
	private String selectCode03;
	
	// 選択項目CD04
	private String selectCode04;
	
	// 選択項目CD05
	private String selectCode05;
	
	// 選択項目CD06
	private String selectCode06;
	
	// 選択項目CD07
	private String selectCode07;
	
	// 選択項目CD08
	private String selectCode08;
	
	// 選択項目CD09
	private String selectCode09;
	
	// 選択項目CD10
	private String selectCode10;
	
	// 選択項目CD11
	private String selectCode11;
	
	// 選択項目CD12
	private String selectCode12;
	
	// 選択項目CD13
	private String selectCode13;
	
	// 選択項目CD14
	private String selectCode14;
	
	// 選択項目CD15
	private String selectCode15;
	
	// 選択項目CD16
	private String selectCode16;
	
	// 選択項目CD17
	private String selectCode17;
	
	// 選択項目CD18
	private String selectCode18;
	
	// 選択項目CD19
	private String selectCode19;
	
	// 選択項目CD20
	private String selectCode20;
	
	// 選択項目ID01
	private Long selectId01;
	
	// 選択項目ID02
	private Long selectId02;
	
	// 選択項目ID03
	private Long selectId03;
	
	// 選択項目ID04
	private Long selectId04;
	
	// 選択項目ID05
	private Long selectId05;
	
	// 選択項目ID06
	private Long selectId06;
	
	// 選択項目ID07
	private Long selectId07;
	
	// 選択項目ID08
	private Long selectId08;
	
	// 選択項目ID09
	private Long selectId09;
	
	// 選択項目ID10
	private Long selectId10;
	
	// 選択項目ID11
	private Long selectId11;
	
	// 選択項目ID12
	private Long selectId12;
	
	// 選択項目ID13
	private Long selectId13;
	
	// 選択項目ID14
	private Long selectId14;
	
	// 選択項目ID15
	private Long selectId15;
	
	// 選択項目ID16
	private Long selectId16;
	
	// 選択項目ID17
	private Long selectId17;
	
	// 選択項目ID18
	private Long selectId18;
	
	// 選択項目ID19
	private Long selectId19;
	
	// 選択項目ID20
	private Long selectId20;
	
	// 選択項目名称01
	private String selectName01;
	
	// 選択項目名称02
	private String selectName02;
	
	// 選択項目名称03
	private String selectName03;
	
	// 選択項目名称04
	private String selectName04;
	
	// 選択項目名称05
	private String selectName05;
	
	// 選択項目名称06
	private String selectName06;
	
	// 選択項目名称07
	private String selectName07;
	
	// 選択項目名称08
	private String selectName08;
	
	// 選択項目名称09
	private String selectName09;
	
	// 選択項目名称10
	private String selectName10;
	
	// 選択項目名称11
	private String selectName11;
	
	// 選択項目名称12
	private String selectName12;
	
	// 選択項目名称13
	private String selectName13;
	
	// 選択項目名称14
	private String selectName14;
	
	// 選択項目名称15
	private String selectName15;
	
	// 選択項目名称16
	private String selectName16;
	
	// 選択項目名称17
	private String selectName17;
	
	// 選択項目名称18
	private String selectName18;
	
	// 選択項目名称19
	private String selectName19;
	
	// 選択項目名称20
	private String selectName20;
	
	// 社員ID
	private String sid;
	
	// 文字項目01
	private String str01;
	
	// 文字項目02
	private String str02;
	
	// 文字項目03
	private String str03;
	
	// 文字項目04
	private String str04;
	
	// 文字項目05
	private String str05;
	
	// 文字項目06
	private String str06;
	
	// 文字項目07
	private String str07;
	
	// 文字項目08
	private String str08;
	
	// 文字項目09
	private String str09;
	
	// 文字項目10
	private String str10;
	
	// 文字項目11
	private String str11;
	
	// 文字項目12
	private String str12;
	
	// 文字項目13
	private String str13;
	
	// 文字項目14
	private String str14;
	
	// 文字項目15
	private String str15;
	
	// 文字項目16
	private String str16;
	
	// 文字項目17
	private String str17;
	
	// 文字項目18
	private String str18;
	
	// 文字項目19
	private String str19;
	
	// 文字項目20
	private String str20;
	
	// 文字項目21
	private String str21;
	
	// 文字項目22
	private String str22;
	
	// 文字項目23
	private String str23;
	
	// 文字項目24
	private String str24;
	
	// 文字項目25
	private String str25;
	
	// 文字項目26
	private String str26;
	
	// 文字項目27
	private String str27;
	
	// 文字項目28
	private String str28;
	
	// 文字項目29
	private String str29;
	
	// 文字項目30
	private String str30;
	
	// 文字項目31
	private String str31;
	
	// 文字項目32
	private String str32;
	
	// 文字項目33
	private String str33;
	
	// 文字項目34
	private String str34;
	
	// 文字項目35
	private String str35;
	
	// 文字項目36
	private String str36;
	
	// 文字項目37
	private String str37;
	
	// 文字項目38
	private String str38;
	
	// 文字項目39
	private String str39;
	
	// 文字項目40
	private String str40;
	
	// 文字項目41
	private String str41;
	
	// 文字項目42
	private String str42;
	
	// 文字項目43
	private String str43;
	
	// 文字項目44
	private String str44;
	
	// 文字項目45
	private String str45;
	
	// 文字項目46
	private String str46;
	
	// 文字項目47
	private String str47;
	
	// 文字項目48
	private String str48;
	
	// 文字項目49
	private String str49;
	
	// 文字項目50
	private String str50;
	
	// 文字項目51
	private String str51;
	
	// 文字項目52
	private String str52;
	
	// 文字項目53
	private String str53;
	
	// 文字項目54
	private String str54;
	
	// 文字項目55
	private String str55;
	
	// 文字項目56
	private String str56;
	
	// 文字項目57
	private String str57;
	
	// 文字項目58
	private String str58;
	
	// 文字項目59
	private String str59;
	
	// 文字項目60
	private String str60;
	
	// 業務名
	private String workName;
}