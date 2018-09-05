package nts.uk.ctx.sys.assist.dom.salary;

import java.util.Optional;
import java.util.List;

/**
* 社会保険基準年月日
*/
public interface SociInsuStanDateRepository
{

    List<SociInsuStanDate> getAllSociInsuStanDate();

    Optional<SociInsuStanDate> getSociInsuStanDateById(String cid, int processCateNo);

    void add(SociInsuStanDate domain);

    void update(SociInsuStanDate domain);

    void remove(String cid, int processCateNo);

}
