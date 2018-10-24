package nts.uk.ctx.at.schedule.dom.adapter;

import java.util.List;

public interface ScTimeAdapter {
	/**
	 * Request List #91
	 * @param param
	 * @return
	 */
	ScTimeImport calculation(Object companySetting, ScTimeParam param);
	
	List<ScTimeImport> calculation(Object companySetting, List<ScTimeParam> params);
	
	Object getCompanySettingForCalculation();
}
