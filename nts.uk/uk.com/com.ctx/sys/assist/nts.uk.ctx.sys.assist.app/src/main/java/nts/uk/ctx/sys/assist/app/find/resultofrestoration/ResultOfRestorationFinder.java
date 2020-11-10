package nts.uk.ctx.sys.assist.app.find.resultofrestoration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.app.find.params.LogDataParams;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResultRepository;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ResultOfRestorationFinder {

	@Inject
	private DataRecoveryResultRepository finder;

	@Inject
	private TableListRepository tableListRepository;

	// step データ復旧の結果を取得
	public List<ResultOfRestorationDto> getResultOfRestoration(LogDataParams logDataParams) {

		// step ドメインモデル「データ復旧の結果」を取得
		List<ResultOfRestorationDto> resultOfRestorations = finder.getResultOfRestoration(
				AppContexts.user().companyId(), 
				logDataParams.getStartDateOperator(),
				logDataParams.getEndDateOperator(),
				logDataParams.getListOperatorEmployeeId()).stream()
						.map(item -> ResultOfRestorationDto.fromDomain(item))
						.collect(Collectors.toList());

		// step 空欄Output List<データ復旧の結果>を作成する。
		List<ResultOfRestorationDto> listResultOfRestoration = new ArrayList<ResultOfRestorationDto>();
		for (ResultOfRestorationDto resultOfRestoration : resultOfRestorations) {

			// step オブジェクト「テーブル一覧」を取得する。
			List<TableList> tableList = tableListRepository.getBySystemTypeAndRecoverId(logDataParams.getSystemType(),
					resultOfRestoration.getDataRecoveryProcessId());

			// step 取得したList<テーブル一覧>をチェックする。
			if (!tableList.isEmpty()) {
				// step 空欄Output List<データ復旧の結果>にループ中の「データ復旧の結果」を追加する。
				listResultOfRestoration.add(resultOfRestoration);
			}
		}

		// step List<データ復旧の結果>を返す。
		return listResultOfRestoration;
	}
}
