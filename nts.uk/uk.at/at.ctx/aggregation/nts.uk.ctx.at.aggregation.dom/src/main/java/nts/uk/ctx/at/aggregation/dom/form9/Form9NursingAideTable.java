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
 * 様式９の看護補助者表
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９の看護補助者表
 * @author lan_lt
 *
 */
@Value
public class Form9NursingAideTable implements DomainValue{
	
	/** 氏名 **/
	private final OutputColumn fullName;
	
	/** 1日目開始列 **/
	private final OutputColumn day1StartColumn;
	
	/** 明細設定 **/
	private final DetailSettingOfForm9 detailSetting;
	
	/** 病棟名 **/
	private Optional<OutputColumn> hospitalWardName;
	
	/** 常勤 **/
	private final Optional<OutputColumn> fullTime;
	
	/** 短時間 **/
	private final Optional<OutputColumn> shortTime;
	
	/** 非常勤 **/
	private final Optional<OutputColumn> partTime;
	
	/** 事務的業務従事者 **/
	private final Optional<OutputColumn> officeWork;
	
	/** 他部署兼務 **/
	private final Optional<OutputColumn> concurrentPost;
	
	/** 夜勤専従 **/
	private final Optional<OutputColumn> nightShiftOnly;
	
	/**
	 * 作る
	 * @param fullName 氏名
	 * @param day1StartColumn 1日目開始列
	 * @param detailSetting 明細設定
	 * @param hospitalWardName 病棟名
	 * @param fullTime 常勤
	 * @param shortTime 短時間
	 * @param partTime 非常勤
	 * @param officeWork 事務的業務従事者
	 * @param concurrentPost 他部署兼務
	 * @param nightShiftOnly 夜勤専従
	 * @return
	 */
	public static Form9NursingAideTable create(
				OutputColumn fullName, OutputColumn day1StartColumn
			,	DetailSettingOfForm9 detailSetting, Optional<OutputColumn> hospitalWardName
			,	Optional<OutputColumn> fullTime, Optional<OutputColumn> shortTime
			,	Optional<OutputColumn> partTime, Optional<OutputColumn> officeWork
			,	Optional<OutputColumn> concurrentPost
			,	Optional<OutputColumn> nightShiftOnly
				){
		
		val columns = Arrays.asList(
					Optional.of(fullName), Optional.of(day1StartColumn)
				,	hospitalWardName, fullTime, shortTime, partTime
				,	officeWork, concurrentPost, nightShiftOnly)
				.stream()
				.flatMap(OptionalUtil::stream)
				.collect(Collectors.toList());
		
		val columnsDistinct = columns.stream()
				.distinct()
				.collect(Collectors.toList());
		
		if(columns.size() != columnsDistinct.size()) {
			throw new BusinessException("Msg_2244");
		}
		
		return new Form9NursingAideTable(fullName, day1StartColumn, detailSetting
				,	hospitalWardName, fullTime, shortTime, partTime, officeWork
				,	concurrentPost, nightShiftOnly);
	}
}
