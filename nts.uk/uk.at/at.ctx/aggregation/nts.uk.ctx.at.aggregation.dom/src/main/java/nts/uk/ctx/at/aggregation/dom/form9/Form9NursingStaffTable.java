package nts.uk.ctx.at.aggregation.dom.form9;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
@Getter
@AllArgsConstructor
public class Form9NursingStaffTable implements DomainValue{
	
	/** 氏名 **/
	private final OutputColumn fullName;
	
	/** 1日目開始列 **/
	private final OutputColumn startColumn;
	
	/** 明細設定 **/
	private final DetailSettingOfForm9 detailSetting;
	
	/** 種別 **/
	private final Optional<OutputColumn> license;
	
	/** 病棟名 **/
	private Optional<OutputColumn> hospitalWardName;
	
	/** 常勤 **/
	private Optional<OutputColumn> fullTime;
	
	/** 短時間 **/
	private Optional<OutputColumn> shortTime;
	
	/** 非常勤 **/
	private Optional<OutputColumn> partTime;
	
	/** 他部署兼務 **/
	private Optional<OutputColumn> concurrentPost;
	
	/** 夜勤専従 **/
	private Optional<OutputColumn> nightShiftOnly;
	
	/**
	 * 作る
	 * @param fullName 氏名
	 * @param startColumn 1日目開始列
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
	public static Form9NursingStaffTable create(
				OutputColumn fullName, OutputColumn startColumn
			,	DetailSettingOfForm9 detailSetting, Optional<OutputColumn> license
			,	Optional<OutputColumn> hospitalWardName, Optional<OutputColumn> fullTime
			,	Optional<OutputColumn> shortTime, Optional<OutputColumn> partTime
			,	Optional<OutputColumn> concurrentPost
			,	Optional<OutputColumn> nightShiftOnly
				){
		
		val items = Arrays.asList(
					Optional.of(fullName), Optional.of(startColumn)
				,	license, hospitalWardName, fullTime, shortTime, partTime
				,	concurrentPost, nightShiftOnly)
				.stream()
				.flatMap(OptionalUtil::stream)
				.collect(Collectors.toList());
		
		val itemsDistinct = items.stream()
				.distinct()
				.collect(Collectors.toList());
		
		if(items.size() != itemsDistinct.size()) {
			throw new BusinessException("Msg_2244");
		}
		
		return new Form9NursingStaffTable(fullName, startColumn
				,	detailSetting, license
				,	hospitalWardName, fullTime, shortTime, partTime
				,	concurrentPost, nightShiftOnly);
	}
}
