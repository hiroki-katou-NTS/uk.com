package nts.uk.ctx.at.function.app.find.condset;

import nts.uk.ctx.at.function.app.find.condset.dto.StdOutputCondSetDto;
import nts.uk.ctx.at.function.dom.adapter.condset.StdOutputCondSetAdapter;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Std output cond set finder.
 *
 * @author nws-minhnb
 */
@Stateless
public class StdOutputCondSetFinder {

	/**
	 * The Standard output condition setting adapter.
	 */
	@Inject
	private StdOutputCondSetAdapter adapter;

	/**
	 * Find all std output cond sets by login cid list.<br>
	 * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.B:実行設定.アルゴリズム.利用するマスタ情報を取得する.ドメインモデル「出力条件設定（定型）」を取得する
	 *
	 * @return the <code>StdOutputCondSetDto</code> list
	 */
	public List<StdOutputCondSetDto> findAllStdOutputCondSetsByLoginCid() {
		// ログイン社員の会社ID
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「出力条件設定（定型）」を取得する
		return this.adapter.findAllStdOutputCondSetsByCid(companyId)
				.stream()
				.map(StdOutputCondSetDto::fromImport)
				.collect(Collectors.toList());
	}

}
