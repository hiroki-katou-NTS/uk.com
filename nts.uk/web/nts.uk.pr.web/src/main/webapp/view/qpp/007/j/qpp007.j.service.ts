module nts.uk.pr.view.qpp007.j {
    export module service {
        var paths: any = {
            findSalaryAggregateItem: "ctx/pr/report/salary/aggregate/item/findSalaryAggregateItem",
            importData: "ctx/pr/core/insurance/labor/importser/importData"
        };

        //connection server find Salary Aggregate Item
        export function findSalaryAggregateItem(salaryAggregateItemFindDto: model.SalaryAggregateItemFindDto)
            : JQueryPromise<model.SalaryAggregateItemFindDto> {
            //call server service
            return nts.uk.request.ajax(paths.findSalaryAggregateItem, salaryAggregateItemFindDto);
        }


        export module model {
            export interface SalaryItemDto {
                salaryItemCode: string;
                salaryItemName: string;
            }

            export interface SalaryAggregateItemFindDto {

                salaryAggregateItemCode: string;
                salaryAggregateItemName: string;
                subItemCodes: SalaryItemDto[];
            }
            export interface SalaryAggregateItemInDto {
                taxDivision: number;
                categoryItem: number;
            }
        }
    }
}