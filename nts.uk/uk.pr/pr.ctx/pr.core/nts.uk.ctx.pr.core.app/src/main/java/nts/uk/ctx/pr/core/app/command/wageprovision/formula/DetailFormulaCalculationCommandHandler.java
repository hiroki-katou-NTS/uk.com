package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import com.aspose.cells.Cell;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DetailFormulaCalculationCommandHandler extends CommandHandlerWithResult<DetailFormulaCommand, String> {



    @Override
    protected String handle(CommandHandlerContext<DetailFormulaCommand> context) {
        String formulaContent = context.getCommand().getFormulaContent();
        for(FormulaDictionary item : FormulaDictionary.values()){
            formulaContent = formulaContent.replaceAll(item.jpName, item.excelName);
        }
        Workbook workbook = new Workbook();
        Worksheet virtualWorksheet = workbook.getWorksheets().get(0);
        Object o = virtualWorksheet.calculateFormula(formulaContent);
        return o.toString();
    }
}