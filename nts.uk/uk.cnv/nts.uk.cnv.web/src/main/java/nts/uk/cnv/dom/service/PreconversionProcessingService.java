package nts.uk.cnv.dom.service;

import javax.ejb.Stateless;

import nts.uk.cnv.dom.service.CreateConversionCodeService.Require;

/**
 * コンバートの前処理を行う
 * @author ai_muto
 *
 * ・パスワード暗号化一時テーブルを作成する
 */
@Stateless
public class PreconversionProcessingService {

	public void execute(Require require, ConversionInfo info) {
		
	}
}
