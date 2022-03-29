/**
 * 9:15:47 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.TimeItemTypeAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.TimeItemName;

/**
 * 加給時間項目
 * @author hungnm
 *
 */
@Getter
public class BonusPayTimeItem extends AggregateRoot {

	//	会社ID
	private String companyId;

	//	使用区分
	private UseAtr useAtr;

	//  名称
	private TimeItemName timeItemName;
	
	//	加給項目NO
	private int id;

	//	加給項目区分
	private TimeItemTypeAtr timeItemTypeAtr;

	private BonusPayTimeItem() {
		super();
	}

	public BonusPayTimeItem(String companyId, UseAtr useAtr, TimeItemName timeItemName,
			int id, TimeItemTypeAtr timeItemTypeAtr) {
		super();
		this.companyId = companyId;
		this.useAtr = useAtr;
		this.timeItemName = timeItemName;
		this.id = id;
		this.timeItemTypeAtr = timeItemTypeAtr;
	}

	public static BonusPayTimeItem createFromJavaType(String companyId, int useAtr,
			String timeItemName, int id, int timeItemTypeAtr) {
		return new BonusPayTimeItem(companyId,
				EnumAdaptor.valueOf(useAtr, UseAtr.class), new TimeItemName(timeItemName), id,
				EnumAdaptor.valueOf(timeItemTypeAtr, TimeItemTypeAtr.class));
	}

	public static BonusPayTimeItem initNewData(String companyId, int useAtr, String timeItemName, int id,
			int timeItemTypeAtr) {
		BonusPayTimeItem newData = new BonusPayTimeItem();
		newData.companyId = companyId;
		newData.useAtr = EnumAdaptor.valueOf(useAtr, UseAtr.class);
		newData.timeItemName = new TimeItemName(timeItemName);
		newData.id = id;
		newData.timeItemTypeAtr = EnumAdaptor.valueOf(timeItemTypeAtr, TimeItemTypeAtr.class);
		return newData;
	}
	
	/**
	 * 	[1] 加給時間に対応する日次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getDaiLyAttendanceId() {
		if(this.timeItemTypeAtr.isNomalType()) {
			return this.getDaiLyAttdIdNormalByNo(this.id);
		}
		return this.getDaiLyAttdIdSpecialByNo(this.id);
	}
	/**
	 * 	[2] 加給時間に対応する月次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceId() {
		if(this.timeItemTypeAtr.isNomalType()) {
			return this.getMonthlyAttdIdNormalByNo(this.id);
		}
		return this.getMonthlyAttdIdSpecialByNo(this.id);
	}
	
	/**
	 * 	[3] 利用できない日次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getDaiLyAttendanceIdNotAvailable() {
		if(this.useAtr == UseAtr.NOT_USE) {
			return this.getDaiLyAttendanceId();
		}
		return new ArrayList<>();
	}
	
	/**
	 * 	[4] 利用できない月次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdNotAvailable() {
		if(this.useAtr == UseAtr.NOT_USE) {
			return this.getMonthlyAttendanceId();
		}
		return new ArrayList<>();
	}
	
	private List<Integer> getDaiLyAttdIdNormalByNo(int id) {
		switch(id) {
		case 1:
			return Arrays.asList(316,336,346,356);
		case 2: 
			return Arrays.asList(317,337,347,357);
		case 3: 
			return Arrays.asList(318,338,348,358);
		case 4: 
			return Arrays.asList(319,339,349,359);
		case 5: 
			return Arrays.asList(320,340,350,360);
		case 6: 
			return Arrays.asList(321,341,351,361);
		case 7: 
			return Arrays.asList(322,342,352,362);
		case 8: 
			return Arrays.asList(323,343,353,363);
		case 9: 
			return Arrays.asList(324,344,354,364);
		default : //10
			return Arrays.asList(325,345,355,365);
		}
	}
	
	private List<Integer> getDaiLyAttdIdSpecialByNo(int id) {
		switch(id) {
		case 1:
			return Arrays.asList(366,386,396,406);
		case 2: 
			return Arrays.asList(367,387,397,407);
		case 3: 
			return Arrays.asList(368,388,398,408);
		case 4: 
			return Arrays.asList(369,389,399,409);
		case 5: 
			return Arrays.asList(370,390,400,410);
		case 6: 
			return Arrays.asList(371,391,401,411);
		case 7: 
			return Arrays.asList(372,392,402,412);
		case 8: 
			return Arrays.asList(373,393,403,413);
		case 9: 
			return Arrays.asList(374,394,404,414);
		default : //10
			return Arrays.asList(375,395,405,415);
		}
	}
	
	private List<Integer> getMonthlyAttdIdNormalByNo(int id) {
		switch(id) {
		case 1:
			return Arrays.asList(334,344,2097,2117);
		case 2: 
			return Arrays.asList(335,345,2098,2118);
		case 3: 
			return Arrays.asList(336,346,2099,2119);
		case 4: 
			return Arrays.asList(337,347,2100,2120);
		case 5: 
			return Arrays.asList(338,348,2101,2121);
		case 6: 
			return Arrays.asList(339,349,2102,2122);
		case 7: 
			return Arrays.asList(340,350,2103,2123);
		case 8: 
			return Arrays.asList(341,351,2104,2124);
		case 9: 
			return Arrays.asList(342,352,2105,2125);
		default : //10
			return Arrays.asList(343,353,2106,2126);
		}
	}
	
	private List<Integer> getMonthlyAttdIdSpecialByNo(int id) {
		switch(id) {
		case 1:
			return Arrays.asList(354,364,2107,2127);
		case 2: 
			return Arrays.asList(355,365,2108,2128);
		case 3: 
			return Arrays.asList(356,366,2109,2129);
		case 4: 
			return Arrays.asList(357,367,2110,2130);
		case 5: 
			return Arrays.asList(358,368,2111,2131);
		case 6: 
			return Arrays.asList(359,369,2112,2132);
		case 7: 
			return Arrays.asList(360,370,2113,2133);
		case 8: 
			return Arrays.asList(361,371,2114,2134);
		case 9: 
			return Arrays.asList(362,372,2115,2135);
		default : //10
			return Arrays.asList(363,373,2116,2136);
		}
	}

}