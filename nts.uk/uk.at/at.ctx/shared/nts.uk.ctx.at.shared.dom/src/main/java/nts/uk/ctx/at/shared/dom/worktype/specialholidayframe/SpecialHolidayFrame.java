package nts.uk.ctx.at.shared.dom.worktype.specialholidayframe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 特別休暇枠
 * 
 * @author sonnh
 *
 */
@Getter
@NoArgsConstructor
public class SpecialHolidayFrame extends AggregateRoot{
	
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 特別休暇枠ID
	 */
	private int SpecialHdFrameNo;
	
	/**
	 * 枠名称
	 */
	private WorkTypeName SpecialHdFrameName;
	
	/**
	 * 利用区分
	 */
	private ManageDistinct deprecateSpecialHd;
	
	/**
	 * するしない区分
	 */
	private NotUseAtr timeMngAtr;
	
	
	/**
	 * 
	 * @param companyId
	 * @param specialHdFrameNo
	 * @param specialHdFrameName
	 * @param deprecateSpecialHd
	 */
	public SpecialHolidayFrame(String companyId, int specialHdFrameNo, WorkTypeName specialHdFrameName,
			ManageDistinct deprecateSpecialHd ,NotUseAtr timeMngAtr) {
		super();
		this.companyId = companyId;
		this.SpecialHdFrameNo = specialHdFrameNo;
		SpecialHdFrameName = specialHdFrameName;
		this.deprecateSpecialHd = deprecateSpecialHd;
		this.timeMngAtr = timeMngAtr;
	}
	
	/**
	 * [1] 特別休暇枠に対応する月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthlyAttendanceItemsSpecialLeave() {
		
		switch (this.SpecialHdFrameNo) {
		case 1 :
			return Arrays.asList(270,1319);																											
		case 2 :
			return Arrays.asList(271,1320);																												
		case 3 :
			return Arrays.asList(272,1321);																												
		case 4 :
			return Arrays.asList(273,1322);																												
		case 5 :
			return Arrays.asList(274,1323);																												
		case 6 :
			return Arrays.asList(275,1324);																												
		case 7 :
			return Arrays.asList(276,1325);																												
		case 8 :
			return Arrays.asList(277,1326);																												
		case 9 :
			return Arrays.asList(278,1327);																												
		case 10 :
			return Arrays.asList(279,1328);																												
		case 11 :
			return Arrays.asList(280,1329);																												
		case 12 :
			return Arrays.asList(281,1330);																												
		case 13 :
			return Arrays.asList(282,1331);																												
		case 14 :
			return Arrays.asList(283,1332);																												
		case 15 :
			return Arrays.asList(284,1333);																												
		case 16 :
			return Arrays.asList(285,1334);																												
		case 17 :
			return Arrays.asList(286,1335);																												
		case 18 :
			return Arrays.asList(287,1336);																												
		case 19 :
			return Arrays.asList(288,1337);																												
		case 20 :
			return Arrays.asList(289,1338);																												
		case 21 :
			return Arrays.asList(290,1339);																												
		case 22 :
			return Arrays.asList(291,1340);																												
		case 23 :
			return Arrays.asList(292,1341);																												
		case 24 :
			return Arrays.asList(293,1342);																												
		case 25 :
			return Arrays.asList(294,1343);																												
		case 26 :
			return Arrays.asList(295,1344);																												
		case 27 :
			return Arrays.asList(296,1345);																												
		case 28 :
			return Arrays.asList(297,1346);																												
		case 29 :
			return Arrays.asList(298,1347);																												
		default :
			return Arrays.asList(299,1348);	
		}
	}
	
	/**
	 * [2] 利用できない月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthlyAttendanceItems() {
		// @利用区分 == しない
		if (this.deprecateSpecialHd == ManageDistinct.NO) // cần QA lại vì khác với EA
			return this.getMonthlyAttendanceItemsSpecialLeave();
		return new ArrayList<>();
	}
	
	/**
	 * 
	 * @param companyId
	 * @param specialHdFrameNo
	 * @param specialHdFrameName
	 * @param deprecateSpecialHd
	 * @return
	 */
	public static SpecialHolidayFrame createSimpleFromJavaType(String companyId, int specialHdFrameNo, String specialHdFrameName,
			int deprecateSpecialHd , int timeMngAtr) {
		return new SpecialHolidayFrame(companyId, 
				specialHdFrameNo,
				new WorkTypeName(specialHdFrameName), 
				EnumAdaptor.valueOf(deprecateSpecialHd, ManageDistinct.class),
				EnumAdaptor.valueOf(timeMngAtr, NotUseAtr.class)
				);
	}

	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param specialHdFrameNo the special hd frame no
	 * @param specialHdFrameName the special hd frame name
	 * @param deprecateSpecialHd the deprecate special hd
	 * @return the special holiday frame
	 */
	public static SpecialHolidayFrame createFromJavaType(String companyId, int specialHdFrameNo, String specialHdFrameName,
			int deprecateSpecialHd , int timeMngAtr) {
		return new SpecialHolidayFrame(companyId,
				specialHdFrameNo, new WorkTypeName(specialHdFrameName), EnumAdaptor.valueOf(deprecateSpecialHd, ManageDistinct.class),
				EnumAdaptor.valueOf(timeMngAtr, NotUseAtr.class));
	}
}
