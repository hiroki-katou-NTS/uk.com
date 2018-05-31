package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatDto;

@Data
public class DisplayItem {

	private Set<String> formatCode;

	private boolean settingUnit;
	
	private List<FormatMPCorrectionDto> lstFormat;
	
	private List<MPSheetDto> lstSheet;
	
	private List<Integer> lstAtdItemUnique; 
	
	private List<MPBusinessTypeControl> lstBusinessTypeCode;
	
	private int bussiness;
	
	/** 表示する項目一覧 */
	private List<MonPfmCorrectionFormatDto> lstMPformats;
	
	public DisplayItem(){
		this.formatCode = new HashSet<>();
		this.lstFormat = new ArrayList<>();
		this.lstSheet = new ArrayList<>();
		this.lstAtdItemUnique = new ArrayList<>();
		this.lstBusinessTypeCode = new ArrayList<>();
	}
}
