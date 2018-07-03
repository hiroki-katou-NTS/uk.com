package nts.uk.ctx.exio.dom.exo.cdconvert;

import java.util.Optional;
import java.util.List;

/**
* 出力コード変換
*/
public interface OutputCodeConvertRepository
{

    List<OutputCodeConvert> getAllOutputCodeConvert();

    Optional<OutputCodeConvert> getOutputCodeConvertById();

    void add(OutputCodeConvert domain);

    void update(OutputCodeConvert domain);

    void remove();

}
