package nts.uk.ctx.at.function.dom.scheduledailytable;

import java.util.List;

import lombok.Value;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 勤務計画実施表の項目設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.勤務計画実施表.勤務計画実施表の項目設定
 * @author dan_pv
 *
 */
@Value
public class ScheduleDailyTableItemSetting {

	private ScheduleDailyTableInkanRow inkanRow;
	
	private List<Integer> personalCounter;
	
	private List<Integer> workplaceCounter;
	
	private NotUseAtr transferDisplay;
	
	private SupporterPrintMethod supporterSchedulePrintMethod;
	
	private SupporterPrintMethod supporterDailyDataPrintMethod;
}
