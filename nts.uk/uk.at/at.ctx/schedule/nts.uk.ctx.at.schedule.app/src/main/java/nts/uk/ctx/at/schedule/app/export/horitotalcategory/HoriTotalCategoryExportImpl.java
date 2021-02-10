package nts.uk.ctx.at.schedule.app.export.horitotalcategory;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

/**
 * 
 * @author Hoidd
 *
 */
@Stateless
@DomainID(value = "Schedule")
public class HoriTotalCategoryExportImpl implements MasterListData {

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KML004_57"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KML004_58"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("メモ", TextResource.localize("KML004_59"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("選択された対象項目", TextResource.localize("KML004_60"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("回数集計集計設定", TextResource.localize("KML004_53"),
				ColumnTextAlign.LEFT, "", true));
 
		return columns;
	}

	
	@Override
	public String mainSheetName() {
		return TextResource.localize("KML004_56");
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}
	
	
	
}
