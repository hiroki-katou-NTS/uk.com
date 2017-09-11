/**
 * 5:42:15 PM Aug 23, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.List;

import lombok.Data;

/**
 * @author hungnm
 *
 */
@Data
public class DPControlDisplayItem {
	// sheet dtos
	private List<DPSheetDto> lstSheet;
	// header dtos
	private List<DPHeaderDto> lstHeader;
	
	public void addColumnsToSheet(List<FormatDPCorrectionDto> lstFormat){
		lstFormat.forEach(f->{
			this.lstSheet.forEach(s -> {
				if(f.getSheetNo().equals(s.getName())){
					s.addColumn(String.valueOf(f.getAttendanceItemId()));
				}
			});
		});
	}
}
