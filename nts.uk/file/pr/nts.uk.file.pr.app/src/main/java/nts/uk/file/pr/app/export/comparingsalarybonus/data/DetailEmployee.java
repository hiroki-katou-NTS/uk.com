package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class DetailEmployee {
      private String personID;
      private String personName;
      List<DataRowComparingSalaryBonus> lstData;
}
