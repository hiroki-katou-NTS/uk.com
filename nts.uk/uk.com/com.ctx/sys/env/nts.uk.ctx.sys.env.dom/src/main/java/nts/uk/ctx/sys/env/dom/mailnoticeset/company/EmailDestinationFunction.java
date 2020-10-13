package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import lombok.Getter;
import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId;

import java.util.List;

/**
 * メール送信先機能
 */
@Getter
public class EmailDestinationFunction {
    /**
     * メール分類
     */
    private EmailClassification emailClassification;

    /**
     * 機能ID
     */
    private List<FunctionId> functionIds;
}
