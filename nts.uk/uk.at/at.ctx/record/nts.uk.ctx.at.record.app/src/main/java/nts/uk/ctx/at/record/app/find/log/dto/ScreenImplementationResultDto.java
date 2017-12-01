package nts.uk.ctx.at.record.app.find.log.dto;

import java.util.List;

import lombok.Value;
import nts.arc.enums.EnumConstant;

/**
 * 
 * @author hieult
 *
 */
@Value
public class ScreenImplementationResultDto {

	/** 就業計算と集計実行ログ */
	private EmpCalAndSumExeLogDto empCalAndSumExeLogDto;

	/** 対象者 */
	private List<TargetPersonDto> targetPersonDto;

	private List<EnumConstant> enumComboBox;

	/** エラーメッセージ情報 */
	private List<ErrMessageInfoDto> errMessageInfoDto;

}