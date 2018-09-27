package nts.uk.ctx.at.request.dom.applicationreflect.service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReflectInformationResult {
	/**
	 * 
	 */
	DONE(0),
	NOTDONE(1),
	/**
	 * 反映チェック処理false
	 */
	CHECKFALSE(2);
	public final int value;
}
