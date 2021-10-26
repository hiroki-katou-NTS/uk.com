package nts.uk.ctx.at.aggregation.dom.form9;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.gul.util.OptionalUtil;
/**
 * 様式９の看護職員表
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９の看護職員表
 * @author lan_lt
 *
 */
@Value
public class Form9NursingTable implements DomainValue{
	
	/** 氏名 **/
	private final OutputColumn fullName;
	
	/** 1日目開始列 **/
	private final OutputColumn day1StartColumn;
	
	/** 明細設定 **/
	private final DetailSettingOfForm9 detailSetting;
	
	/** 種別 **/
	private final Optional<OutputColumn> license;
	
	/** 病棟名 **/
	private final Optional<OutputColumn> hospitalWardName;
	
	/** 常勤 **/
	private final Optional<OutputColumn> fullTime;
	
	/** 短時間 **/
	private final Optional<OutputColumn> shortTime;
	
	/** 非常勤 **/
	private final Optional<OutputColumn> partTime;
	
	/** 他部署兼務 **/
	private final Optional<OutputColumn> concurrentPost;
	
	/** 夜勤専従 **/
	private final Optional<OutputColumn> nightShiftOnly;
	
	/**
	 * 作る
	 * @param fullName 氏名
	 * @param day1StartColumn 1日目開始列
	 * @param detailSetting 明細設定
	 * @param license 種別
	 * @param hospitalWardName 病棟名
	 * @param fullTime 常勤
	 * @param shortTime 短時間
	 * @param partTime 非常勤
	 * @param concurrentPost 他部署兼務
	 * @param nightShiftOnly 夜勤専従
	 * @return
	 */
	public static Form9NursingTable create(
				OutputColumn fullName, OutputColumn day1StartColumn
			,	DetailSettingOfForm9 detailSetting, Optional<OutputColumn> license
			,	Optional<OutputColumn> hospitalWardName, Optional<OutputColumn> fullTime
			,	Optional<OutputColumn> shortTime, Optional<OutputColumn> partTime
			,	Optional<OutputColumn> concurrentPost
			,	Optional<OutputColumn> nightShiftOnly
				){
		
		val columns = Arrays.asList(
					Optional.of(fullName), Optional.of(day1StartColumn)
				,	license, hospitalWardName, fullTime, shortTime, partTime
				,	concurrentPost, nightShiftOnly)
				.stream()
				.flatMap(OptionalUtil::stream)
				.collect(Collectors.toList());
		
		val columnsDistinct = columns.stream()
				.distinct()
				.collect(Collectors.toList());
		
		if(columns.size() != columnsDistinct.size()) {
			throw new BusinessException("Msg_2244");
		}
		
		return new Form9NursingTable(fullName, day1StartColumn
				,	detailSetting, license
				,	hospitalWardName, fullTime, shortTime, partTime
				,	concurrentPost, nightShiftOnly);
	}
}
