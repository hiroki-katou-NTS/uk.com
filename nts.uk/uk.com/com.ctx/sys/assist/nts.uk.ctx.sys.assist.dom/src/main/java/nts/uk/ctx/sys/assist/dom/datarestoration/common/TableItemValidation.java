package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;

@Stateless
public class TableItemValidation {
	
	@Inject
	private TableColumnNameNativeQueryRepository tableColumnRepository;
	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;
	// アルゴリズム「テーブル項目チェック」を実行する
	public ServerPrepareMng checkTableItem(ServerPrepareMng serverPrepareMng, List<TableList> tableList){
		if(compareItem(serverPrepareMng, tableList)){
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.TABLE_ITEM_DIFFERENCE);
			serverPrepareMngRepository.update(serverPrepareMng);
		}
		return serverPrepareMng;
	}
	// テーブル一覧のすべての行をアルゴリズム「項目の比較」でチェックする
	private boolean compareItem(ServerPrepareMng serverPrepareMng, List<TableList> tableList){
		String fileId = serverPrepareMng.getFileId().get();
		List<String> csvHeader = new ArrayList<>();
		List<String> tableColumn = new ArrayList<>();
		for(TableList tableListItem: tableList){
			csvHeader = getActualCsvHeader(fileId, tableListItem.getInternalFileName());
			if (csvHeader.isEmpty()) return true;
			tableColumn = tableColumnRepository.getTableColumnName(tableListItem.getTableEnglishName());
			if (tableColumn.isEmpty() || csvHeader.isEmpty() || !tableColumn.containsAll(csvHeader) || !csvHeader.containsAll(tableColumn)) return true; 
		}
		return false;
	}
	private List<String> getActualCsvHeader(String fileId, String fileName){
		List<String> csvHeader = CsvFileUtil.getCsvHeader(fileName, fileId);
		// Csv header have 5 new column, don't use them to validate
		if (csvHeader.size() >5){
			return csvHeader.subList(5, csvHeader.size());
		}
		return Collections.EMPTY_LIST;
	}
}
