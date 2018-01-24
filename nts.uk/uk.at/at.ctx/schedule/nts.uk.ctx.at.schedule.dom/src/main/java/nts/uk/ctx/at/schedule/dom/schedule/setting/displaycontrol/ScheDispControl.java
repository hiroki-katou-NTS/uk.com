package nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.UseAtr;

/**
 * 勤務予定の表示制御
 * 
 * @author TanLV
 *
 */
@Getter
@AllArgsConstructor
public class ScheDispControl extends AggregateRoot {
	/** 会社ID */
	private String companyId;
	
	/** 資格表示記号 B1_14 */
	private PersonSymbolQualify personSyQualify;
	
	/** 半日表示区分 B2_4 */
	private UseAtr symbolHalfDayAtr;
	
	/** 勤務就業記号表示区分 B2_8 */
	private UseAtr symbolAtr;
	
	/** 取得超過表示区分 B3_4 */
	private UseAtr pubHolidayExcessAtr;

	/** 取得不足表示区分 B3_8 */
	private UseAtr pubHolidayShortageAtr;

	/** 半日記号 */
	private String symbolHalfDayName;
	
	private List<SchePerInfoAtr> schePerInfoAtr;
	
	private List<ScheQualifySet> scheQualifySet;
	
	/**
	 * Create From Java Type
	 * 
	 * @param companyId
	 * @param personSyQualify
	 * @param symbolHalfDayAtr
	 * @param symbolAtr
	 * @param pubHolidayExcessAtr
	 * @param pubHolidayShortageAtr
	 * @param symbolHalfDayName
	 * @param schePerInfoAtr
	 * @param scheEntitlementSet
	 * @return
	 */
	public static ScheDispControl createFromJavaType(String companyId, String personSyQualify, int symbolHalfDayAtr, int symbolAtr, int pubHolidayExcessAtr,
			int pubHolidayShortageAtr, String symbolHalfDayName, List<SchePerInfoAtr> schePerInfoAtr, List<ScheQualifySet> scheQualifySet) {
		
		return new ScheDispControl(companyId, new PersonSymbolQualify(personSyQualify), EnumAdaptor.valueOf(symbolHalfDayAtr, UseAtr.class), EnumAdaptor.valueOf(symbolAtr, UseAtr.class),
				EnumAdaptor.valueOf(pubHolidayExcessAtr, UseAtr.class), EnumAdaptor.valueOf(pubHolidayShortageAtr, UseAtr.class), symbolHalfDayName, schePerInfoAtr, scheQualifySet);
	}
}
