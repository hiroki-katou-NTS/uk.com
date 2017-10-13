/**
 * 5:42:15 PM Aug 23, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

	public DPControlDisplayItem() {
		super();
		this.lstSheet = new ArrayList<>();
		this.lstHeader = new ArrayList<>();
	}
	
	private boolean isExistSheet(DPSheetDto sheet){
		for(DPSheetDto exstSheet : this.lstSheet){
			if(exstSheet.getName().equals(sheet.getName())){
				return true;
			}
		}
		return false;
	}

	public void createSheets(List<DPSheetDto> lstSheet) {
		for (DPSheetDto sheet : lstSheet) {
			if(!isExistSheet(sheet)){
				this.lstSheet.add(sheet);
			}
		}
	}

	public void addColumnsToSheet(List<FormatDPCorrectionDto> lstFormat) {
		lstFormat.forEach(f -> {
			this.lstSheet.forEach(s -> {
				if (f.getSheetNo().equals(s.getName())) {
					s.addColumn(String.valueOf(f.getAttendanceItemId()));
				}
			});
		});
	}

	public void setHeaderText(List<DPAttendanceItem> lstAttendanceItem) {
		lstAttendanceItem.stream().forEach(i -> {
			Optional<DPHeaderDto> header = this.getLstHeader().stream()
					.filter(h -> h.getKey().equals(String.valueOf(i.getId()))).findFirst();
			if (header.isPresent()) {
				header.get().setHeaderText(i);
			}
		});
	}

	public void setHeaderColor(List<DPAttendanceItemControl> lstAttendanceItemControl) {
		lstAttendanceItemControl.stream().forEach(i -> {
			Optional<DPHeaderDto> header = this.getLstHeader().stream()
					.filter(h -> h.getKey().equals(String.valueOf(i.getAttendanceItemId()))).findFirst();
			if (header.isPresent()) {
				header.get().setHeaderColor(i);
			}
		});
	}

	public void setColumnsAccessModifier(List<DPBusinessTypeControl> lstDPBusinessTypeControl) {
		lstDPBusinessTypeControl.stream().forEach(i -> {
			Optional<DPHeaderDto> header = this.getLstHeader().stream()
					.filter(h -> h.getKey().equals(String.valueOf(i.getAttendanceItemId()))).findFirst();
			if (header.isPresent()) {
				header.get().setChangedByOther(i.isChangedByOther());
				header.get().setChangedByYou(i.isChangedByYou());
			}
		});
	}

}
