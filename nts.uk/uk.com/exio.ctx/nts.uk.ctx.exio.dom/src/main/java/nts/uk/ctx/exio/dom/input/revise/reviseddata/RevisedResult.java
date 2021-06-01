package nts.uk.ctx.exio.dom.input.revise.reviseddata;

import java.util.List;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * データの編集結果
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RevisedResult {
	
	/** 編集済みのデータ */
	private Optional<RevisedDataRecord> revisedData;
	
	/** 編集時に発生したエラー */
	private List<RevisingError> revisingError;
}
