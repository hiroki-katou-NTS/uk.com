package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.app.command.datarestoration.FindDataHistoryDto;
import nts.uk.ctx.sys.assist.app.command.datarestoration.GetDataHistoryCommand;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResult;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResultRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSaving;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSavingRepository;
import nts.uk.ctx.sys.assist.dom.storage.SysEmployeeStorageAdapter;

/**
 * アルゴリズム「保存セットで検索する」
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DataHistoryFinder {

	@Inject
	private DataRecoveryResultRepository dataRecoveryResultRepository;

	@Inject
	private PerformDataRecoveryRepository performDataRecoveryRepository;

	@Inject
	private ResultOfSavingRepository resultOfSavingRepository;

	@Inject
	private SysEmployeeStorageAdapter sysEmployeeStorageAdapter;

	public List<DataHistoryDto> findData(GetDataHistoryCommand command) {
		List<FindDataHistoryDto> list = command.getObjects();
		List<String> saveSetCodes = list.stream().map(FindDataHistoryDto::getPatternCode).filter(Objects::nonNull)
				.collect(Collectors.toList());
		List<DataHistoryDto> res = new ArrayList<DataHistoryDto>();
		if (!saveSetCodes.isEmpty()) {
			res.addAll(findBySaveSetCodes(saveSetCodes, command.getFrom(), command.getTo()));
		}

		if (!res.isEmpty()) {
			Map<String, String> pool = new HashMap<String, String>();
			res.forEach(data -> {
				String sid = data.getPractitioner();
				String businessName = pool.get(sid);
				if (businessName == null) {
					businessName = sysEmployeeStorageAdapter.getByListSid(Collections.singletonList(sid)).get(0)
							.getBusinessname().v();
					pool.put(sid, businessName);
				}
				data.setPractitioner(sid + " " + businessName);
			});
		}
		/**
		 * 取得したデータを画面表示する
		 */
		return res.stream().sorted(Comparator.comparing(DataHistoryDto::getStartDatetime)).collect(Collectors.toList());
	}

	private List<DataHistoryDto> findBySaveSetCodes(List<String> saveSetCodes, GeneralDateTime from, GeneralDateTime to) {
		/**
		 * 起動する時取得したList<データ保存の結果＞から絞り込みする。
		 */
		List<ResultOfSaving> rosList = resultOfSavingRepository.getResultOfSavingBySaveSetCode(saveSetCodes);

		/**
		 * 起動する時取得したList<データ復旧の実行＞から絞り込みする。
		 */
		List<PerformDataRecovery> pdrList = performDataRecoveryRepository.getPerformDataRecoverByIds(
				rosList.stream().map(ResultOfSaving::getStoreProcessingId).collect(Collectors.toList()));

		/**
		 * 起動する時取得したList<データ復旧の結果＞から絞り込みする。
		 */
		List<DataRecoveryResult> drrList = dataRecoveryResultRepository.getDataRecoveryResultsByIds(pdrList.stream()
				.map(PerformDataRecovery::getDataRecoveryProcessId).collect(Collectors.toList()));
		return pdrList
				.stream().map(
						pdr -> DataHistoryDto
								.fromDomains(
										rosList.stream()
												.filter(ros -> ros.getStoreProcessingId()
														.equals(pdr.getSaveProcessId().get()))
												.findFirst().orElse(null),
										pdr,
										drrList.stream()
												.filter(data -> data.getStartDateTime().after(from) 
															&& data.getEndDateTime().isPresent() 
																	? data.getEndDateTime().get().before(to) 
																	: true)
												.filter(drr -> drr.getDataRecoveryProcessId()
														.equals(pdr.getDataRecoveryProcessId()))
												.findFirst().orElse(null)))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
}
