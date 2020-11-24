package nts.uk.ctx.sys.portal.dom.toppagealarm.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページアラーム（ver4～）.DomainService.トップページアラームデータの存在するかのチェック結果
 */
@Builder
@AllArgsConstructor
@Getter
public class ToppageCheckResult {
	
	/**
	 * ありなし区分
	 */
	private Boolean isExisted;
	 
	/**
	 * 発生日時
	 */
	private Optional<GeneralDateTime> occurrenceDateTime;
}
