package nts.uk.ctx.at.function.dom.scheduletable;

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
	private OutputSettingCode code;
	
	/**
	 * 	名称
	 */
	private final OutputSettingName name;
	
	/**
	 * 出力項目
	 */
	private final OutputItem outputItem;
	
	/**
	 * 職場計カテゴリ一覧
	 */
	//private final List<WorkplaceCounterCategory> workplaceCounterCategories;
	
	/**
	 * 個人計カテゴリ一覧
	 */
	//private final List<PersonalCounterCategory> personalCounterCategories;
	
	public static ScheduleTableOutputSetting create(
			OutputSettingCode code,
			OutputSettingName name,
			OutputItem outputItem
			// List<WorkplaceCounterCategory> workplaceCounterCategories
			//List<PersonalCounterCategory> personalCounterCategories
			) {
		
		// check
		
		return new ScheduleTableOutputSetting(code, name, outputItem);
		
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
			
		return new ScheduleTableOutputSetting(newCode, newName, this.outputItem);
		// TODO change return statement
	}

}
