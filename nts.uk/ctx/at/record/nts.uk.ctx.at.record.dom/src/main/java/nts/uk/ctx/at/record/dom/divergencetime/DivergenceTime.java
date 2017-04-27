package nts.uk.ctx.at.record.dom.divergencetime;
import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class DivergenceTime extends AggregateRoot{
	/*会社ID*/
	private String companyId;
	/*乖離時間ID*/
	private int divTimeId;
	/*乖離時間使用設定*/
	private DivergenceTimeUseSet divTimeUseSet;
	/*アラーム時間*/
	private Time alarmTime;
	/*エラー時間*/
	private Time errTime;
	/*選択使用設定*/	
	private InputSetting selectSet;
	/*入力使用設定*/
	private InputSetting inputSet;

	public DivergenceTime(String companyId, 
						int divTimeId, 
						DivergenceTimeUseSet divTimeUseSet, 
						Time alarmTime,
						Time errTime, 
						InputSetting selectSet, 
						InputSetting inputSet) {
		super();
		this.companyId = companyId;
		this.divTimeId = divTimeId;
		this.divTimeUseSet = divTimeUseSet;
		this.alarmTime = alarmTime;
		this.errTime = errTime;
		this.selectSet = selectSet;
		this.inputSet = inputSet;
	}
	
	public static DivergenceTime createSimpleFromJavaType(String companyId,
														int divTimeId,
														int divTimeUseSet,
														int alarmTime,
														int errTime,
														int selectUseSet,
														int cancelErrSelect,
														int inputUseSet,
														int cancelErrInput){
		return new DivergenceTime(
				companyId, 
				divTimeId, 
				EnumAdaptor.valueOf(divTimeUseSet, DivergenceTimeUseSet.class),
				new Time(BigDecimal.valueOf(alarmTime)), 
				new Time(BigDecimal.valueOf(errTime)), 
				InputSetting.convert(selectUseSet,cancelErrSelect), 
				InputSetting.convert(inputUseSet,cancelErrInput));
				
	}
	
}
