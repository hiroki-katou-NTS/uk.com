/**
 * 
 */
package nts.uk.ctx.hr.develop.dom.databeforereflecting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * 個人情報反映前データ
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DataBeforeReflectingPerInfo extends AggregateRoot{
	
	private String historyId;	
	private String contractCode;	
	private String companyId;	
	private String companyCode;
	private String pId;	
	private String sId;	
	private String scd;	
	private int workId;	
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
	
	private int int_01;
	private int int_02;
	private int int_03;
	private int int_04;
	private int int_05;
	private int int_06;
	private int int_07;
	private int int_08;
	private int int_09;
	private int int_10;
	private int int_11;
	private int int_12;
	private int int_13;
	private int int_14;
	private int int_15;
	private int int_16;
	private int int_17;
	private int int_18;
	private int int_19;
	private int int_20;
	
	private float num_01;
	private float num_02;
	private float num_03;
	private float num_04;
	private float num_05;
	private float num_06;
	private float num_07;
	private float num_08;
	private float num_09;
	private float num_10;
	private float num_11;
	private float num_12;
	private float num_13;
	private float num_14;
	private float num_15;
	private float num_16;
	private float num_17;
	private float num_18;
	private float num_19;
	private float num_20;
	
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
	
	private int select_id_01;
	private int select_id_02;
	private int select_id_03;
	private int select_id_04;
	private int select_id_05;
	private int select_id_06;
	private int select_id_07;
	private int select_id_08;
	private int select_id_09;
	private int select_id_10;
	
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
	
}
