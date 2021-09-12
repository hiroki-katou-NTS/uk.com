package nts.uk.file.com.app.equipment.data;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.office.dom.equipment.data.EquipmentData;

@Data
@Builder
public class EquipmentDataExportDataSource {
	/**
	 * 設備利用実績データ<List>
	 */
	private List<EquipmentData> equipmentDatas;
	
	/**
	 * 会社情報
	 */
	private Company companyInfo;
	
	//TODO
}

