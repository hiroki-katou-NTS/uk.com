package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.FetchingPosition;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.codeconvert.ExternalImportCodeConvert;

/**
 * 文字型編集
 * @author m_kitahira
 *
 */
@AllArgsConstructor
@Getter
public class StringRevise implements ReviseValue {
	
	/** 値の読み取り位置を指定する */
	private boolean useFetchingPosition;
	
	/** 値の読み取り位置 */
	private Optional<FetchingPosition> fetchingPosition;
	
	/** 固定長編集する */
	private boolean usePadding;
	
	/** 固定長編集内容 */
	private Optional<Padding> padding;
	
	/** コード変換 */
	private Optional<ExternalImportCodeConvert> codeConvert;
	
	@Override
	public Object revise(String target) {
		
		String strResult = target;
		
		if(useFetchingPosition) {
			// 値の有効範囲を指定する場合
			if(fetchingPosition.isPresent()) {
				strResult = this.fetchingPosition.get().extract(strResult);
			}
		}
		
		if(usePadding) {
			// 固定長編集をする場合
			if(padding.isPresent()) {
				strResult = this.padding.get().fix(strResult);
			}
		}
		
		if(codeConvert.isPresent()) {
			strResult = this.codeConvert.get().convert(strResult).toString();
		}
		
		return strResult;
	}
}
