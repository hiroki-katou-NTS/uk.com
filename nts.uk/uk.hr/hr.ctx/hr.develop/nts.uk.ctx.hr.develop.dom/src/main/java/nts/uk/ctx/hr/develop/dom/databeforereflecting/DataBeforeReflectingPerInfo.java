/**
 * 
 */
package nts.uk.ctx.hr.develop.dom.databeforereflecting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * 個人情報反映前データ
 */
@NoArgsConstructor
@Getter
public class DataBeforeReflectingPerInfo extends AggregateRoot {

	private String historyId;
	private String contractCode;
	private String companyId;
	private String companyCode;
	private String pId;
	private String sId;
	private String scd;
	private Integer workId;
	private String personName;
	private String workName;
	private RequestFlag requestFlag;
	private GeneralDate registerDate;
	private GeneralDateTime releaseDate;
	private OnHoldFlag onHoldFlag;
	private Status stattus;
	private String histId_Refer;

	private GeneralDateTime date_01;
	private GeneralDateTime date_02;
	private GeneralDateTime date_03;
	private GeneralDateTime date_04;
	private GeneralDateTime date_05;
	private GeneralDateTime date_06;
	private GeneralDateTime date_07;
	private GeneralDateTime date_08;
	private GeneralDateTime date_09;
	private GeneralDateTime date_10;

	private Integer int_01;
	private Integer int_02;
	private Integer int_03;
	private Integer int_04;
	private Integer int_05;
	private Integer int_06;
	private Integer int_07;
	private Integer int_08;
	private Integer int_09;
	private Integer int_10;
	private Integer int_11;
	private Integer int_12;
	private Integer int_13;
	private Integer int_14;
	private Integer int_15;
	private Integer int_16;
	private Integer int_17;
	private Integer int_18;
	private Integer int_19;
	private Integer int_20;

	private Float num_01;
	private Float num_02;
	private Float num_03;
	private Float num_04;
	private Float num_05;
	private Float num_06;
	private Float num_07;
	private Float num_08;
	private Float num_09;
	private Float num_10;
	private Float num_11;
	private Float num_12;
	private Float num_13;
	private Float num_14;
	private Float num_15;
	private Float num_16;
	private Float num_17;
	private Float num_18;
	private Float num_19;
	private Float num_20;

	private String select_code_01;
	private String select_code_02;
	private String select_code_03;
	private String select_code_04;
	private String select_code_05;
	private String select_code_06;
	private String select_code_07;
	private String select_code_08;
	private String select_code_09;
	private String select_code_10;

	private Integer select_id_01;
	private Integer select_id_02;
	private Integer select_id_03;
	private Integer select_id_04;
	private Integer select_id_05;
	private Integer select_id_06;
	private Integer select_id_07;
	private Integer select_id_08;
	private Integer select_id_09;
	private Integer select_id_10;

	private String select_name_01;
	private String select_name_02;
	private String select_name_03;
	private String select_name_04;
	private String select_name_05;
	private String select_name_06;
	private String select_name_07;
	private String select_name_08;
	private String select_name_09;
	private String select_name_10;

	private String str_01;
	private String str_02;
	private String str_03;
	private String str_04;
	private String str_05;
	private String str_06;
	private String str_07;
	private String str_08;
	private String str_09;
	private String str_10;

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
			String select_code_09, String select_code_10, Integer select_id_01, Integer select_id_02,
			Integer select_id_03, Integer select_id_04, Integer select_id_05, Integer select_id_06,
			Integer select_id_07, Integer select_id_08, Integer select_id_09, Integer select_id_10,
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
		this.str_01 = str_01;
		this.str_02 = str_02;
		this.str_03 = str_03;
		this.str_04 = str_04;
		this.str_05 = str_05;
		this.str_06 = str_06;
		this.str_07 = str_07;
		this.str_08 = str_08;
		this.str_09 = str_09;
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
			String select_code_08, String select_code_09, String select_code_10, Integer select_id_01,
			Integer select_id_02, Integer select_id_03, Integer select_id_04, Integer select_id_05,
			Integer select_id_06, Integer select_id_07, Integer select_id_08, Integer select_id_09,
			Integer select_id_10, String select_name_01, String select_name_02, String select_name_03,
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

}
