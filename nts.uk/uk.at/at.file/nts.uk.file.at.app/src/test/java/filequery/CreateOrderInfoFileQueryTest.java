package filequery;

import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.file.at.app.export.bento.CreateOrderInfoFileQuery;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(JMockit.class)
public class CreateOrderInfoFileQueryTest {

    @Test
    public void testStep1(){
        List<String> wplId = new ArrayList<>(Arrays.asList("123","456","789"));
        DatePeriod period = new DatePeriod(GeneralDate.ymd(2020,01,01), GeneralDate.today());

        List<String> actual = new CreateOrderInfoFileQuery().getListEmpIdInWorkPlace(wplId, period);
    }
}
