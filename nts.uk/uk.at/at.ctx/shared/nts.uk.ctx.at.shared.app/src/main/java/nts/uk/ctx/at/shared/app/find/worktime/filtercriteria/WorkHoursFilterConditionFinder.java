package nts.uk.ctx.at.shared.app.find.worktime.filtercriteria;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.error.BusinessException;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeDto;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class WorkHoursFilterConditionFinder {
	
	/**
	 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL001_就業時間帯選択.A：就業時間帯選択.アルゴリズム.始業時刻とコード名称、備考による検索.始業時刻とコード名称、備考による検索
	 */
	@SuppressWarnings("unused")
	public List<WorkTimeDto> searchByDetails(SearchByDetailsParam param) {
		Integer startTime = param.getStartTime();
		Integer endTime = param.getEndTime();
		String searchQuery = param.getSearchQuery();
		// 始業時刻のチェック
		if ((startTime == null && endTime != null)
				|| (startTime != null && endTime == null)
				|| (startTime > endTime)) {
			throw new BusinessException("Msg_307");
		}
		// 始業時刻とコード名称の未入力チェック
		if (startTime == null && endTime == null
				&& StringUtil.isNullOrEmpty(searchQuery, true)) {
			throw new BusinessException("Msg_2303");
		}
		// 選択可能な就業時間帯一覧から、就業時間帯を絞り込む
		return param.getWorkTimes().stream()
				.filter(data -> this.isValid(data, startTime, endTime, searchQuery))
				.collect(Collectors.toList());
	}

	/**
	 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL001_就業時間帯選択.A：就業時間帯選択.アルゴリズム.絞り込みキーワードで検索する.絞り込みキーワードで検索する
	 */
	public List<WorkTimeDto> searchByKeyword(SearchByKeywordParam param) {
		// INPUT.選択可能な「就業時間帯の設定」から、INPUT．キーワードで絞りこむ
		return param.getWorkTimes().stream()
				.filter(data -> data.name.contains(param.getKeyword())
						|| data.remark.contains(param.getKeyword()))
				.collect(Collectors.toList());
	}
	
	private boolean isValid(WorkTimeDto workTime, Integer startTime, Integer endTime, String searchQuery) {
		boolean isValidTime = true, isValidSearchQuery = true;
		// 始業時刻＜＞空白の場合
		if (startTime != null) {
			
		}
		// コード名称＜＞空白の場合
		if (!StringUtil.isNullOrEmpty(searchQuery, true)) {
			isValidSearchQuery = workTime.code.contains(searchQuery)
					|| workTime.name.contains(searchQuery) || workTime.remark.contains(searchQuery);
		}
		return isValidTime && isValidSearchQuery;
	}
}
