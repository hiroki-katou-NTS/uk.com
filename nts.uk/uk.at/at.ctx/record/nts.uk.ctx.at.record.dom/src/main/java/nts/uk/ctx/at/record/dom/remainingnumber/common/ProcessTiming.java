package nts.uk.ctx.at.record.dom.remainingnumber.common;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 * 処理タイミング
 * @author masaaki_jinno
 *
 */
@Getter
public enum ProcessTiming {

	/**
	 * 消滅
	 */
	LASPED(1),

	/**
	 * 付与
	 */
	GRANT(2),

	/**
	 * 消化
	 */
	DIGEST(3),

	/**
	 * エラーチェック
	 */
	ERROR_CHECK(4);

	public int value;

	ProcessTiming(int type){
		this.value = type;
	}

	public static ProcessTiming toEnum(int value){
		return EnumAdaptor.valueOf(value, ProcessTiming.class);
	}

}
