package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class DisplayItem {

	private Set<String> formatCode;

	private boolean settingUnit;
	
	private List<FormatMPCorrectionDto> lstFormat;
	
	private List<MPSheetDto> lstSheet;
	
	private List<Integer> lstAtdItemUnique; 
	
	private List<MPBusinessTypeControl> lstBusinessTypeCode;
	
	private int bussiness;
	
	public DisplayItem(){
		this.formatCode = new HashSet<>();
		this.lstFormat = new ArrayList<>();
		this.lstSheet = new ArrayList<>();
		this.lstAtdItemUnique = new ArrayList<>();
		this.lstBusinessTypeCode = new ArrayList<>();
	}
}
