package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InfoWithDateAppOutput {
	/**
	 * 申請日に関係する情報
	 */
	private InfoWithDateApplication infoWithDateApplication;
	
	/**
	 * エラーメッセージ
	 */
	private Optional<String> opErrorMsg;
}
