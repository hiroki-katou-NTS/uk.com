package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.system.ServerSystemProperties;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;

@Stateless
public class TableListRestorationService {
	private static final String FILE_NAME_CSV1 = "保存対象テーブル一覧";
	private static final String FILE_NAME_CSV2 = "対象社員";
	private static final String dateFormat = "yy/mm/dd";
			
	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;
	
	@Inject
	private FileUtil fileUtil;
	
	@Inject
	private TableListRepository tableListRepository;
	//アルゴリズム「テーブル一覧の復元」を実行する
	public TableList restoreTableList(ServerPrepareMng serverPrepareMng){
		TableList tableList = null;
		List<List<String>> tableListContent = fileUtil.getAllRecord(serverPrepareMng.getFileId().get(), serverPrepareMng.getUploadFileName().get(), 3); 
		if (!tableListContent.isEmpty()){
		List<String> tableListSetting = tableListContent.get(1);
		// 
//		tableList = new TableList(tableListSetting.get(0), tableListSetting.get(1), tableListSetting.get(2), tableListSetting.get(3), 
//				Integer.parseInt(tableListSetting.get(4)), tableListSetting.get(5), tableListSetting.get(6), tableListSetting.get(7),
//				tableListSetting.get(8), tableListSetting.get(9), tableListSetting.get(10), tableListSetting.get(11), 
//				tableListSetting.get(12), tableListSetting.get(13), tableListSetting.get(14), tableListSetting.get(15),  
//				GeneralDate.fromString(tableListSetting.get(16), dateFormat), GeneralDate.fromString(tableListSetting.get(17), dateFormat), tableListSetting.get(18), Integer.parseInt(tableListSetting.get(19)),  
//				tableListSetting.get(20), Integer.parseInt(tableListSetting.get(21)), tableListSetting.get(22), tableListSetting.get(23), 
//				tableListSetting.get(24), tableListSetting.get(25), tableListSetting.get(26), tableListSetting.get(27), 
//				tableListSetting.get(28), tableListSetting.get(29), tableListSetting.get(30), tableListSetting.get(31),
//				tableListSetting.get(32), tableListSetting.get(33), tableListSetting.get(34), Integer.parseInt(tableListSetting.get(35)), 
//				tableListSetting.get(36), tableListSetting.get(37), tableListSetting.get(38), tableListSetting.get(39), 
//				tableListSetting.get(40), tableListSetting.get(41), tableListSetting.get(42), tableListSetting.get(43), 
//				tableListSetting.get(44), tableListSetting.get(45), tableListSetting.get(46), tableListSetting.get(47), 
//				tableListSetting.get(48), tableListSetting.get(50), tableListSetting.get(51), 
//				tableListSetting.get(52), tableListSetting.get(53), tableListSetting.get(54), tableListSetting.get(55), 
//				tableListSetting.get(56), tableListSetting.get(57), tableListSetting.get(58), tableListSetting.get(59),
//				tableListSetting.get(60), tableListSetting.get(61), tableListSetting.get(62), tableListSetting.get(63),
//				tableListSetting.get(64), tableListSetting.get(65), tableListSetting.get(66), tableListSetting.get(67),
//				tableListSetting.get(68), tableListSetting.get(69), tableListSetting.get(70), tableListSetting.get(71),
//				tableListSetting.get(72), tableListSetting.get(73), tableListSetting.get(74), tableListSetting.get(75),
//				tableListSetting.get(76), tableListSetting.get(77), tableListSetting.get(78), tableListSetting.get(79),
//				tableListSetting.get(80), tableListSetting.get(81), tableListSetting.get(82), tableListSetting.get(83),
//				tableListSetting.get(84), tableListSetting.get(85), tableListSetting.get(86), tableListSetting.get(87),
//				tableListSetting.get(88), tableListSetting.get(89), tableListSetting.get(90), tableListSetting.get(91),
//				tableListSetting.get(92), tableListSetting.get(93), tableListSetting.get(94), tableListSetting.get(95),
//				tableListSetting.get(96), tableListSetting.get(97), tableListSetting.get(98), tableListSetting.get(99),
//				tableListSetting.get(100), tableListSetting.get(101), tableListSetting.get(102), Integer.parseInt(tableListSetting.get(116)), tableListSetting.get(117), tableListSetting.get(118), tableListSetting.get(119),
//				tableListSetting.get(120), tableListSetting.get(121), tableListSetting.get(122), tableListSetting.get(123),
//				tableListSetting.get(124), tableListSetting.get(125), tableListSetting.get(126), tableListSetting.get(127),
//				Integer.parseInt(tableListSetting.get(128)));
//		tableListRepository.add(tableList);
		} else {
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.TABLE_LIST_FAULT);
			serverPrepareMngRepository.add(serverPrepareMng);
		}
		return tableList;
	}
}
