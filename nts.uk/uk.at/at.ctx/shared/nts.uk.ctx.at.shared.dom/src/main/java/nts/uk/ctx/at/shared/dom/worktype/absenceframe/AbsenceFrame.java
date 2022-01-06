package nts.uk.ctx.at.shared.dom.worktype.absenceframe;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;

/**
 * 欠勤枠
 * 
 * @author sonnh
 *
 */
@Getter
@NoArgsConstructor
public class AbsenceFrame extends AggregateRoot{
	
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 欠勤枠ID
	 */
	private int absenceFrameNo;
	
	/**
	 * 枠名称
	 */
	private WorkTypeName absenceFrameName;
	
	/**
	 * 利用区分
	 */
	private ManageDistinct deprecateAbsence;
	
	/**
	 * 
	 * @param companyId
	 * @param absenceFrameNo
	 * @param absenceFrameName
	 * @param deprecateAbsence
	 */
	public AbsenceFrame(String companyId, int absenceFrameNo, WorkTypeName absenceFrameName,
			ManageDistinct deprecateAbsence) {
		super();
		this.companyId = companyId;
		this.absenceFrameNo = absenceFrameNo;
		this.absenceFrameName = absenceFrameName;
		this.deprecateAbsence = deprecateAbsence;
	}
	
	/**
	 * [1] 欠勤枠に対応する月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthAttdItemsCorrespondAbsenteeism() {
		switch (this.absenceFrameNo) {
		case 1 :
			return Arrays.asList(239,1288);																											
		case 2 :
			return Arrays.asList(240,1289);																											
		case 3 :
			return Arrays.asList(241,1290);																										
		case 4 :
			return Arrays.asList(242,1291);																									
		case 5 :
			return Arrays.asList(243,1292);																								
		case 6 :
			return Arrays.asList(244,1293);																							
		case 7 :
			return Arrays.asList(245,1294);																						
		case 8 :
			return Arrays.asList(246,1295);																					
		case 9 :
			return Arrays.asList(247,1296);																				
		case 10	:
			return Arrays.asList(248,1297);																												
		case 11	:
			return Arrays.asList(249,1298);																												
		case 12	:
			return Arrays.asList(250,1299);																												
		case 13	:
			return Arrays.asList(251,1300);																												
		case 14	:
			return Arrays.asList(252,1301);																												
		case 15	:
			return Arrays.asList(253,1302);																												
		case 16	:
			return Arrays.asList(254,1303);																												
		case 17	:
			return Arrays.asList(255,1304);																												
		case 18	:
			return Arrays.asList(256,1305);																												
		case 19	:
			return Arrays.asList(257,1306);																												
		case 20	:
			return Arrays.asList(258,1307);																												
		case 21	:
			return Arrays.asList(259,1308);																												
		case 22	:
			return Arrays.asList(260,1309);																												
		case 23	:
			return Arrays.asList(261,1310);																												
		case 24	:
			return Arrays.asList(262,1311);																												
		case 25	:
			return Arrays.asList(263,1312);																												
		case 26	:
			return Arrays.asList(264,1313);																												
		case 27	:
			return Arrays.asList(265,1314);																												
		case 28	:
			return Arrays.asList(266,1315);																												
		case 29	:
			return Arrays.asList(267,1316);																												
		default	:
			return Arrays.asList(268,1317);	
		}
	}
	
	/**
	 * [2] 利用できない月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthAttdItems() {
		if (this.deprecateAbsence == ManageDistinct.NO) // cần QA lại vì ko giống EA
			return this.getMonthAttdItemsCorrespondAbsenteeism();
		return new ArrayList<>();
	}
	
	/**
	 * 
	 * @param companyId
	 * @param absenceFrameNo
	 * @param absenceFrameName
	 * @param deprecateAbsence
	 * @return
	 */
	public static AbsenceFrame createSimpleFromJavaType(String companyId, int absenceFrameNo, String absenceFrameName,
			int deprecateAbsence) {
		return new AbsenceFrame(
				companyId, 
				absenceFrameNo, 
				new WorkTypeName(absenceFrameName), 
				EnumAdaptor.valueOf(deprecateAbsence, ManageDistinct.class));
	}

	/**
	 * 
	 * @param companyId
	 * @param absenceFrameNo
	 * @param absenceFrameName
	 * @param deprecateAbsence
	 * @return
	 */
	public static AbsenceFrame createFromJavaType(String companyId, int absenceFrameNo, String absenceFrameName,
			int deprecateAbsence) {
		return new AbsenceFrame(companyId, absenceFrameNo, new WorkTypeName(absenceFrameName), EnumAdaptor.valueOf(deprecateAbsence, ManageDistinct.class));
	}
}
