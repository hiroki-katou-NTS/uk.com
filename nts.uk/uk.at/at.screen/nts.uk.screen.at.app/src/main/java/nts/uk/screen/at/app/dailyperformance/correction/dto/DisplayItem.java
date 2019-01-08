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
	
	private List<DPBusinessTypeControl> lstBusinessTypeCode;
	
	private int bussiness;
	
	private Set<String> autBussCode;
	
	private List<DCMessageError> errors;
	
	public DisplayItem(){
		this.formatCode = new HashSet<>();
		this.lstFormat = new ArrayList<>();
		this.lstSheet = new ArrayList<>();
		this.lstAtdItemUnique = new ArrayList<>();
		this.lstBusinessTypeCode = new ArrayList<>();
		this.autBussCode = new HashSet<>();
		this.errors = new ArrayList<>();
	}
}
