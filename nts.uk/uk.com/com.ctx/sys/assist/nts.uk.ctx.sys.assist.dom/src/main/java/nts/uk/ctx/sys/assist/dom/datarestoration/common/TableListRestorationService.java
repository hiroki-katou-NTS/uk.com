package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.assist.dom.category.RecoverFormCompanyOther;
import nts.uk.ctx.sys.assist.dom.category.StorageRangeSaved;
import nts.uk.ctx.sys.assist.dom.category.TimeStore;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.HistoryDiviSion;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class TableListRestorationService {
	private static final String TABLELIST_CSV = "保存対象テーブル一覧";
	private static final String dateFormat = "yy/mm/dd";

	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;

	// アルゴリズム「テーブル一覧の復元」を実行する
	public List<Object> restoreTableList(ServerPrepareMng serverPrepareMng) {
		List<TableList> tableList = new ArrayList<>();
		List<List<String>> tableListContent = FileUtil.getAllRecord(serverPrepareMng.getFileId().get(),
				TABLELIST_CSV, 3);
		if (tableListContent.size() < 2) {
			try {
				for (List<String> tableListSetting : tableListContent) {
					TimeStore retentionPeriodCls = EnumAdaptor.valueOf(Integer.parseInt(tableListSetting.get(7)),
							TimeStore.class);
					StorageRangeSaved storageRangeSaved = EnumAdaptor.valueOf(Integer.parseInt(tableListSetting.get(8)),
							StorageRangeSaved.class);
					NotUseAtr surveyPreservation = EnumAdaptor.valueOf(Integer.parseInt(tableListSetting.get(12)),
							NotUseAtr.class);
					RecoverFormCompanyOther anotherComCls = EnumAdaptor
							.valueOf(Integer.parseInt(tableListSetting.get(13)), RecoverFormCompanyOther.class);
					HistoryDiviSion historyCls = EnumAdaptor.valueOf(Integer.parseInt(tableListSetting.get(17)),
							HistoryDiviSion.class);
					NotUseAtr hasParentTblFlg = EnumAdaptor.valueOf(Integer.parseInt(tableListSetting.get(18)),
							NotUseAtr.class);
					GeneralDate saveDateFrom = GeneralDate.fromString(tableListSetting.get(108), dateFormat);
					GeneralDate saveDateTo = GeneralDate.fromString(tableListSetting.get(109), dateFormat);
					TableList tableListData = new TableList(tableListSetting.get(0), tableListSetting.get(1),
							tableListSetting.get(2), tableListSetting.get(3), tableListSetting.get(4),
							tableListSetting.get(5), tableListSetting.get(6), retentionPeriodCls,
							storageRangeSaved, tableListSetting.get(9), tableListSetting.get(10),
							tableListSetting.get(11), surveyPreservation, anotherComCls,
							Integer.parseInt(tableListSetting.get(14)), tableListSetting.get(15),
							tableListSetting.get(16), historyCls, hasParentTblFlg, tableListSetting.get(19),
							tableListSetting.get(20), tableListSetting.get(21), tableListSetting.get(22),
							tableListSetting.get(23), tableListSetting.get(24), tableListSetting.get(25),
							tableListSetting.get(26), tableListSetting.get(27), tableListSetting.get(28),
							tableListSetting.get(29), tableListSetting.get(30), tableListSetting.get(31),
							tableListSetting.get(32), tableListSetting.get(33), tableListSetting.get(34),
							tableListSetting.get(35), tableListSetting.get(36), tableListSetting.get(37),
							tableListSetting.get(38), tableListSetting.get(39), tableListSetting.get(40),
							tableListSetting.get(41), tableListSetting.get(42), tableListSetting.get(43),
							tableListSetting.get(44), tableListSetting.get(45), tableListSetting.get(46),
							tableListSetting.get(47), tableListSetting.get(48), tableListSetting.get(49),
							tableListSetting.get(50), tableListSetting.get(51), tableListSetting.get(52),
							tableListSetting.get(53), tableListSetting.get(54), tableListSetting.get(55),
							tableListSetting.get(56), tableListSetting.get(57), tableListSetting.get(58),
							tableListSetting.get(59), tableListSetting.get(60), tableListSetting.get(61),
							tableListSetting.get(62), tableListSetting.get(63), tableListSetting.get(64),
							tableListSetting.get(65), tableListSetting.get(66), tableListSetting.get(67),
							tableListSetting.get(68), tableListSetting.get(69), tableListSetting.get(70),
							tableListSetting.get(71), tableListSetting.get(72), tableListSetting.get(73),
							tableListSetting.get(74), tableListSetting.get(75), tableListSetting.get(76),
							tableListSetting.get(77), tableListSetting.get(78), tableListSetting.get(79),
							tableListSetting.get(80), tableListSetting.get(81), tableListSetting.get(82),
							tableListSetting.get(83), tableListSetting.get(84), tableListSetting.get(85),
							tableListSetting.get(86), tableListSetting.get(87), tableListSetting.get(88),
							tableListSetting.get(89), tableListSetting.get(90), tableListSetting.get(91),
							tableListSetting.get(92), tableListSetting.get(93), tableListSetting.get(94),
							tableListSetting.get(95), tableListSetting.get(96), tableListSetting.get(97),
							tableListSetting.get(98), tableListSetting.get(99), tableListSetting.get(100),
							tableListSetting.get(101), tableListSetting.get(102), tableListSetting.get(103),
							tableListSetting.get(104), tableListSetting.get(105), tableListSetting.get(106),
							saveDateFrom, saveDateTo, tableListSetting.get(109), tableListSetting.get(110),
							tableListSetting.get(111), Integer.parseInt(tableListSetting.get(112)), Integer.parseInt(tableListSetting.get(113)));
				}
			} catch (NumberFormatException e) {
				serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.TABLE_LIST_FAULT);
				serverPrepareMngRepository.update(serverPrepareMng);
			}
		} else {
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.TABLE_LIST_FAULT);
			serverPrepareMngRepository.update(serverPrepareMng);
		}
		return Arrays.asList(serverPrepareMng, tableList);
	}
}
