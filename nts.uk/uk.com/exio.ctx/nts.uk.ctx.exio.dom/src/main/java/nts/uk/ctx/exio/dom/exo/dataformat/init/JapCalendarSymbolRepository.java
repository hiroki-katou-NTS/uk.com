package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.util.Optional;
import java.util.List;

/**
* 和暦記号
*/
public interface JapCalendarSymbolRepository
{

    List<JapCalendarSymbol> getAllJapCalendarSymbol();

    Optional<JapCalendarSymbol> getJapCalendarSymbolById();

    void add(JapCalendarSymbol domain);

    void update(JapCalendarSymbol domain);

    void remove();

}
