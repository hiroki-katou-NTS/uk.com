package nts.uk.ctx.at.function.dom.scheduletable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * スケジュール表の出力設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.スケジュール表.スケジュール表の出力設定
 * @author dan_pv
 *
 */
@AllArgsConstructor
@Getter
public class ScheduleTableOutputSetting implements DomainAggregate{
	
	/**
	 *  コード
	 */
	private final OutputSettingCode code;
	
	/**
	 * 	名称
	 */
	private OutputSettingName name;
	
	/**
	 * 出力項目
	 */
	private OutputItem outputItem;
	
	/**
	 * 職場計カテゴリ一覧
	 */
	private List<WorkplaceCounterCategory> workplaceCounterCategories;
	
	/**
	 * 個人計カテゴリ一覧
	 */
	private List<PersonalCounterCategory> personalCounterCategories;
	
	/**
	 * 作る
	 * @param code
	 * @param name
	 * @param outputItem
	 * @param workplaceCounterCategories
	 * @param personalCounterCategories
	 * @return
	 */
	public static ScheduleTableOutputSetting create(
			OutputSettingCode code,
			OutputSettingName name,
			OutputItem outputItem,
			List<WorkplaceCounterCategory> workplaceCounterCategories,
			List<PersonalCounterCategory> personalCounterCategories
			) {
		
		if ( workplaceCounterCategories.size() != new HashSet<>(workplaceCounterCategories).size()
				|| personalCounterCategories.size() != new HashSet<>(personalCounterCategories).size()) {
			throw new RuntimeException("List is duplicated");
		}
		
		return new ScheduleTableOutputSetting(code, name, outputItem, workplaceCounterCategories, personalCounterCategories);
		
	}
	
	/**
	 * 複製する
	 * @param newCode
	 * @param newName
	 * @return
	 */
	public ScheduleTableOutputSetting clone(
			OutputSettingCode newCode,
			OutputSettingName newName) {
		
		if ( this.code.v().equals(newCode.v())) throw new BusinessException("Msg_355");
		if ( this.name.v().equals(newName.v())) throw new BusinessException("Msg_705");
			
		
		return new ScheduleTableOutputSetting(
				newCode, 
				newName, 
				this.outputItem,
				new ArrayList<>(this.workplaceCounterCategories),
				new ArrayList<>(this.personalCounterCategories));
	}

}
