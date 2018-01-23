package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class DisplayItem {

	private Set<String> formatCode;

	private boolean settingUnit;
	
	private List<FormatDPCorrectionDto> lstFormat;
	
	private List<DPSheetDto> lstSheet;
	
	private List<Integer> lstAtdItemUnique; 
	
	private List<String> lstBusinessTypeCode;
	
	private int bussiness;
}
