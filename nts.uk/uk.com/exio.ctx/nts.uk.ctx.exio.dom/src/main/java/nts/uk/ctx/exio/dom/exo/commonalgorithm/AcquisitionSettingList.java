package nts.uk.ctx.exio.dom.exo.commonalgorithm;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.condset.CondSet;
import nts.uk.ctx.exio.dom.exo.condset.StandardAtr;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;

/**
 * 外部出力取得設定一覧
 * 
 * @author tam.nx
 *
 */
@Stateless
public class AcquisitionSettingList {

	@Inject
	private StdOutputCondSetRepository stdOutputCondSetRepository;

	/**
	 * 外部出力取得設定一覧
	 * 
	 * @param cId
	 *            会社ID
	 * @param userId
	 *            ユーザID
	 * @param conditionSettingCode
	 *            条件設定コード
	 * @return 条件設定（定型/ユーザ）
	 */
	public List<CondSet> getAcquisitionSettingList(String cId, String userId, StandardAtr stdType,
			Optional<String> conditionSettingCode) {
		if (StandardAtr.STANDARD.equals(stdType)) {
			return this.stdOutputCondSetRepository.getStdOutputCondSetById(cId, conditionSettingCode).stream()
					.map(item -> {
						return CondSet.fromStdOutputCondSet(item);
					}).collect(Collectors.toList());
		}
		// Pending
		/*if (StandardAttr.USER.equals(stdType)) {
			return Collections.emptyList();
		}*/
		return Collections.emptyList();
	}
}
