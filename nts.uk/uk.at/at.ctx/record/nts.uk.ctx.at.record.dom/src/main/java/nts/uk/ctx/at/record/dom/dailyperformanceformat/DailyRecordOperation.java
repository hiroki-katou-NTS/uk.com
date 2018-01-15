package nts.uk.ctx.at.record.dom.dailyperformanceformat;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.enums.SettingUnit;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class DailyRecordOperation extends AggregateRoot {
	
	private String companyId;
	
	private SettingUnit settingUnit;

	public DailyRecordOperation(String companyId, SettingUnit settingUnit) {
		super();
		this.companyId = companyId;
		this.settingUnit = settingUnit;
	}
	
	public static DailyRecordOperation createFromJavaType(String companyId, int settingUnit){
		return new DailyRecordOperation(companyId, EnumAdaptor.valueOf(settingUnit, SettingUnit.class));
	}

}
