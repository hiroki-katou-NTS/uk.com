/**
 * 6:00:49 PM Aug 28, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.List;

import nts.uk.screen.at.app.dailyperformance.correction.dto.cache.DPCorrectionStateParam;

/**
 * @author hungnm
 *
 */
public class DPParams {
	public DateRange dateRange;
	public List<DailyPerformanceEmployeeDto> lstEmployee;
	public Integer displayFormat;
	public Integer initScreen;
	public Integer mode;
	public CorrectionOfDailyPerformance correctionOfDaily;
	// only use when click button A2_3
	public List<String> errorCodes;
	// only use when click button  A2_4
	public List<String> formatCodes;
	public Boolean showError;
    public ObjectShare objectShare;	
    public Boolean showLock;
    public Integer closureId;
    public Boolean initFromScreenOther;
    public DPCorrectionStateParam dpStateParam;
    public boolean changeFormat = false;

    public DailyPerformanceCorrectionDto screenDto;
}
