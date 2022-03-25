package nts.uk.ctx.sys.gateway.dom.login.url;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.url.UrlExecInfo;

import java.util.Optional;

/**
 * URLログイン要求を検証する
 */
public class UrlLoginRequestValidator {

    public static void validate(Require require, String urlID){
        //URLパラメータの存在チェック(Check param URL)
        if(urlID == null || urlID.isEmpty()){
            throw new BusinessException(new RawErrorMessage("無効なURLです"));
        }

        // ドメインモデル「埋込URL実行情報」を取得する
        Optional<UrlExecInfo> opUrlExecInfo = require.getUrlExecInfoByUrlID(urlID);
        if(!opUrlExecInfo.isPresent()){
            throw new BusinessException(new RawErrorMessage("無効なURLです"));
        }

		//システム日時が「埋込URL実行情報.有効期限」を超えていないことを確認する (So sánh sysDate với EXpirationDate)
		if(opUrlExecInfo.get().getExpiredDate().before(GeneralDateTime.now())){
            throw new BusinessException("Msg_1095");
		}
    }

    public interface Require{
        Optional<UrlExecInfo> getUrlExecInfoByUrlID(String urlID);
    }
}
