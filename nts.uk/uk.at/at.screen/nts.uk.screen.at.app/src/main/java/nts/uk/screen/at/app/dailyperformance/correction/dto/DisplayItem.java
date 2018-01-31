package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.ArrayList;
import java.util.HashSet;
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
	
	public DisplayItem(){
		this.formatCode = new HashSet<>();
		this.lstFormat = new ArrayList<>();
		this.lstSheet = new ArrayList<>();
		this.lstAtdItemUnique = new ArrayList<>();
		this.lstBusinessTypeCode = new ArrayList<>();
	}
}
