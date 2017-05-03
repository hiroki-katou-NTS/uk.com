module nts.uk.pr.view.qmm017.q {
    export module service {
        var paths: any = {
            findLastestFormulaManual: "pr/formula/formulaMaster/findLastestFormulaManual/"
        }

        export function findLastestFormulaManual(formulaCode: string): JQueryPromise<model.FormulaManualDto> {
            var dfd = $.Deferred<model.FormulaManualDto>();
            nts.uk.request.ajax("pr", paths.findLastestFormulaManual + formulaCode)
                .done(function(res: model.FormulaManualDto) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
    }

    export module model {
        export class FormulaManualDto {
            companyCode: string;
            formulaCode: string;
            historyId: string;
            formulaContent: string;
            referenceMonthAtr: number;
            roundAtr: number;
            roundDigit: number;
        }
    }
}