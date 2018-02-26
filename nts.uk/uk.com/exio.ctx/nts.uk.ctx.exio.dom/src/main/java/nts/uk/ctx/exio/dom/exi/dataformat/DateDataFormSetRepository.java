package nts.uk.ctx.exio.dom.exi.dataformat;

import java.util.Optional;
import java.util.List;

/**
* 日付型データ形式設定
*/
public interface DateDataFormSetRepository
{

    List<DateDataFormSet> getAllDateDataFormSet();

    Optional<DateDataFormSet> getDateDataFormSetById(String cid, String conditionSetCd, int acceptItemNum);

    void add(DateDataFormSet domain);

    void update(DateDataFormSet domain);

    void remove(String cid, String conditionSetCd, int acceptItemNum);

}
