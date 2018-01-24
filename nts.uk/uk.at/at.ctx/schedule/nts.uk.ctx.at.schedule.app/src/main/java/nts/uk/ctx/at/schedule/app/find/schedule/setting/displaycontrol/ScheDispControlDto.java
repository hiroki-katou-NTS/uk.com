package nts.uk.ctx.at.schedule.app.find.schedule.setting.displaycontrol;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.ScheDispControl;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheDispControlDto {
	/** 会社ID */
	private String companyId;
	
	/** 資格表示記号 B1_14 */
	private String personSyQualify;
	
	/** 半日表示区分 B2_4 */
	private int symbolHalfDayAtr;
	
	/** 勤務就業記号表示区分 B2_8 */
	private int symbolAtr;
	
	/** 取得超過表示区分 B3_4 */
	private int pubHolidayExcessAtr;

	/** 取得不足表示区分 B3_8 */
	private int pubHolidayShortageAtr;

	/** 半日記号 */
	private String symbolHalfDayName;
	
	private List<SchePerInfoAtrDto> schePerInfoAtr;
	
	private List<ScheQualifySetDto> scheQualifySet;
	
	/**
	 * From domain
	 * 
	 * @param domain
	 * @return
	 */
	public static ScheDispControlDto fromDomain(ScheDispControl domain){
    	List<SchePerInfoAtrDto> schePerInfoAtr = domain.getSchePerInfoAtr().stream()
				.map(x-> SchePerInfoAtrDto.fromDomain(x))
				.collect(Collectors.toList());
    	
    	List<ScheQualifySetDto> scheQualifySet = domain.getScheQualifySet().stream()
				.map(x-> ScheQualifySetDto.fromDomain(x))
				.collect(Collectors.toList());
    	
		return new ScheDispControlDto(
				domain.getCompanyId(),
				domain.getPersonSyQualify().v(),
				domain.getPubHolidayShortageAtr().value,
				domain.getSymbolAtr().value,
				domain.getPubHolidayExcessAtr().value,
				domain.getPubHolidayShortageAtr().value,
				domain.getSymbolHalfDayName(),
				schePerInfoAtr,
				scheQualifySet
		);
	}
}
