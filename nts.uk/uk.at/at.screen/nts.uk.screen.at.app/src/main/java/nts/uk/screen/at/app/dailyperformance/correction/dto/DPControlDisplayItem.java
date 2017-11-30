/**
 * 5:42:15 PM Aug 23, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;

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
	
	private List<DPAttendanceItem> lstAttendanceItem;
	
	private Set<String> formatCode;

	public DPControlDisplayItem() {
		super();
		this.lstSheet = new ArrayList<>();
		this.lstHeader = new ArrayList<>();
	}
	
	private boolean isExistHeader(DPHeaderDto header) {
		for(DPHeaderDto exstHeader : this.lstHeader){
			if(exstHeader.getKey().equals(header.getKey())){
				return true;
			}
		}
		return false;
	}
	
	private boolean isExistSheet(DPSheetDto sheet){
		for(DPSheetDto exstSheet : this.lstSheet){
			if(exstSheet.getName().equals(sheet.getName())){
				return true;
			}
		}
		return false;
	}
	
	public void setLstHeader(List<DPHeaderDto> lstHeader) {
		for(DPHeaderDto header : lstHeader){
			if(!isExistHeader(header)){
				this.lstHeader.add(header);
			}
		}
	}

	public void createSheets(List<DPSheetDto> lstSheet) {
		for (DPSheetDto sheet : lstSheet) {
			if(!isExistSheet(sheet)){
				this.lstSheet.add(sheet);
			}
		}
	}

	public void addColumnsToSheet(List<FormatDPCorrectionDto> lstFormat, Map<Integer,DPAttendanceItem>  mapDP) {
		lstFormat.forEach(f -> {
			this.lstSheet.forEach(s -> {
				if (f.getSheetNo().equals(s.getName()) && !s.isExistColumn(String.valueOf(f.getAttendanceItemId()))) {
					int attendanceAtr = mapDP.get(f.getAttendanceItemId()).getAttendanceAtr() ;
					if(attendanceAtr == DailyAttendanceAtr.Code.value || attendanceAtr == DailyAttendanceAtr.Classification.value ){
						s.addColumn("Code"+f.getAttendanceItemId());
						s.addColumn("Name"+f.getAttendanceItemId());
					}else{
						s.addColumn("_"+String.valueOf(f.getAttendanceItemId()));
					}
				}
			});
		});
	}
	
	public void setHeaderText(List<DPAttendanceItem> lstAttendanceItem) {
		lstAttendanceItem.stream().forEach(i -> {
			Optional<DPHeaderDto> header = this.getLstHeader().stream()
					.filter(h -> h.getKey().equals("_"+String.valueOf(i.getId()))).findFirst();
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
				if(!header.get().getGroup().isEmpty()){
					header.get().getGroup().get(0).setHeaderColor(i);	
					header.get().getGroup().get(1).setHeaderColor(i);	
				}
			}
		});
	}

	public void setColumnsAccessModifier(List<DPBusinessTypeControl> lstDPBusinessTypeControl) {
		lstDPBusinessTypeControl.stream().forEach(i -> {
			Optional<DPHeaderDto> header = this.getLstHeader().stream()
					.filter(h -> h.getKey().substring(1, h.getKey().length()).equals(String.valueOf(i.getAttendanceItemId()))).findFirst();
			if (header.isPresent()) {
				header.get().setChangedByOther(i.isChangedByOther());
				header.get().setChangedByYou(i.isChangedByYou());
			}
		});
	}

}
