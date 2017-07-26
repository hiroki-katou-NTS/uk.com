package nts.uk.ctx.basic.infra.repository.system.era;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;

import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListHeaderColumns;

@Stateless
@Named(value = "test2") 
public class TestMasterListColumnImpl2 implements MasterListHeaderColumns{

	@Override
	public List<MasterHeaderColumn> getHeaderColumns() {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		
		int i = 1;
		
		while (i <= 15) {
			columns.add(new MasterHeaderColumn("Column " + i, "", ColumnTextAlign.CENTER, "", true));
			i++;
		}
		
		return columns;
	}

}
