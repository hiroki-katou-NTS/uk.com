package nts.uk.ctx.sys.assist.app.find.autosetting.deletion;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInCharge;
import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInChargeService;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionPatternSetting;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionPatternSettingRepository;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * アルゴリズム「データ削除表示」
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ScreenDelDisplayProcessingFinder {

	@Inject
	private LoginPersonInChargeService picService;

	@Inject
	private DataDeletionPatternSettingRepository dataDeletionPatternSettingRepository;

	public ScreenDelDisplayProcessingDto findScreenDisplayProcessing() {
		LoginUserContext user = AppContexts.user();

		// ドメインモデル「削除パターン設定」を取得する
		List<DataDeletionPatternSetting> patterns = dataDeletionPatternSettingRepository
				.findByContractCd(user.contractCode());

		// ログイン者が担当者か判断する
		LoginPersonInCharge pic = picService.getPic();

		// List<システム種類>に「システム種類」を追加する。
		List<SystemType> systemTypes = picService.getSystemTypes(pic);

		// 取得したList＜パターン設定>をチェックする。
		if (!patterns.isEmpty()) {
			ScreenDelDisplayProcessingDto dto = new ScreenDelDisplayProcessingDto();
			dto.setPatterns(patterns.stream().map(p -> {
				DataDeletionPatternSettingDto res = new DataDeletionPatternSettingDto();
				p.setMemento(res);
				return res;
			}).sorted(Comparator.comparing(DataDeletionPatternSettingDto::getPatternCode)).collect(Collectors.toList()));
			dto.setSystemTypes(systemTypes.stream().map(t -> t.value).collect(Collectors.toList()));
			return dto;
		} else {
			throw new BusinessException("Msg_1736");
		}
	}
}
