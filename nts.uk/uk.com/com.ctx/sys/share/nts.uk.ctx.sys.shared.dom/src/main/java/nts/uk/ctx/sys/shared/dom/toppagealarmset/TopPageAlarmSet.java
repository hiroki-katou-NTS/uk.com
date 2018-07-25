package nts.uk.ctx.sys.shared.dom.toppagealarmset;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * トップページアラーム設定
 * @author yennth
 *
 */
@Getter
public class TopPageAlarmSet extends AggregateRoot{
	/** 会社ID */
	private String companyId;
	/** アラームカテゴリ */
	private AlarmCategory alarmCategory;
	/** 利用区分 */
	private UseAtr useAtr;
	
	public TopPageAlarmSet(String companyId, AlarmCategory alarmCategory, UseAtr useAtr) {
		super();
		this.companyId = companyId;
		this.alarmCategory = alarmCategory;
		this.useAtr = useAtr;
	} 
	
	public static TopPageAlarmSet createFromJavaType(String companyId, int alarmCategory, int useAtr){
		return new TopPageAlarmSet(companyId, 
				EnumAdaptor.valueOf(alarmCategory, AlarmCategory.class), EnumAdaptor.valueOf(useAtr, UseAtr.class));
	}
}
