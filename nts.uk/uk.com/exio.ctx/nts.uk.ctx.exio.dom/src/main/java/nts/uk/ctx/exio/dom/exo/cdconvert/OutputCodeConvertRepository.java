package nts.uk.ctx.exio.dom.exo.cdconvert;

import java.util.List;
import java.util.Optional;

/**
 * 出力コード変換
 */
public interface OutputCodeConvertRepository {

	List<OutputCodeConvert> getAllOutputCodeConvert();

	Optional<OutputCodeConvert> getOutputCodeConvertById(String cid, String convertCode);

	List<OutputCodeConvert> getOutputCodeConvertByCid(String cid);
	
	void add(OutputCodeConvert domain);

	void update(OutputCodeConvert domain);

	void remove(String cid, String convertCode);
	
}
