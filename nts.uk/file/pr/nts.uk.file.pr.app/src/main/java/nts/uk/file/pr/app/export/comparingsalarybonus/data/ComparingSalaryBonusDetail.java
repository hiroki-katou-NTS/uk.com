package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class ComparingSalaryBonusDetail {
 private String itemName;
 private BigDecimal amountItemEarly;
 private BigDecimal amountItemLater;
 private BigDecimal differentAmount;
 private String regitration1;
 private String regitration2;
 private String reason;
 private String confirm;
}
